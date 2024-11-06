package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class DiaphanousBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.DIAPHANOUS_BLOCK.get(), pPos, pBlockState) {

    var blockToRender: ItemStack = Items.STONE.defaultInstance
    var alpha: Float = 1f

    companion object {

        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState) {

        }

    }

}