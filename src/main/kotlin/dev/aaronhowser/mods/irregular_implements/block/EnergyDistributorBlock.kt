package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnergyDistributorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
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

class EnergyDistributorBlock : Block(
	Properties.ofFullCopy(Blocks.IRON_BLOCK)
), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return EnergyDistributorBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntities.ENERGY_DISTRIBUTOR.get(),
			EnergyDistributorBlockEntity::tick
		)
	}

	companion object {
		val FACING = BlockStateProperties.FACING
	}

}