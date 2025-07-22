package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDetectorBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
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

class BlockDetectorBlock : Block(Properties.ofFullCopy(Blocks.DISPENSER)), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(BlockStateProperties.POWERED, false)
		)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return BlockDetectorBlockEntity(pos, state)
	}

	override fun useWithoutItem(
		pState: BlockState,
		pLevel: Level,
		pPos: BlockPos,
		pPlayer: Player,
		pHitResult: BlockHitResult
	): InteractionResult {
		val blockEntity = pLevel.getBlockEntity(pPos) as? BlockDetectorBlockEntity
			?: return InteractionResult.FAIL
		pPlayer.openMenu(blockEntity)
		return InteractionResult.SUCCESS
	}

	override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
		val be = level.getBlockEntity(pos) as? BlockDetectorBlockEntity
			?: return

		val isDetectingBlock = be.isBlockDetected()
		val wasDetectingBlock = state.getValue(TRIGGERED)

		if (isDetectingBlock && !wasDetectingBlock) {
			level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, true))
		} else if (!isDetectingBlock && wasDetectingBlock) {
			level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, false))
		}
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		val facing = state.getValue(FACING)
		return direction != facing
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return if (state.getValue(TRIGGERED)) 15 else 0
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return getDirectSignal(state, level, pos, direction)
	}

	companion object {
		val FACING: DirectionProperty = BlockStateProperties.FACING
		val TRIGGERED: BooleanProperty = BlockStateProperties.TRIGGERED
	}

}