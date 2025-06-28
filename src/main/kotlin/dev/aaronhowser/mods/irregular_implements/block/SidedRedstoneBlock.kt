package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class SidedRedstoneBlock : Block(
	Properties
		.ofFullCopy(Blocks.REDSTONE_BLOCK)
		.isRedstoneConductor(Blocks::never)
) {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
		)
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		val facing = state.getValue(FACING)

		return direction == facing.opposite
	}

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

	companion object {
		private val FACING = BlockStateProperties.FACING
	}

}