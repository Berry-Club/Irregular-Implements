package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import dev.aaronhowser.mods.irregular_implements.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.LineIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.redstone.NeighborUpdater
import org.antlr.v4.runtime.misc.MultiMap
import kotlin.jvm.optionals.getOrNull

class RedstoneObserverBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : SyncingBlockEntity(ModBlockEntityTypes.REDSTONE_OBSERVER.get(), pos, blockState), RedstoneToolLinkable {

	override val syncImmediately: Boolean = true

	override fun setRemoved() {
		val level = this.level
		if (level != null) {
			removeObserver(level, this.blockPos)
		}

		super.setRemoved()
	}

	private var linkedPos: BlockPos? = null
	private var nextLinkedPos: BlockPos? = null

	private var weakPower: IntArray = IntArray(Direction.entries.size)
	private var strongPower: IntArray = IntArray(Direction.entries.size)
	private var analogPower: Int = 0

	override fun getLinkedPos(): BlockPos? = linkedPos

	fun getWeakPower(direction: Direction): Int = weakPower[direction.ordinal]
	fun getStrongPower(direction: Direction): Int = strongPower[direction.ordinal]
	fun getAnalogPower(): Int = analogPower

	fun setWeakPower(power: Int, index: Int): Boolean {
		if (weakPower[index] == power) {
			return false
		}

		weakPower[index] = power
		setChanged()
		return true
	}

	fun setStrongPower(power: Int, index: Int): Boolean {
		if (strongPower[index] == power) {
			return false
		}

		strongPower[index] = power
		setChanged()
		return true
	}

	fun setAnalogPower(power: Int): Boolean {
		if (analogPower == power) {
			return false
		}

		analogPower = power
		setChanged()
		return true
	}

	/**
	 * Actually sets futureLinkedPos; the change will be applied on the next server tick.
	 * The reason for this is that when the BlockEntity is first being loaded, Level is null, which means
	 * we can't link/unlink blocks at that time.
	 */
	override fun setLinkedPos(pos: BlockPos?) {
		if (pos == nextLinkedPos) return
		nextLinkedPos = pos
		setChanged()
	}

	private fun updateLinkedPos() {
		if (linkedPos == nextLinkedPos) return
		val level = this.level ?: return

		val oldPos = linkedPos
		if (oldPos != null) {
			unlinkBlock(level, blockPos, oldPos)
		}

		val newPos = nextLinkedPos
		if (newPos == null) {
			removeObserver(level, blockPos)
		} else {
			linkBlock(level, blockPos, newPos)
		}

		linkedPos = newPos
		setChanged()
	}

	private fun serverTick() {
		val level = this.level ?: return
		if (level.isClientSide) return

		updateLinkedPos()
	}

	private fun clientTick() {
		val level = this.level ?: return
		if (!level.isClientSide) return

		updateLinkedPos()

		val player = AaronClientUtil.localPlayer ?: return
		if (!player.isHolding(ModItems.REDSTONE_TOOL.get())) return

		CubeIndicatorRenderer.addIndicator(
			this.blockPos,
			2,
			0x32FF0000,
			size = 0.5f
		)

		val targetPos = this.linkedPos
		if (targetPos != null) {
			LineIndicatorRenderer.addIndicator(
				start = this.blockPos.center,
				end = targetPos.center,
				duration = 2,
				color = 0xFFFF0000.toInt()
			)

			CubeIndicatorRenderer.addIndicator(
				target = targetPos,
				duration = 2,
				color = 0x320000FF,
				size = 0.5f
			)
		}
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putIntArray(WEAK_POWER_TAG, weakPower)
		tag.putIntArray(STRONG_POWER_TAG, strongPower)
		tag.putInt(ANALOG_POWER, analogPower)
		saveToTag(tag)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		weakPower = readPowerArray(tag, WEAK_POWER_TAG)
		strongPower = readPowerArray(tag, STRONG_POWER_TAG)
		analogPower = tag.getInt(ANALOG_POWER)
		loadFromTag(tag)
	}

