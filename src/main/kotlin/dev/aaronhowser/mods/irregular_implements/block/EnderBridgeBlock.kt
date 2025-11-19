package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.scheduler.SchedulerExtensions.scheduleTaskInTicks
import dev.aaronhowser.mods.irregular_implements.EnderAnchorCarrier
import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderAnchorBlockEntity.Companion.getEnderAnchorPositions
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModSounds
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.AABB

class EnderBridgeBlock(
	val distancePerTick: Int
) : Block(
	Properties
		.ofFullCopy(Blocks.OBSIDIAN)
) {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(ENABLED, false)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING, ENABLED)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	//TODO: Bring back particles
	override fun neighborChanged(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		block: Block,
		fromPos: BlockPos,
		isMoving: Boolean
	) {
		if (level !is ServerLevel || level !is EnderAnchorCarrier) return

		val isPowered = level.hasNeighborSignal(pos)
		val wasPowered = state.getValue(ENABLED)

		if (isPowered == wasPowered) return
		val newState = state.setValue(ENABLED, isPowered)
		level.setBlockAndUpdate(pos, newState)

		if (!isPowered) return

		val direction = state.getValue(FACING)

		val anchors = level.getEnderAnchorPositions()

		val anchorsOnAxis = anchors.filter { anchorPos ->
			when (direction.axis) {
				Direction.Axis.X -> anchorPos.z == pos.z && anchorPos.y == pos.y
				Direction.Axis.Z -> anchorPos.x == pos.x && anchorPos.y == pos.y
				Direction.Axis.Y -> anchorPos.x == pos.x && anchorPos.z == pos.z
			}
		}

		var closestAnchor: BlockPos? = null
		var closestDistance = Int.MAX_VALUE

		for (anchorPos in anchorsOnAxis) {
			val distance = when (direction) {
				Direction.NORTH -> pos.z - anchorPos.z
				Direction.SOUTH -> anchorPos.z - pos.z
				Direction.WEST -> pos.x - anchorPos.x
				Direction.EAST -> anchorPos.x - pos.x
				Direction.UP -> anchorPos.y - pos.y
				Direction.DOWN -> pos.y - anchorPos.y
			}

			if (distance < 0) continue // Anchor is behind the bridge
			if (distance < closestDistance) {
				closestDistance = distance
				closestAnchor = anchorPos
			}
		}

		val tickDuration = if (closestAnchor == null) {
			20 * 5
		} else {
			closestDistance / distancePerTick
		}

		level.scheduleTaskInTicks(tickDuration) {
			if (level.getBlockState(pos).getValue(ENABLED)) {
				if (closestAnchor != null && level.getBlockState(closestAnchor).`is`(ModBlocks.ENDER_ANCHOR)) {
					foundAnchor(level, pos, closestAnchor)
				} else {
					turnOffBridge(level, pos, bridgeFailed = true)
				}
			}
		}

	}

	companion object {
		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
		val FACING: DirectionProperty = BlockStateProperties.FACING

		//FIXME: Sometimes doesn't grab players that are stepping on it
		private fun getEntities(level: Level, bridgePos: BlockPos): List<Entity> {
			return level.getEntities(
				null,
				AABB.ofSize(bridgePos.center, 1.25, 2.5, 1.25)
			)
		}

		private fun foundAnchor(
			level: Level,
			bridgePos: BlockPos,
			anchorPos: BlockPos
		) {
			for (entity in getEntities(level, bridgePos)) {
				entity.teleportTo(
					anchorPos.x + 0.5,
					anchorPos.y + 1.0,
					anchorPos.z + 0.5
				)
			}

			level.playSound(
				null,
				anchorPos.above(),
				SoundEvents.ENDERMAN_TELEPORT,
				SoundSource.BLOCKS
			)

			turnOffBridge(level, bridgePos, bridgeFailed = false)
		}

		private fun turnOffBridge(
			level: Level,
			bridgePos: BlockPos,
			bridgeFailed: Boolean
		) {
			val state = level.getBlockState(bridgePos)
			if (!state.getValue(ENABLED)) return

			val newState = state.setValue(ENABLED, false)
			level.setBlockAndUpdate(bridgePos, newState)

			val soundEvent = if (bridgeFailed) ModSounds.FART.get() else SoundEvents.ENDERMAN_TELEPORT

			level.playSound(
				null,
				bridgePos.above(),
				soundEvent,
				SoundSource.BLOCKS
			)
		}
	}

}