package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty

class ContactLeverBlock : Block(
	Properties
		.ofFullCopy(Blocks.STONE)
) {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(ENABLED, false)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING, ENABLED)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return if (state.getValue(ENABLED)) 15 else 0
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return if (state.getValue(ENABLED)) 15 else 0
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		return direction != state.getValue(FACING).opposite
	}

	companion object {
		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
		val FACING: DirectionProperty = BlockStateProperties.FACING

		fun handleClickBlock(
			level: Level,
			pos: BlockPos,
		) {
			if (level.isClientSide) return

			for (direction in Direction.entries) {
				val blockPos = pos.relative(direction)
				val blockState = level.getBlockState(blockPos)

				if (!blockState.isBlock(ModBlocks.CONTACT_LEVER.get())) continue
				if (blockState.getValue(FACING) != direction.opposite) continue

				pull(level, blockPos, blockState)
			}
		}

		private fun pull(
			level: Level,
			pos: BlockPos,
			blockState: BlockState
		) {
			val newState = blockState.cycle(ENABLED)
			level.setBlockAndUpdate(pos, newState)

			level.playSound(
				null,
				pos,
				SoundEvents.LEVER_CLICK,
				SoundSource.BLOCKS,
				0.3f,
				if (newState.getValue(ENABLED)) 0.6f else 0.5f
			)
		}
	}

}