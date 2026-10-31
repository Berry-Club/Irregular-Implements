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
import net.minecraft.world.level.ChunkPos
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
	private var futureLinkedPos: BlockPos? = null

	private var directionalPower: Int = 0
	private var directionalDirect: Int = 0
	private var analogPower: Int = 0

	override fun getLinkedPos(): BlockPos? = linkedPos

	fun getDirectionalPower(): Int = directionalPower
	fun setDirectionalPower(power: Int): Boolean {
		if (directionalPower != power) {
			directionalPower = power
			return true
		} else {
			return false
		}
	}

	fun getDirectionalDirect(): Int = directionalDirect
	fun setDirectionalDirect(direct: Int): Boolean {
		if (directionalDirect != direct) {
			directionalDirect = direct
			return true
		} else {
			return false
		}
	}

	fun getAnalog(): Int = analogPower
	fun setAnalog(analog: Int): Boolean {
		if (analogPower != analog) {
			analogPower = analog
			return true
		} else {
			return false
		}
	}

	/**
	 * Actually sets futureLinkedPos; the change will be applied on the next server tick.
	 * The reason for this is that when the BlockEntity is first being loaded, Level is null, which means
	 * we can't link/unlink blocks at that time.
	 */
	override fun setLinkedPos(pos: BlockPos?) {
		futureLinkedPos = pos
	}

	private fun updateLinkedPos() {
		if (linkedPos == futureLinkedPos) return
		val level = this.level ?: return

		val oldPos = linkedPos
		if (oldPos != null) {
			unlinkBlock(
				level = level,
				observerPos = this.blockPos,
				targetPos = oldPos
			)
		}

		val newPos = futureLinkedPos

		if (newPos != null) {
			linkBlock(
				level = level,
				observerPos = this.blockPos,
				targetPos = newPos
			)
		} else {
			removeObserver(level, this.blockPos)
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
		tag.putInt("directional_power", this.directionalPower)
		tag.putInt("directional_direct", this.directionalDirect)
		tag.putInt("analog", this.analogPower)
		this.saveToTag(tag)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)
		this.directionalPower = tag.getInt("directional_power")
		this.directionalDirect = tag.getInt("directional_direct")
		this.analogPower = tag.getInt("analog")
		this.loadFromTag(tag)
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
							updateObservers(level as ServerLevel,targetPos)
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
		fun updateObservers(level: ServerLevel, targetPos: BlockPos) {
			val targetBlock = level.getBlockState(targetPos)
			if (targetBlock.isBlock(ModBlockTagsProvider.IGNORES_WIRELESS_REDSTONE)) return

			val positions = linkedPositions[LevelPos(level, targetPos)] ?: return

			for (observerPos in positions) {
				if (level.isLoaded(observerPos)) {
					val observer = level.getBlockState(observerPos)
					if (observer.isBlock(ModBlocks.REDSTONE_OBSERVER)) {
						val observerEntity = level.getBlockEntity(observerPos, ModBlockEntityTypes.REDSTONE_OBSERVER.get()).orElse(null) ?: return
						var linkedPower = 0
						var linkedDirect = 0
						for (direction in NeighborUpdater.UPDATE_ORDER) {
							val directionOrdinal = direction.ordinal
							linkedPower = linkedPower.or(level.getSignal(targetPos,direction).shl(directionOrdinal * 4))
							linkedDirect = linkedDirect.or(level.getDirectSignal(targetPos,direction).shl(directionOrdinal * 4))
						}
						val linkedAnalog = targetBlock.getAnalogOutputSignal(level,targetPos)

						val powerChanged = observerEntity.setDirectionalPower(linkedPower)
						val directChanged = observerEntity.setDirectionalDirect(linkedDirect)
						val analogChanged = observerEntity.setAnalog(linkedAnalog)
						updateNeighborPower(level,observerPos,observer,observerEntity,powerChanged,directChanged,analogChanged)

					}
				}
			}
		}

		@JvmStatic
		fun updateNeighborPower(level: ServerLevel, pos: BlockPos, observer: BlockState, observerBlockEntity: RedstoneObserverBlockEntity?, power: Boolean, direct: Boolean, analog: Boolean) {
			val redstoneObserverBlock = ModBlocks.REDSTONE_OBSERVER.get()
			if (power || direct) {
				for (direction in Direction.entries) {
					val neighborPos = pos.relative(direction)
					level.neighborChanged(neighborPos, redstoneObserverBlock, pos)
					level.updateNeighborsAtExceptFromFacing(neighborPos, redstoneObserverBlock, direction.opposite)
				}
			}

			if (analog) {
				level.updateNeighbourForOutputSignal(pos, redstoneObserverBlock)
			}

			if (power || direct || analog) {
				observerBlockEntity?.setChanged()
				observer.updateNeighbourShapes(level, pos, 3)

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