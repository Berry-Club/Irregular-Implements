package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import java.awt.Color

class SpectreCoilBlock private constructor(
    val type: Type
) : Block(
    Properties
        .ofFullCopy(Blocks.IRON_BLOCK)
) {

    companion object {
        val FACING: DirectionProperty = BlockStateProperties.FACING

        private const val HEIGHT = 0.09375

        var SHAPE_NORTH: VoxelShape = box(0.3125, 0.3125, 1.0 - HEIGHT, 0.6875, 0.6875, 1.0)
        var SHAPE_SOUTH: VoxelShape = box(0.3125, 0.3125, 0.0, 0.6875, 0.6875, HEIGHT)
        var SHAPE_WEST: VoxelShape = box(1.0 - HEIGHT, 0.3125, 0.3125, 1.0, 0.6875, 0.6875)
        var SHAPE_EAST: VoxelShape = box(0.0, 0.3125, 0.3125, HEIGHT, 0.6875, 0.6875)
        var SHAPE_UP: VoxelShape = box(0.3125, 0.0, 0.3125, 0.6875, HEIGHT, 0.6875)
        var SHAPE_DOWN: VoxelShape = box(0.3125, 1.0f - HEIGHT, 0.3125, 0.6875, 1.0, 0.6875)

        val BASIC = SpectreCoilBlock(Type.BASIC)
        val REDSTONE = SpectreCoilBlock(Type.REDSTONE)
        val ENDER = SpectreCoilBlock(Type.ENDER)
        val NUMBER = SpectreCoilBlock(Type.NUMBER)
        val GENESIS = SpectreCoilBlock(Type.GENESIS)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.DOWN)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(DirectionalBlock.FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(DirectionalBlock.FACING, context.clickedFace.opposite)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return when (state.getValue(DirectionalBlock.FACING)) {
            Direction.NORTH -> SHAPE_NORTH
            Direction.SOUTH -> SHAPE_SOUTH
            Direction.WEST -> SHAPE_WEST
            Direction.EAST -> SHAPE_EAST
            Direction.UP -> SHAPE_UP
            Direction.DOWN -> SHAPE_DOWN
            else -> SHAPE_NORTH
        }
    }

    enum class Type(val id: String, val color: Int) {
        BASIC("basic", Color.CYAN.rgb),
        REDSTONE("redstone", Color.RED.rgb),
        ENDER("ender", Color(200, 0, 210).rgb),
        NUMBER("number", Color.GREEN.rgb),
        GENESIS("genesis", Color.ORANGE.rgb),
    }

}