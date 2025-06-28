package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModSounds
import dev.aaronhowser.mods.irregular_implements.util.ServerScheduler
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
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

class EnderBridgeBlock(
    val distancePerTick: Int,
    properties: Properties = Properties
        .ofFullCopy(Blocks.OBSIDIAN)
) : DirectionalBlock(properties) {

    companion object {
        val CODEC: MapCodec<EnderBridgeBlock> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                Codec.INT
                    .fieldOf("distance_per_tick")
                    .forGetter(EnderBridgeBlock::distancePerTick),
                propertiesCodec()
            ).apply(instance, ::EnderBridgeBlock)
        }

        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED

        //FIXME: Sometimes doesn't grab players that are stepping on it
        private fun getEntities(level: Level, bridgePos: BlockPos): List<Entity> {
            return level.getEntities(
                null,
				AABB.ofSize(bridgePos.above().center, 1.25, 2.5, 1.25)
            )
        }

        //TODO: Config
        private const val MAX_ITERATIONS = Int.MAX_VALUE

        /**
         * Searches for an anchor block in the given direction.
         * Searches through unloaded chunks and non-full blocks.
         * Only stops searching if it reaches the maximum iterations, or if it's found an Anchor.
         */
        fun searchForAnchor(
            level: ServerLevel,
            blocksPerIteration: Int,
            bridgePos: BlockPos,
            searchOrigin: BlockPos,
            direction: Direction,
            iterations: Int
        ) {
            if (iterations >= MAX_ITERATIONS) {
                turnOffBridge(level, bridgePos, bridgeFailed = true)

                val component = ModLanguageProvider.Messages.ENDER_BRIDGE_ITERATIONS
                    .toComponent(iterations * blocksPerIteration)

                for (entity in getEntities(level, bridgePos)) {
                    entity.sendSystemMessage(component)
                }

                return
            }

            val players = level.players()

            for (i in 0 until blocksPerIteration) {
                val pos = searchOrigin.relative(direction, i)
                if (!level.isLoaded(pos)) continue

                if (iterations % 4 == 0) {
                    val particleCount = (1 + Mth.ceil(iterations.toDouble() / 20)).coerceIn(1, 100)
                    val particleSpeed = (0.5 + iterations / 30.0).coerceIn(0.5, 3.0)

                    for (player in players) {
                        level.sendParticles(
                            ParticleTypes.PORTAL,
                            bridgePos.x + 0.5,
                            bridgePos.y + 2.0,
                            bridgePos.z + 0.5,
                            particleCount,
                            0.0,
                            0.0,
                            0.0,
                            particleSpeed
                        )
                    }
                }

                val state = level.getBlockState(pos)
                if (state.`is`(ModBlocks.ENDER_ANCHOR)) {

                    //TODO: Particles not spawning?
                    val particleCount = (1 + Mth.ceil(iterations.toDouble() / 20)).coerceIn(10, 100)
                    for (player in players) {
                        level.sendParticles(
                            ParticleTypes.REVERSE_PORTAL,
                            pos.x + 0.5,
                            pos.y + 1.5,
                            pos.z + 0.5,
                            particleCount,
                            0.0,
                            0.0,
                            0.0,
                            0.5
                        )
                    }

                    foundAnchor(level, bridgePos, pos)
                    return
                }

                if (state.isCollisionShapeFullBlock(level, pos)) {
                    turnOffBridge(level, bridgePos, bridgeFailed = true)

                    val blockName = state.block.name

                    val component = ModLanguageProvider.Messages.ENDER_BRIDGE_HIT_BLOCK
                        .toComponent(blockName, pos.x, pos.y, pos.z)

                    for (entity in getEntities(level, bridgePos)) {
                        entity.sendSystemMessage(component)
                    }

                    return
                }
            }

            ServerScheduler.scheduleTaskInTicks(1) {
                searchForAnchor(
                    level = level,
                    blocksPerIteration = blocksPerIteration,
                    bridgePos = bridgePos,
                    searchOrigin = searchOrigin.relative(direction),
                    direction = direction,
                    iterations = iterations + blocksPerIteration
                )
            }
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

    override fun codec(): MapCodec<EnderBridgeBlock> = CODEC

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ContactLeverBlock.ENABLED, false)
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
        if (level !is ServerLevel) return

        val isPowered = level.hasNeighborSignal(pos)
        if (!isPowered) return
        if (state.getValue(ENABLED)) return

        val newState = state.setValue(ENABLED, true)
        level.setBlockAndUpdate(pos, newState)

        val direction = state.getValue(FACING)
        searchForAnchor(
            level = level,
            blocksPerIteration = distancePerTick,
            bridgePos = pos,
            searchOrigin = pos.relative(direction),
            direction = direction,
            iterations = 0
        )
    }

}