package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import org.antlr.v4.runtime.misc.MultiMap

abstract class RedstoneInterfaceBlockEntity(
    pBlockEntityType: BlockEntityType<*>,
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(pBlockEntityType, pPos, pBlockState) {

    companion object {

        val linkedPositions: MultiMap<BlockPos, BlockPos> = MultiMap()

        fun linkBlock(interfacePos: BlockPos, targetPos: BlockPos) {
            linkedPositions.getOrPut(interfacePos) { mutableListOf() }.add(targetPos)
        }

        fun unlinkBlock(interfacePos: BlockPos, targetPos: BlockPos) {
            linkedPositions[interfacePos]?.remove(targetPos)
            if (!linkedPositions.containsKey(interfacePos)) {
                linkedPositions.remove(interfacePos)
            }
        }

        fun getLinkedPower(level: Level, targetPos: BlockPos): Int {
            val interfaces = linkedPositions[targetPos] ?: return -1
            return interfaces.maxOf { level.getBestNeighborSignal(it) }
        }

    }

    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}