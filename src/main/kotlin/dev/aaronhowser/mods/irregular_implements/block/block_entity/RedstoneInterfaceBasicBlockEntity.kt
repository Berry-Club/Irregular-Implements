package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
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
                unlinkBlock(
                    level = this.level!!,
                    interfacePos = this.blockPos,
                    targetPos = oldField
                )
            }

            if (value != null) {
                linkBlock(
                    level = this.level!!,
                    interfacePos = this.blockPos,
                    targetPos = value
                )
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

}