package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
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

class RainShieldBlock : EntityBlock, Block(
	Properties.ofFullCopy(Blocks.END_ROD)
		.lightLevel { 0 }
) {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(ENABLED, true)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(ENABLED)
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		return true
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	override fun neighborChanged(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		block: Block,
		fromPos: BlockPos,
		isMoving: Boolean
	) {
		if (level.isClientSide) return

		val redstoneStrength = level.getBestNeighborSignal(pos)
		level.setBlockAndUpdate(pos, state.setValue(ENABLED, redstoneStrength == 0))
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = RainShieldBlockEntity(pos, state)

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntities.RAIN_SHIELD.get(),
			RainShieldBlockEntity::tick
		)
	}

	companion object {
		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
		val SHAPE: VoxelShape = box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0)
	}

}