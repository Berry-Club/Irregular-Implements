package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block_entity.InventoryRerouterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class InventoryRerouterBlock : Block(Properties.ofFullCopy(Blocks.IRON_BLOCK)), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return InventoryRerouterBlockEntity(pos, state)
	}

	override fun useWithoutItem(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		val exposedSide = hitResult.direction
		val front = state.getValue(FACING)

		if (exposedSide == front) return InteractionResult.PASS

		if (!level.isClientSide) {
			val blockEntity = level.getBlockEntity(pos) as? InventoryRerouterBlockEntity
				?: return InteractionResult.PASS

			blockEntity.cycleSide(exposedSide)
		}

		return InteractionResult.sidedSuccess(level.isClientSide)
	}

	companion object {
		val FACING: DirectionProperty = BlockStateProperties.FACING
	}

}