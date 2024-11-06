package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class TriggerGlass : TransparentBlock(
    Properties
        .ofFullCopy(Blocks.RED_STAINED_GLASS)
        .dynamicShape()
) {

    companion object {
        private const val MAX_RECURSIONS = 50

        val NOT_SOLID: BooleanProperty = BooleanProperty.create("not_solid")
        val ITERATIONS_REMAINING: IntegerProperty = IntegerProperty.create("iterations_remaining", 0, MAX_RECURSIONS)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(NOT_SOLID, false)
                .setValue(ITERATIONS_REMAINING, MAX_RECURSIONS)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(NOT_SOLID, ITERATIONS_REMAINING)
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

        propagate(level, pos, MAX_RECURSIONS)
    }

    private fun propagate(
        level: Level,
        pos: BlockPos,
        recursionsLeft: Int
    ) {
        if (recursionsLeft <= 0) return        //TODO: Configurable range

        val state = level.getBlockState(pos)
        if (state.block != this) return

        if (state.getValue(NOT_SOLID) && state.getValue(ITERATIONS_REMAINING) >= recursionsLeft) return

        val newState = state
            .setValue(NOT_SOLID, true)
            .setValue(ITERATIONS_REMAINING, recursionsLeft)

        level.setBlock(pos, newState, 2)
        level.scheduleTick(pos, this, 20 * 3)   //TODO: configurable duration

        for (direction in Direction.entries) {
            val offset = pos.relative(direction)
            propagate(level, offset, recursionsLeft - 1)
        }
    }

    override fun tick(
        state: BlockState,
        level: ServerLevel,
        pos: BlockPos,
        random: RandomSource
    ) {
        level.setBlock(pos, defaultBlockState(), 2)

        val isPowered = level.hasNeighborSignal(pos)
        if (isPowered) {
            propagate(level, pos, MAX_RECURSIONS)
        }
    }

}