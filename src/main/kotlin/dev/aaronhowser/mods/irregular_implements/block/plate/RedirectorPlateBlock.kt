package dev.aaronhowser.mods.irregular_implements.block.plate

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
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.toVec3

class RedirectorPlateBlock : BasePlateBlock() {

    companion object {
        val INPUT_DIRECTION: DirectionProperty = DirectionProperty.create("input", Direction.Plane.HORIZONTAL)
        val OUTPUT_DIRECTION: DirectionProperty = DirectionProperty.create("output", Direction.Plane.HORIZONTAL)
    }


    init {
        registerDefaultState(
            defaultBlockState()
                .setValue(INPUT_DIRECTION, Direction.NORTH)
                .setValue(OUTPUT_DIRECTION, Direction.NORTH)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(INPUT_DIRECTION, OUTPUT_DIRECTION)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(INPUT_DIRECTION, context.horizontalDirection.opposite)
            .setValue(OUTPUT_DIRECTION, context.horizontalDirection)
    }

    override fun useWithoutItem(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hitResult: BlockHitResult
    ): InteractionResult {
        if (level.isClientSide) return InteractionResult.PASS

        val currentInput = state.getValue(INPUT_DIRECTION)
        val currentOutput = state.getValue(OUTPUT_DIRECTION)

        val centerPos = pos.toVec3().add(0.5, 0.0, 0.5)
        val deltaVec = centerPos.vectorTo(hitResult.location)

        val direction = Direction.getNearest(deltaVec.x, deltaVec.y, deltaVec.z)

        if (direction == currentInput || direction == currentOutput) return InteractionResult.FAIL

        val newState = state.setValue(OUTPUT_DIRECTION, direction)
        level.setBlockAndUpdate(pos, newState)

        return InteractionResult.SUCCESS
    }

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        val entityMotion = entity.deltaMovement
        val motionDirection = Direction.getNearest(entityMotion.x, entityMotion.y, entityMotion.z)

        val blockCenter = pos.toVec3().add(0.5, 0.0, 0.5)
        val differenceVec = blockCenter.subtract(entity.position())

    }

}