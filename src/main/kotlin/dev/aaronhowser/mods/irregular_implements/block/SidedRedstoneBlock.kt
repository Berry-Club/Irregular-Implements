package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition

class SidedRedstoneBlock(
    properties: Properties =
        Properties
            .ofFullCopy(Blocks.REDSTONE_BLOCK)
            .isRedstoneConductor(Blocks::never)
) : DirectionalBlock(properties) {

    companion object {
        private val CODEC = simpleCodec(::SidedRedstoneBlock)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        )
    }

    override fun codec(): MapCodec<SidedRedstoneBlock> = CODEC

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.nearestLookingDirection.opposite)
    }

    override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return if (state.getValue(FACING) == direction.opposite) 16 else 0
    }

    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return getDirectSignal(state, level, pos, direction)
    }

}