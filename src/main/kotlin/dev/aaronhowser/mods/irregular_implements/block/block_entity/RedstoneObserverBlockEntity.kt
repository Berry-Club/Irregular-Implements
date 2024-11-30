package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import org.antlr.v4.runtime.misc.MultiMap

class RedstoneObserverBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : RedstoneToolLinkable, BlockEntity(ModBlockEntities.REDSTONE_OBSERVER.get(), pPos, pBlockState) {

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
                .add(observerPos)
        }

        @JvmStatic
        private fun unlinkBlock(level: Level, observerPos: BlockPos, targetPos: BlockPos) {
            val levelPos = LevelPos(level, targetPos)

            linkedPositions[levelPos]?.remove(observerPos)
            if (linkedPositions[levelPos]?.isEmpty().isTrue) {
                linkedPositions.remove(levelPos)
            }
        }

        @JvmStatic
        fun updateObservers(level: Level, targetPos: BlockPos) {
            val levelPos = LevelPos(level, targetPos)

            for (observerPos in linkedPositions[levelPos] ?: return) {
                level.updateNeighborsAt(observerPos, level.getBlockState(observerPos).block)
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

    }

    override fun setRemoved() {
        val level = this.level
        if (level != null) {
            removeObserver(level, this.blockPos)
        }

        super.setRemoved()
    }

    override var linkedPos: BlockPos? = null
        set(value) {
            val oldField = field
            if (oldField != null) {
                unlinkBlock(
                    level = this.level!!,
                    observerPos = this.blockPos,
                    targetPos = oldField
                )
            }

            if (value != null) {
                linkBlock(
                    level = this.level!!,
                    observerPos = this.blockPos,
                    targetPos = value
                )
            } else {
                removeObserver(this.level!!, this.blockPos)
            }

            field = value
            setChanged()
        }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)
        this.saveToTag(tag)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)
        this.loadFromTag(tag)
    }

    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}