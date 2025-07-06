package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty

class EnderMailboxBlock : Block(Properties.ofFullCopy(Blocks.IRON_BLOCK)) {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(IS_FLAG_RAISED, false)
				.setValue(FACING, Direction.NORTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
		builder.add(IS_FLAG_RAISED, FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	companion object {
		val IS_FLAG_RAISED: BooleanProperty = BooleanProperty.create("is_flag_raised")
		val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
	}

}