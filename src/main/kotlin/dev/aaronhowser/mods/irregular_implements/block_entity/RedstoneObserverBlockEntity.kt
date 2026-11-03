package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isServerSide
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isTrue
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
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.redstone.NeighborUpdater
import org.antlr.v4.runtime.misc.MultiMap

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
		return true
	}

	fun setStrongPower(power: Int, index: Int): Boolean {
		if (strongPower[index] == power) {
			return false
		}

		strongPower[index] = power
		return true
	}

	fun setAnalogPower(power: Int): Boolean {
		if (analogPower == power) {
			return false
		}

		analogPower = power
		return true
	}

	/**
	 * Actually sets futureLinkedPos; the change will be applied on the next server tick.
	 * The reason for this is that when the BlockEntity is first being loaded, Level is null, which means
	 * we can't link/unlink blocks at that time.
	 */
	override fun setLinkedPos(pos: BlockPos?) {
		nextLinkedPos = pos
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
		val list = CompoundTag()
		list.putIntArray("weak_power", this.weakPower)
		list.putIntArray("strong_power", this.strongPower)
		list.putInt("analog_power", this.analogPower)
		tag.put("directional_power", list)
		this.saveToTag(tag)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)
		val list: CompoundTag = tag.get("directional_power") as CompoundTag
		this.weakPower = readPowerArray(list, "weak_power")
		this.strongPower = readPowerArray(list, "strong_power")
		this.analogPower = list.getInt("analog_power")
		this.loadFromTag(tag)
	}

	private fun readPowerArray(list: CompoundTag, key: String): IntArray {
		val result = IntArray(Direction.entries.size)
		val saved = list.getIntArray(key)
		return saved.copyInto(result, endIndex = minOf(saved.size, result.size))
	}

	fun updateNeighborPower(level: Level, pos: BlockPos, observer: BlockState) {
		val redstoneObserverBlock = ModBlocks.REDSTONE_OBSERVER.get()
		for (direction in NeighborUpdater.UPDATE_ORDER) {
			val neighborPos = pos.relative(direction)
			level.neighborChanged(neighborPos, redstoneObserverBlock, pos)
			level.updateNeighborsAtExceptFromFacing(neighborPos, redstoneObserverBlock, direction.opposite)

		}
		level.updateNeighbourForOutputSignal(pos, redstoneObserverBlock)
		observer.updateNeighbourShapes(level, pos, 3)
		setChanged()
	}

	companion object {
		private data class LevelPos(val level: Level, val pos: BlockPos)

		/**
		 * A map of a linked position to the position of every Observer that's watching it
		 */
		private val linkedPositions: MultiMap<LevelPos, BlockPos> = MultiMap()

		@JvmStatic
		private fun linkBlock(level: Level, observerPos: BlockPos, targetPos: BlockPos) {
			linkedPositions
				.getOrPut(LevelPos(level, targetPos)) { mutableListOf() }
				.let { observers ->
					if (observerPos !in observers) {
						observers.add(observerPos)
						if (level.isServerSide) {
							updateObservers(level as ServerLevel, targetPos)
						}
					}
				}
		}

		@JvmStatic
		private fun unlinkBlock(level: Level, observerPos: BlockPos, targetPos: BlockPos) {
			val levelPos = LevelPos(level, targetPos)

			linkedPositions[levelPos]?.remove(observerPos)
			if (linkedPositions[levelPos]?.isEmpty().isTrue()) {
				linkedPositions.remove(levelPos)
			}
		}

		@JvmStatic
		fun updateObservers(level: Level, targetPos: BlockPos) {
			val targetBlock = level.getBlockState(targetPos)
			if (targetBlock.isBlock(ModBlockTagsProvider.IGNORES_WIRELESS_REDSTONE)) return

			val positions = linkedPositions[LevelPos(level, targetPos)] ?: return

			for (observerPos in positions) {
				if (level.isLoaded(observerPos)) {
					val observer = level.getBlockState(observerPos)
					if (observer.isBlock(ModBlocks.REDSTONE_OBSERVER)) {
						val observerEntity = level.getBlockEntity(observerPos, ModBlockEntityTypes.REDSTONE_OBSERVER.get()).orElse(null) ?: continue

						var powerChange = false

						for (direction in NeighborUpdater.UPDATE_ORDER) {
							val index = direction.ordinal
							powerChange = observerEntity.setWeakPower(level.getSignal(targetPos, direction).coerceIn(0, 15), index) || powerChange
							powerChange = observerEntity.setStrongPower(level.getDirectSignal(targetPos, direction).coerceIn(0, 15), index) || powerChange
						}
						powerChange = observerEntity.setAnalogPower(targetBlock.getAnalogOutputSignal(level, targetPos).coerceIn(0, 15)) || powerChange


						if (powerChange) {
							observerEntity.updateNeighborPower(level, observerPos, observer)
						}

					}
				}
			}
		}

		@JvmStatic
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

}