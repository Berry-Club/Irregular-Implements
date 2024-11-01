package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.RainShieldChunks
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

/**
 * The majority of the logic is in [dev.aaronhowser.mods.irregular_implements.mixin.LevelMixin],
 * but some is also in [dev.aaronhowser.mods.irregular_implements.mixin.BiomeMixin] and [dev.aaronhowser.mods.irregular_implements.mixin.LevelRendererMixin]
 */
class RainShieldBlock : EntityBlock, Block(
    Properties
        .of()
        .sound(SoundType.STONE)
        .strength(2f)
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? = null

    companion object {
        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED

        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState) {
            if (level !is RainShieldChunks) return

            val chunkPos = level.getChunk(blockPos).pos.toLong()
            if (blockState.getValue(ENABLED)) {
                level.`irregular_implements$addChunkPos`(chunkPos)
            }
        }

        fun chunkHasActiveRainShield(level: LevelReader, blockPos: BlockPos): Boolean {
            if (!level.isAreaLoaded(blockPos, 1)) return false
            if (level !is RainShieldChunks) return false

            val chunkPos = level.getChunk(blockPos).pos.toLong()
            return level.`irregular_implements$chunkPosHasRainShields`(chunkPos)
        }
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(ENABLED, true)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(ENABLED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(ENABLED, true)
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
        return true
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

        val redstoneStrength = level.getBestNeighborSignal(pos)
        level.setBlockAndUpdate(pos, state.setValue(ENABLED, redstoneStrength == 0))
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { tLevel, tPos, tState, _ ->
            tick(tLevel, tPos, tState)
        }
    }


}