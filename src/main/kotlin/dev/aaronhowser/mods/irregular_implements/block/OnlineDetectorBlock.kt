package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.OnlineDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isServerSide
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
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
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class OnlineDetectorBlock : EntityBlock, Block(Properties.ofFullCopy(Blocks.DISPENSER)) {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(ENABLED, false)
				.setValue(HORIZONTAL_FACING, Direction.NORTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(ENABLED, HORIZONTAL_FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(ENABLED, false)
			.setValue(HORIZONTAL_FACING, context.horizontalDirection)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return OnlineDetectorBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
		return if (level.isClientSide) {
			null
		} else {
			BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntityTypes.ONLINE_DETECTOR.get(), OnlineDetectorBlockEntity::tick)
		}
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {

		if (level.isServerSide) {
			val blockEntity = level.getBlockEntity(pos) as? OnlineDetectorBlockEntity ?: return InteractionResult.FAIL
			player.openMenu(blockEntity)
			blockEntity.sendStringUpdate()
		}

		return InteractionResult.SUCCESS
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		return true
	}

	override fun isSignalSource(state: BlockState): Boolean {
		return true
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return if (state.getValue(ENABLED)) 15 else 0
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return getDirectSignal(state, level, pos, direction)
	}

	companion object {
		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
		val HORIZONTAL_FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
	}

}