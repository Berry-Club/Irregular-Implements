package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IgniterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult
import java.util.*

class IgniterBlock(
	properties: Properties = Properties.ofFullCopy(Blocks.DISPENSER)
) : DirectionalBlock(properties), EntityBlock {

	companion object {
		val CODEC: MapCodec<IgniterBlock> = simpleCodec(::IgniterBlock)

		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
	}

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(ENABLED, false)
		)
	}

	override fun codec(): MapCodec<IgniterBlock> {
		return CODEC
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING, ENABLED)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	override fun isFireSource(state: BlockState, level: LevelReader, pos: BlockPos, direction: Direction): Boolean {
		return direction == state.getValue(FACING)
	}

	override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
		return direction == state.getValue(FACING)
	}

	override fun neighborChanged(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		neighborBlock: Block,
		neighborPos: BlockPos,
		movedByPiston: Boolean
	) {
		if (level.isClientSide) return

		val isPowered = level.hasNeighborSignal(pos)
		val wasEnabled = state.getValue(ENABLED)

		if (isPowered != wasEnabled) {
			val newState = state.setValue(ENABLED, isPowered)
			level.setBlockAndUpdate(pos, newState)
		}

		val blockEntity = level.getBlockEntity(pos) as? IgniterBlockEntity ?: return

		blockEntity.blockUpdated(isPowered, wasEnabled)
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		if (level.isClientSide) return InteractionResult.SUCCESS

		val blockEntity = level.getBlockEntity(pos) as? IgniterBlockEntity
			?: return InteractionResult.SUCCESS

		player.openMenu(blockEntity)

		return InteractionResult.SUCCESS
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return IgniterBlockEntity(pos, state)
	}

}