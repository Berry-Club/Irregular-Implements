package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

abstract class BasePlateBlock(
	properties: Properties =
		Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)
) : Block(properties) {

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return Shapes.empty()
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		return canSupportRigidBlock(level, pos.below())
	}

	override fun updateShape(
		state: BlockState,
		direction: Direction,
		neighborState: BlockState,
		level: LevelAccessor,
		pos: BlockPos,
		neighborPos: BlockPos
	): BlockState {
		return if (canSurvive(state, level, pos)) {
			state
		} else {
			Blocks.AIR.defaultBlockState()
		}
	}

	companion object {
		val SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 0.5, 16.0)
	}

}