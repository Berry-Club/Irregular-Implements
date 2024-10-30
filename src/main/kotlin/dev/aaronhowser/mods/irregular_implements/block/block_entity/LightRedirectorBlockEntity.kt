package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class LightRedirectorBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.LIGHT_REDIRECTOR.get(), pPos, pBlockState) {
}