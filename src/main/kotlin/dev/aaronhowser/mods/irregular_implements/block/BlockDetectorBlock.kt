package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.block.SimpleContainerBlock
import dev.aaronhowser.mods.irregular_implements.block_entity.BlockDetectorBlockEntity
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

class BlockDetectorBlock : SimpleContainerBlock(Properties.ofFullCopy(Blocks.DISPENSER)), EntityBlock {

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
		return BlockDetectorBlockEntity(pos, state)
	}

	override fun useWithoutItem(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		val blockEntity = level.getBlockEntity(pos) as? BlockDetectorBlockEntity
			?: return InteractionResult.FAIL
		player.openMenu(blockEntity)
		return InteractionResult.SUCCESS
	}

	override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
		val be = level.getBlockEntity(pos) as? BlockDetectorBlockEntity ?: return
		be.checkAndUpdate()
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		return true
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return if (state.getValue(TRIGGERED)) 15 else 0
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return getDirectSignal(state, level, pos, direction)
	}

	override fun isSignalSource(state: BlockState): Boolean {
		return true
	}

	companion object {
		val FACING: DirectionProperty = BlockStateProperties.FACING
		val TRIGGERED: BooleanProperty = BlockStateProperties.TRIGGERED
	}

}