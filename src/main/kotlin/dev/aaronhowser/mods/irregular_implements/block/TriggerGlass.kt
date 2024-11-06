package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class TriggerGlass : TransparentBlock(
    Properties
        .ofFullCopy(Blocks.RED_STAINED_GLASS)
        .dynamicShape()
) {

    companion object {
        val NOT_SOLID: BooleanProperty = BooleanProperty.create("not_solid")
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(NOT_SOLID, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(NOT_SOLID)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(NOT_SOLID, false)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (state.getValue(NOT_SOLID)) {
            Shapes.empty()
        } else {
            Shapes.block()
        }
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
        return true
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        block: Block,
        fromPos: BlockPos,
        isMoving: Boolean
    ) {
        if (level.isClientSide) return

        val isPowered = level.hasNeighborSignal(pos)
        if (!isPowered) return

        propagate(level, pos, 0)
    }

    private fun propagate(
        level: Level,
        pos: BlockPos,
        recursions: Int
    ) {
        if (recursions >= 20) return        //TODO: Configurable range

        val state = level.getBlockState(pos)
        if (state.block != this) return
        if (state.getValue(NOT_SOLID)) return

        level.setBlockAndUpdate(pos, state.setValue(NOT_SOLID, true))
        level.scheduleTick(pos, this, 20 * 3)   //TODO: configurable duration

        for (direction in Direction.entries) {
            val offset = pos.relative(direction)
            propagate(level, offset, recursions + 1)
        }
    }

    override fun tick(
        state: BlockState,
        level: ServerLevel,
        pos: BlockPos,
        random: RandomSource
    ) {
        level.setBlockAndUpdate(pos, state.setValue(NOT_SOLID, false))
    }

    override fun skipRendering(state: BlockState, adjacentBlockState: BlockState, side: Direction): Boolean {
        if (adjacentBlockState.block != this) return super.skipRendering(state, adjacentBlockState, side)

        return adjacentBlockState.getValue(NOT_SOLID) == state.getValue(NOT_SOLID)
    }

}