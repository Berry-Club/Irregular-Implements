package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FarmBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType

// Because fuck block codecs, they don't serialize so like why
abstract class NoCodecBushBlock(properties: Properties) : Block(properties) {

	protected open fun mayPlaceOn(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
		return state.isBlock(BlockTags.DIRT) || state.getBlock() is FarmBlock
	}

	override fun updateShape(
		state: BlockState,
		facing: Direction,
		facingState: BlockState,
		level: LevelAccessor,
		currentPos: BlockPos,
		facingPos: BlockPos
	): BlockState {
		return if (!state.canSurvive(level, currentPos))
			Blocks.AIR.defaultBlockState()
		else
			super.updateShape(state, facing, facingState, level, currentPos, facingPos)
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		val blockPos = pos.below()
		val belowBlockState = level.getBlockState(blockPos)
		val soilDecision = belowBlockState.canSustainPlant(level, blockPos, Direction.UP, state)
		if (!soilDecision.isDefault()) return soilDecision.isTrue()
		return this.mayPlaceOn(belowBlockState, level, blockPos)
	}

	override fun propagatesSkylightDown(state: BlockState, reader: BlockGetter, pos: BlockPos): Boolean {
		return state.getFluidState().isEmpty()
	}

	override fun isPathfindable(state: BlockState, pathComputationType: PathComputationType): Boolean {
		return if (pathComputationType == PathComputationType.AIR && !this.hasCollision) true else super.isPathfindable(state, pathComputationType)
	}

}