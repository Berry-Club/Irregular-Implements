package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class SlimeCubeBlock : Block(Properties.ofFullCopy(Blocks.SLIME_BLOCK)), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(POWERED, false)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
		builder.add(POWERED)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState()
			.setValue(POWERED, context.level.hasNeighborSignal(context.clickedPos))
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
		return null
	}

	companion object {
		val POWERED: BooleanProperty = BlockStateProperties.POWERED
		val SHAPE: VoxelShape = box(0.375, 0.375, 0.375, 0.625, 0.625, 0.625)
	}

}