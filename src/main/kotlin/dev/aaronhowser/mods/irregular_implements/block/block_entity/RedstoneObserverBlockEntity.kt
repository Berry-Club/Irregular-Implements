package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RedstoneObserverBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : RedstoneToolLinkable, BlockEntity(ModBlockEntities.REDSTONE_OBSERVER.get(), pPos, pBlockState) {

    override var linkedPos: BlockPos? = null

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)
        this.saveToTag(tag)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)
        this.loadFromTag(tag)
    }

}