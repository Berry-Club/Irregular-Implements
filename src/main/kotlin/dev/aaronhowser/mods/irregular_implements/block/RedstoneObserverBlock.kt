package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneObserverBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RedstoneObserverBlock : EntityBlock, Block(
    Properties
        .ofFullCopy(Blocks.IRON_BLOCK)
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return RedstoneObserverBlockEntity(pos, state)
    }

    override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos): Int {
        val blockEntity = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
        val linkedPos = blockEntity.linkedPos ?: return 0
        if (!level.isLoaded(linkedPos)) return 0

        return level
            .getBlockState(linkedPos)
            .getAnalogOutputSignal(level, linkedPos)
    }

    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        val blockEntity = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
        val linkedPos = blockEntity.linkedPos ?: return 0
        if (level is Level && !level.isLoaded(linkedPos)) return 0

        return level
            .getBlockState(linkedPos)
            .getSignal(level, linkedPos, direction)
    }

    override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        val blockEntity = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
        val linkedPos = blockEntity.linkedPos ?: return 0
        if (level is Level && !level.isLoaded(linkedPos)) return 0

        return level
            .getBlockState(linkedPos)
            .getDirectSignal(level, linkedPos, direction)
    }

}