package dev.aaronhowser.mods.irregular_implements.block.plate

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.toVec3
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.Mth
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

	companion object {
		val ACTIVE_ONE: DirectionProperty = DirectionProperty.create("active_one", Direction.Plane.HORIZONTAL)
		val ACTIVE_TWO: DirectionProperty = DirectionProperty.create("active_two", Direction.Plane.HORIZONTAL)
	}


	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(ACTIVE_ONE, Direction.NORTH)
				.setValue(ACTIVE_TWO, Direction.NORTH)
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

		val newState = state.setValue(ACTIVE_TWO, direction)
		level.setBlockAndUpdate(pos, newState)

		return InteractionResult.SUCCESS
	}

	//FIXME: Seems to not work great for players, but works fine for everything else
	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		val entityMotion = entity.deltaMovement
		if (entityMotion.horizontalDistanceSqr() < 0.001) return

		val movingInDirection = Direction.getNearest(entityMotion.x, 0.0, entityMotion.z)

		val blockCenter = pos.toVec3().add(0.5, 0.0, 0.5)
		val entityDisplacementVector = blockCenter.vectorTo(entity.position())
		val entityComingFromDirection = Direction.getNearest(entityDisplacementVector.x, 0.0, entityDisplacementVector.z)

		val currentOne = state.getValue(ACTIVE_ONE)
		val currentTwo = state.getValue(ACTIVE_TWO)

		val moveToDirection = if (entityComingFromDirection == currentOne && movingInDirection == currentOne.opposite) {
			currentTwo
		} else if (entityComingFromDirection == currentTwo && movingInDirection == currentTwo.opposite) {
			currentOne
		} else {
			return
		}

		val moveToPos = moveToDirection.normal.toVec3().scale(0.4).add(blockCenter)

		val lookAngleDifference = entityComingFromDirection.opposite.toYRot() - moveToDirection.toYRot()
		val outputMotion = entityMotion.yRot(Mth.DEG_TO_RAD * lookAngleDifference)

		entity.teleportTo(moveToPos.x, entity.y, moveToPos.z)
		entity.deltaMovement = outputMotion
	}

}