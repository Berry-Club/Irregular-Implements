package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockTeleporterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
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
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class BlockTeleporterBlock : Block(Properties.ofFullCopy(Blocks.DISPENSER)), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(TRIGGERED, false)
		)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING, TRIGGERED)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return BlockTeleporterBlockEntity(pos, state)
	}

	override fun useWithoutItem(
		pState: BlockState,
		pLevel: Level,
		pPos: BlockPos,
		pPlayer: Player,
		pHitResult: BlockHitResult
	): InteractionResult {
		val blockEntity = pLevel.getBlockEntity(pPos) as? BlockTeleporterBlockEntity
			?: return InteractionResult.FAIL
		pPlayer.openMenu(blockEntity)
		return InteractionResult.SUCCESS
	}

	override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
		val isPowered = level.hasNeighborSignal(pos)
		val wasPowered = state.getValue(TRIGGERED)

		if (isPowered && !wasPowered) {
			val be = level.getBlockEntity(pos) as? BlockTeleporterBlockEntity
			be?.swapBlocks()

			val newState = state.setValue(TRIGGERED, true)
			level.setBlockAndUpdate(pos, newState)
		} else if (!isPowered && wasPowered) {
			val newState = state.setValue(TRIGGERED, false)
			level.setBlockAndUpdate(pos, newState)
		}
	}

	companion object {
		val FACING: DirectionProperty = BlockStateProperties.FACING
		val TRIGGERED: BooleanProperty = BlockStateProperties.TRIGGERED
	}

}