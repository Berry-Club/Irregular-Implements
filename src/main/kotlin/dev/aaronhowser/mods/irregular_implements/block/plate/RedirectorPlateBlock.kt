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

class RedirectorPlateBlock : BasePlateBlock() {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(ACTIVE_ONE, Direction.NORTH)
				.setValue(ACTIVE_TWO, Direction.SOUTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(ACTIVE_ONE, ACTIVE_TWO)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(ACTIVE_ONE, context.horizontalDirection.opposite)
			.setValue(ACTIVE_TWO, context.horizontalDirection)
	}

	override fun useWithoutItem(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		if (level.isClientSide) return InteractionResult.SUCCESS

		val currentOne = state.getValue(ACTIVE_ONE)
		val currentTwo = state.getValue(ACTIVE_TWO)

		val centerPos = pos.toVec3().add(0.5, 0.0, 0.5)
		val deltaVec = centerPos.vectorTo(hitResult.location)

		val direction = Direction.getNearest(deltaVec.x, deltaVec.y, deltaVec.z)

		if (direction == currentOne || direction == currentTwo) return InteractionResult.FAIL

		val newState = state.setValue(if (player.isSecondaryUseActive) ACTIVE_TWO else ACTIVE_ONE, direction)
		level.setBlockAndUpdate(pos, newState)

		return InteractionResult.SUCCESS
	}

	//FIXME: Seems to not work great for players, but works fine for everything else
	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		val entryDirection = PlateMovement.getEntryDirection(pos, entity) ?: return

		val currentOne = state.getValue(ACTIVE_ONE)
		val currentTwo = state.getValue(ACTIVE_TWO)

		val outputDirection = when (entryDirection) {
			currentOne -> currentTwo
			currentTwo -> currentOne
			else -> return
		}

		PlateMovement.redirect(pos, entity, entryDirection, outputDirection)
	}

	companion object {
		val ACTIVE_ONE: DirectionProperty = DirectionProperty.create("active_one", Direction.Plane.HORIZONTAL)
		val ACTIVE_TWO: DirectionProperty = DirectionProperty.create("active_two", Direction.Plane.HORIZONTAL)
	}

}