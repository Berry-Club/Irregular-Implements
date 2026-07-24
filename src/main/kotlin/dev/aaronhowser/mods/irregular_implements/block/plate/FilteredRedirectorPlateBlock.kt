package dev.aaronhowser.mods.irregular_implements.block.plate

import dev.aaronhowser.mods.irregular_implements.block_entity.FilteredRedirectorPlateBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class FilteredRedirectorPlateBlock : BasePlateBlock(), EntityBlock {

	init {
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH))
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState().setValue(FACING, context.horizontalDirection)
	}

	override fun useWithoutItem(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		val blockEntity = level.getBlockEntity(pos) as? FilteredRedirectorPlateBlockEntity
			?: return InteractionResult.PASS

		if (!level.isClientSide) player.openMenu(blockEntity)
		return InteractionResult.sidedSuccess(level.isClientSide)
	}

	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		val entry = PlateMovement.getEntryDirection(pos, entity) ?: return
		val facing = state.getValue(FACING)
		if (entry != facing && entry != facing.opposite) return

		val blockEntity = level.getBlockEntity(pos) as? FilteredRedirectorPlateBlockEntity ?: return
		val output = when {
			blockEntity.matchesFilter(0, entity) -> facing.counterClockWise
			blockEntity.matchesFilter(1, entity) -> facing.clockWise
			else -> entry.opposite
		}

		PlateMovement.redirect(pos, entity, entry, output)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return FilteredRedirectorPlateBlockEntity(pos, state)
	}

	companion object {
		val FACING: DirectionProperty = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL)
	}

}
