package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.SlimeCubeBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
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

	override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
		val isPowered = level.hasNeighborSignal(pos)
		val wasPowered = state.getValue(POWERED)

		if (isPowered != wasPowered) {
			val newState = state.setValue(POWERED, isPowered)
			level.setBlockAndUpdate(pos, newState)
		}
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return SlimeCubeBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntityTypes.SLIME_CUBE.get(),
			SlimeCubeBlockEntity::tick
		)
	}

	companion object {
		val POWERED: BooleanProperty = BlockStateProperties.POWERED
		val SHAPE: VoxelShape = box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0)
	}

}