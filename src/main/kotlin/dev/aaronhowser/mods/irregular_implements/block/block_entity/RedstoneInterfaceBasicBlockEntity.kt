package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.ServerScheduler
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceBasicBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : RedstoneToolLinkable, RedstoneInterfaceBlockEntity(
    ModBlockEntities.REDSTONE_INTERFACE.get(),
    pPos,
    pBlockState
) {

    override var linkedPos: BlockPos? = null
        set(value) {
            val oldField = field
            if (oldField != null) {
                unlinkBlock(this.level!!, this.blockPos, oldField)
            }

            if (value != null) {
                linkBlock(this.level!!, this.blockPos, value)
            }

            field = value
            setChanged()
        }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        val linkedPos = linkedPos
        if (linkedPos != null) {
            tag.putLong(RedstoneToolLinkable.LINKED_POS_NBT, linkedPos.asLong())
        }
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        if (tag.contains(RedstoneToolLinkable.LINKED_POS_NBT)) {
            ServerScheduler.scheduleTaskInTicks(1) {
                linkedPos = BlockPos.of(tag.getLong(RedstoneToolLinkable.LINKED_POS_NBT))
            }
        }
    }

}