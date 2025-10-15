package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import dev.aaronhowser.mods.irregular_implements.handler.WirelessRedstoneHandler
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class RedstoneInterfaceBlockEntity(
	pBlockEntityType: BlockEntityType<*>,
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(pBlockEntityType, pPos, pBlockState) {

	override fun setChanged() {
		super.setChanged()

		level?.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL_IMMEDIATE)
	}

	override fun setRemoved() {
		val level = this.level
		if (level != null) {
			WirelessRedstoneHandler.removeInterface(level, this.blockPos)
		}

		super.setRemoved()
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}