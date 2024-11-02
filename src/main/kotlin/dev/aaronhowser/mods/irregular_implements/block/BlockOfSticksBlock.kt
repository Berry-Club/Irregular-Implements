package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

class BlockOfSticksBlock(
    val returning: Boolean
) : Block(
    Properties.of()
        .mapColor(MapColor.SAND)
        .sound(SoundType.SCAFFOLDING)
        .strength(0.3f)
        .pushReaction(PushReaction.DESTROY)
        .isRedstoneConductor(Blocks::never)
        .isValidSpawn(Blocks::never)
        .isSuffocating(Blocks::never)
        .isViewBlocking(Blocks::never)
        .noOcclusion()
) {

    companion object {
        val SHOULD_DROP: BooleanProperty = BooleanProperty.create("should_drop")
    }

    init {
        registerDefaultState(
            defaultBlockState()
                .setValue(SHOULD_DROP, true)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(SHOULD_DROP)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(
            SHOULD_DROP,
            !context.player?.hasInfiniteMaterials().isTrue  // Only drop if placed by a player with infinite materials
        )
    }

    override fun onPlace(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pOldState: BlockState,
        pMovedByPiston: Boolean
    ) {
        if (!pLevel.isClientSide) pLevel.scheduleTick(pPos, this, 20 * 7)
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston)
    }

    override fun tick(
        pState: BlockState,
        pLevel: ServerLevel,
        pPos: BlockPos,
        pRandom: RandomSource
    ) {
        val shouldDrop = pState.getValue(SHOULD_DROP)

        if (!this.returning) {
            pLevel.destroyBlock(pPos, shouldDrop)
            return super.tick(pState, pLevel, pPos, pRandom)
        }

        if (shouldDrop) {
            val nearestPlayer = pLevel.getNearestPlayer(
                pPos.x.toDouble(),
                pPos.y.toDouble(),
                pPos.z.toDouble(),
                100.0,
                false   // "Should exclude creative players" == false
            )

            val drops = getDrops(pState, pLevel, pPos, null)
            for (drop in drops) {
                popResource(pLevel, nearestPlayer?.blockPosition() ?: pPos, drop)
            }
        }

        pLevel.destroyBlock(pPos, false)
        super.tick(pState, pLevel, pPos, pRandom)
    }

}