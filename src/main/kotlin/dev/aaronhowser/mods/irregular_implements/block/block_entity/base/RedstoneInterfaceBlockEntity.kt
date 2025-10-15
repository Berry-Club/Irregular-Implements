package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import org.antlr.v4.runtime.misc.MultiMap

abstract class  RedstoneInterfaceBlockEntity(
	blockEntityType: BlockEntityType<*>,
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(blockEntityType, pos, blockState) {

	abstract fun updateTargets()

	open fun clientTick() {}

	fun updatePos(pos: BlockPos) {
		val level = this.level ?: return

		if (level.isLoaded(pos)) {
			val linkedState = level.getBlockState(pos)
			linkedState.handleNeighborChanged(level, pos, this.blockState.block, pos, false)
			level.updateNeighborsAt(pos, linkedState.block)
		}
	}

	override fun setChanged() {
		super.setChanged()

		level?.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL_IMMEDIATE)
	}

	override fun setRemoved() {
		val level = this.level
		if (level != null) {
			removeInterface(level, this.blockPos)
		}

		super.setRemoved()
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		private data class LevelPos(val level: Level, val pos: BlockPos)

		/**
		 * A map of a linked position to the position of every Interface that links to it
		 */
		private val linkedPositions: MultiMap<LevelPos, BlockPos> = MultiMap()

		@JvmStatic
		fun linkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
			linkedPositions
				.getOrPut(LevelPos(level, targetPos)) { mutableListOf() }
				.add(interfacePos)
		}

		@JvmStatic
		fun unlinkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
			val levelPos = LevelPos(level, targetPos)

			val interfaces = linkedPositions[levelPos] ?: return

			interfaces.remove(interfacePos)
			if (interfaces.isEmpty().isTrue) {
				linkedPositions.remove(levelPos)
			}
		}

		@JvmStatic
		fun getLinkedPower(level: Level, targetPos: BlockPos): Int {
			val levelPos = LevelPos(level, targetPos)
			val interfaces = linkedPositions[levelPos] ?: return -1
			if (interfaces.isEmpty()) return -1
			return interfaces.maxOf { level.getBestNeighborSignal(it) }
		}

		@JvmStatic
		fun removeInterface(level: Level, interfacePos: BlockPos) {
			val iterator = linkedPositions.entries.iterator()

			while (iterator.hasNext()) {
				val (levelPos, interfaces) = iterator.next()

				if (levelPos.level == level && interfaces.contains(interfacePos)) {
					interfaces.remove(interfacePos)
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
			blockEntity: RedstoneInterfaceBlockEntity
		) {
			if (level.isClientSide) {
				blockEntity.clientTick()
			}
		}
	}

}