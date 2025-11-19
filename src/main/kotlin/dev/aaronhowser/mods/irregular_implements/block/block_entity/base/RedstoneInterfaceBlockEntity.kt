package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import net.minecraft.core.BlockPos
import net.minecraft.core.GlobalPos
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

abstract class RedstoneInterfaceBlockEntity(
	blockEntityType: BlockEntityType<*>,
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(blockEntityType, pos, blockState) {

	abstract fun updateTargets()

	protected open fun clientTick() {}
	protected open fun serverTick() {}

	protected fun updatePos(pos: BlockPos) {
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
		/**
		 * A map of a linked position to the position of every Interface that links to it
		 */
		private val linkedPositions: MultiMap<GlobalPos, BlockPos> = MultiMap()

		@JvmStatic
		fun linkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
			linkedPositions
				.getOrPut(GlobalPos(level.dimension(), targetPos)) { mutableListOf() }
				.add(interfacePos)
		}

		@JvmStatic
		fun unlinkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
			val globalPos = GlobalPos(level.dimension(), targetPos)

			val interfaces = linkedPositions[globalPos] ?: return
			interfaces.remove(interfacePos)

			if (interfaces.isEmpty()) {
				linkedPositions.remove(globalPos)
			}
		}

		@JvmStatic
		fun getLinkedPower(level: Level, targetPos: BlockPos): Int {
			val globalPos = GlobalPos(level.dimension(), targetPos)

			val interfaces = linkedPositions[globalPos] ?: return -1
			if (interfaces.isEmpty()) return -1

			return interfaces.maxOf { level.getBestNeighborSignal(it) }
		}

		@JvmStatic
		fun removeInterface(level: Level, interfacePos: BlockPos) {
			val iterator = linkedPositions.entries.iterator()
			val dimension = level.dimension()

			while (iterator.hasNext()) {
				val (globalPos, interfaces) = iterator.next()

				if (globalPos.dimension == dimension && interfaces.contains(interfacePos)) {
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
			} else {
				blockEntity.serverTick()
			}
		}
	}

}