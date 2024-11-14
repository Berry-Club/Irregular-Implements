package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.ServerScheduler
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.AABB
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.toVec3

class EnderBridgeBlock(
//    val isPrismarine: Boolean,
    properties: Properties = Properties
        .ofFullCopy(Blocks.STONE)
) : DirectionalBlock(properties) {

    companion object {
        val CODEC: MapCodec<EnderBridgeBlock> = simpleCodec(::EnderBridgeBlock)

        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED

        val MAX_ITERATIONS = 100

        /**
         * Searches for an anchor block in the given direction.
         * Searches through unloaded chunks and non-full blocks.
         * Only stops searching if it reaches the maximum iterations, or if it's found an Anchor.
         */
        fun searchForAnchor(
            level: Level,
            bridgePos: BlockPos,
            searchPos: BlockPos,
            direction: Direction,
            iterations: Int
        ) {
            if (iterations > MAX_ITERATIONS) {
                turnOffBridge(level, bridgePos)
                return
            }

            if (level.isLoaded(searchPos)) {
                val state = level.getBlockState(searchPos)
                if (state.`is`(ModBlocks.ENDER_ANCHOR)) {
                    foundAnchor(level, bridgePos, searchPos)
                    return
                }

                if (!state.isCollisionShapeFullBlock(level, searchPos)) {
                    turnOffBridge(level, bridgePos)
                    return
                }
            }

            ServerScheduler.scheduleTaskInTicks(1) {
                searchForAnchor(level, bridgePos, searchPos.relative(direction), direction, iterations + 1)
            }
        }

        private fun foundAnchor(
            level: Level,
            bridgePos: BlockPos,
            anchorPos: BlockPos
        ) {
            val entitiesOnBridge = level.getEntities(
                null,
                AABB.ofSize(bridgePos.toVec3(), 2.5, 2.5, 2.5)
            ).filter { it.blockPosBelowThatAffectsMyMovement == bridgePos }

            for (entity in entitiesOnBridge) {
                entity.teleportTo(
                    anchorPos.x + 0.5,
                    anchorPos.y + 1.0,
                    anchorPos.z + 0.5
                )
            }

            turnOffBridge(level, bridgePos)
        }

        private fun turnOffBridge(
            level: Level,
            bridgePos: BlockPos
        ) {
            val state = level.getBlockState(bridgePos)
            if (!state.getValue(ENABLED)) return

            val newState = state.setValue(ENABLED, false)
            level.setBlockAndUpdate(bridgePos, newState)
        }
    }

    override fun codec(): MapCodec<EnderBridgeBlock> = CODEC

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ContactLever.ENABLED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, ENABLED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.nearestLookingDirection.opposite)
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        block: Block,
        fromPos: BlockPos,
        isMoving: Boolean
    ) {
        if (level.isClientSide) return

        val isPowered = level.hasNeighborSignal(pos)
        if (!isPowered) return
        if (state.getValue(ENABLED)) return

        val newState = state.setValue(ENABLED, true)
        level.setBlockAndUpdate(pos, newState)

        val direction = state.getValue(FACING)
        searchForAnchor(
            level = level,
            bridgePos = pos,
            searchPos = pos.relative(direction),
            direction = direction,
            iterations = 0
        )

    }

}