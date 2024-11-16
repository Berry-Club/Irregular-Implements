package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
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
        private data class LevelPos(val level: Level, val pos: BlockPos) {

            //FIXME: Hashcode not working??? in array get
            override fun hashCode(): Int {
                var result = level.hashCode()
                result = 31 * result + pos.hashCode()
                return result
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as LevelPos

                if (level != other.level) return false
                if (pos != other.pos) return false

                return true
            }
        }

        /**
         * A map of a linked position to the position of every Interface that links to it
         */
        private val linkedPositions: MultiMap<LevelPos, BlockPos> = MultiMap()

        fun linkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
            linkedPositions
                .getOrPut(LevelPos(level, targetPos)) { mutableListOf() }
                .add(interfacePos)
        }

        fun unlinkBlock(level: Level, interfacePos: BlockPos, targetPos: BlockPos) {
            val levelPos = LevelPos(level, targetPos)

            linkedPositions[levelPos]?.remove(interfacePos)
            if (linkedPositions[levelPos]?.isEmpty().isTrue) {
                linkedPositions.remove(levelPos)
            }
        }

        fun getLinkedPower(level: Level, targetPos: BlockPos): Int {
            val levelPos = LevelPos(level, targetPos)
            val interfaces = linkedPositions[levelPos] ?: return -1

            return interfaces.maxOf { level.getBestNeighborSignal(it) }
        }

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

}