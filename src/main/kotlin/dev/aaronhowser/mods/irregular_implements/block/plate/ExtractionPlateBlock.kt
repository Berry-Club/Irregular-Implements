package dev.aaronhowser.mods.irregular_implements.block.plate

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.toVec3
import dev.aaronhowser.mods.irregular_implements.block_entity.ExtractionPlateBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class ExtractionPlateBlock : BasePlateBlock(), EntityBlock {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(INPUT, Direction.NORTH)
				.setValue(OUTPUT, Direction.SOUTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(INPUT, OUTPUT)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState()
			.setValue(INPUT, context.horizontalDirection.opposite)
			.setValue(OUTPUT, context.horizontalDirection)
	}

	override fun useWithoutItem(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		if (hitResult.direction != Direction.UP) return InteractionResult.PASS

		val center = pos.toVec3().add(0.5, 0.0, 0.5)
		val delta = center.vectorTo(hitResult.location)
		val clickedDirection = Direction.getNearest(delta.x, 0.0, delta.z)
		val property = if (player.isSecondaryUseActive) INPUT else OUTPUT

		if (!level.isClientSide) {
			level.setBlockAndUpdate(pos, state.setValue(property, clickedDirection))
		}

		return InteractionResult.sidedSuccess(level.isClientSide)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return ExtractionPlateBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntityTypes.EXTRACTION_PLATE.get(),
			ExtractionPlateBlockEntity::tick
		)
	}

	companion object {
		val INPUT: DirectionProperty = DirectionProperty.create("input", Direction.Plane.HORIZONTAL)
		val OUTPUT: DirectionProperty = DirectionProperty.create("output", Direction.Plane.HORIZONTAL)
	}

}
