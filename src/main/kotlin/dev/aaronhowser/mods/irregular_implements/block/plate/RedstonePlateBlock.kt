package dev.aaronhowser.mods.irregular_implements.block.plate

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.toVec3
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class RedstonePlateBlock : BasePlateBlock() {

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
		val otherDirection = state.getValue(if (property == INPUT) OUTPUT else INPUT)

		if (clickedDirection == otherDirection || clickedDirection == state.getValue(property)) {
			return InteractionResult.FAIL
		}

		if (!level.isClientSide) {
			level.setBlockAndUpdate(pos, state.setValue(property, clickedDirection))
		}

		return InteractionResult.sidedSuccess(level.isClientSide)
	}

	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		val entry = PlateMovement.getEntryDirection(pos, entity) ?: return
		val input = state.getValue(INPUT)
		val output = state.getValue(OUTPUT)

		val destination = when (entry) {
			input -> if (level.hasNeighborSignal(pos)) input.opposite else output
			output -> if (level.hasNeighborSignal(pos)) output.opposite else input
			else -> return
		}

		PlateMovement.redirect(pos, entity, entry, destination)
	}

	companion object {
		val INPUT: DirectionProperty = DirectionProperty.create("input", Direction.Plane.HORIZONTAL)
		val OUTPUT: DirectionProperty = DirectionProperty.create("output", Direction.Plane.HORIZONTAL)
	}

}
