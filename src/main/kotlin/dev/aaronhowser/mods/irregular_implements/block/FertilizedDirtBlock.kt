package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.util.TriState

class FertilizedDirtBlock : Block(Properties.ofFullCopy(Blocks.FARMLAND)) {

    companion object {
        val TILLED: BooleanProperty = BooleanProperty.create("tilled")

        val TILLED_SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(TILLED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(TILLED)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (state.getValue(TILLED)) {
            TILLED_SHAPE
        } else {
            Shapes.block()
        }
    }

    override fun isFertile(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return state.getValue(TILLED)
    }

    override fun isOcclusionShapeFullBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return !state.getValue(TILLED)
    }

    override fun canSustainPlant(
        state: BlockState,
        level: BlockGetter,
        soilPosition: BlockPos,
        facing: Direction,
        plant: BlockState
    ): TriState {

    }

}