	private fun readPowerArray(list: CompoundTag, key: String): IntArray {
		val result = IntArray(Direction.entries.size)
		val saved = list.getIntArray(key)
		return saved.copyInto(result, endIndex = minOf(saved.size, result.size))
	}

	companion object {
		const val WEAK_POWER_TAG = "weak_power"
		const val STRONG_POWER_TAG = "strong_power"
		const val ANALOG_POWER = "analog_power"

		/**
		 * A map of a linked position to the position of every Observer that's watching it
		 */
		private val linkedPositions: MultiMap<LevelPos, BlockPos> = MultiMap()

		@JvmStatic
		private fun linkBlock(level: Level, observerPos: BlockPos, targetPos: BlockPos) {
			val observers = linkedPositions.getOrPut(LevelPos(level, targetPos), ::mutableListOf)

			if (observerPos !in observers) {
				observers.add(observerPos)
				if (level is ServerLevel) {
					updateObservers(level, targetPos)
				}
			}
		}

		@JvmStatic
		private fun unlinkBlock(level: Level, observerPos: BlockPos, targetPos: BlockPos) {
			val levelPos = LevelPos(level, targetPos)

			val current = linkedPositions[levelPos]
			if (current.isNullOrEmpty()) return

			current.remove(observerPos)
			if (current.isEmpty()) {
				linkedPositions.remove(levelPos)
			}
		}

		@JvmStatic
		fun updateObservers(level: Level, targetPos: BlockPos) {
			val targetBlock = level.getBlockState(targetPos)
			if (targetBlock.isBlock(ModBlockTagsProvider.IGNORES_WIRELESS_REDSTONE)) return

			val positions = linkedPositions[LevelPos(level, targetPos)] ?: return

			for (observerPos in positions) {
				if (!level.isLoaded(observerPos)) continue

				val observer = level.getBlockState(observerPos)
				if (!observer.isBlock(ModBlocks.REDSTONE_OBSERVER)) continue

				val observerBe = level.getBlockEntity(
					observerPos,
					ModBlockEntityTypes.REDSTONE_OBSERVER.get()
				).getOrNull() ?: continue

				val analogThere = targetBlock.getAnalogOutputSignal(level, targetPos).coerceIn(0, 15)
				var didPowerChange = observerBe.setAnalogPower(analogThere)

				for (direction in NeighborUpdater.UPDATE_ORDER) {
					val index = direction.ordinal
					val weakThere = level.getSignal(targetPos, direction).coerceIn(0, 15)
					val strongThere = level.getDirectSignal(targetPos, direction).coerceIn(0, 15)

					val changeThisTime = observerBe.setWeakPower(weakThere, index) || observerBe.setStrongPower(strongThere, index)
					if (changeThisTime) didPowerChange = true
				}

				if (didPowerChange) {
					updateNeighborPower(level, observerPos, observer)
				}
			}
		}

		fun updateNeighborPower(level: Level, pos: BlockPos, observer: BlockState) {
			val redstoneObserverBlock = ModBlocks.REDSTONE_OBSERVER.get()

			for (direction in NeighborUpdater.UPDATE_ORDER) {
				val neighborPos = pos.relative(direction)
				level.neighborChanged(neighborPos, redstoneObserverBlock, pos)
				level.updateNeighborsAtExceptFromFacing(neighborPos, redstoneObserverBlock, direction.opposite)
			}

			level.updateNeighbourForOutputSignal(pos, redstoneObserverBlock)
			observer.updateNeighbourShapes(level, pos, Block.UPDATE_ALL)
		}

		private fun removeObserver(level: Level, observerPos: BlockPos) {
			val iterator = linkedPositions.entries.iterator()

			while (iterator.hasNext()) {
				val (levelPos, interfaces) = iterator.next()

				if (levelPos.level == level && interfaces.contains(observerPos)) {
					interfaces.remove(observerPos)
					if (interfaces.isEmpty()) {
						iterator.remove()
					}
				}
			}
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: RedstoneObserverBlockEntity
		) {
			if (level.isClientSide) {
				blockEntity.clientTick()
			} else {
				blockEntity.serverTick()
			}
		}
	}

	private data class LevelPos(val level: Level, val pos: BlockPos)

}