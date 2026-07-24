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
				.setValue(POWERED_OUTPUT, Direction.EAST)
				.setValue(UNPOWERED_OUTPUT, Direction.WEST)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(INPUT, POWERED_OUTPUT, UNPOWERED_OUTPUT)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		val input = context.horizontalDirection.opposite

		return defaultBlockState()
			.setValue(INPUT, input)
			.setValue(POWERED_OUTPUT, input.clockWise)
			.setValue(UNPOWERED_OUTPUT, input.opposite)
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

		val property = if (player.isSecondaryUseActive) UNPOWERED_OUTPUT else POWERED_OUTPUT
		val otherProperty = if (property == POWERED_OUTPUT) UNPOWERED_OUTPUT else POWERED_OUTPUT

		if (clickedDirection == state.getValue(INPUT)
			|| clickedDirection == state.getValue(otherProperty)
			|| clickedDirection == state.getValue(property)
		) {
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
		if (entry != input) return

		val output = if (level.hasNeighborSignal(pos)) {
			state.getValue(POWERED_OUTPUT)
		} else {
			state.getValue(UNPOWERED_OUTPUT)
		}

		PlateMovement.redirect(pos, entity, entry, output)
	}

	companion object {
		val INPUT: DirectionProperty = DirectionProperty.create("input", Direction.Plane.HORIZONTAL)
		val POWERED_OUTPUT: DirectionProperty = DirectionProperty.create("powered_output", Direction.Plane.HORIZONTAL)
		val UNPOWERED_OUTPUT: DirectionProperty = DirectionProperty.create("unpowered_output", Direction.Plane.HORIZONTAL)
	}

}