package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceAdvancedBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneInterfaceBlockEntity(ModBlockEntities.ADVANCED_REDSTONE_INTERFACE.get(), pPos, pBlockState) {
}