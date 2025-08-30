package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AutoPlacerBlockEntity (
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.AUTO_PLACER.get(), pPos, pBlockState){
}