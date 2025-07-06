package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class EnderMailboxBlock : Block(Properties.ofFullCopy(Blocks.IRON_BLOCK)) {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(IS_FLAG_RAISED, false)
				.setValue(FACING, Direction.NORTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(IS_FLAG_RAISED, FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirections.first { it.axis.isHorizontal })
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		return canSupportCenter(level, pos.below(), Direction.UP)
	}

	override fun updateShape(state: BlockState, direction: Direction, neighborState: BlockState, level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos): BlockState {
		return if (state.canSurvive(level, pos)) {
			state
		} else {
			Blocks.AIR.defaultBlockState()
		}
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		val direction = state.getValue(FACING)

		return when (direction) {
			Direction.NORTH, Direction.SOUTH -> SHAPE_NS
			Direction.EAST, Direction.WEST -> SHAPE_EW
			else -> SHAPE_NS
		}
	}

	companion object {
		val IS_FLAG_RAISED: BooleanProperty = BooleanProperty.create("is_flag_raised")
		val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING

		val SHAPE_NS: VoxelShape = box(5.0, 0.0, 1.0, 11.0, 22.0, 15.0)
		val SHAPE_EW: VoxelShape = box(1.0, 0.0, 5.0, 15.0, 22.0, 11.0)
	}

}