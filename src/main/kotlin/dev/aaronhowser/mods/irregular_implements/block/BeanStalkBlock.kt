package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class BeanStalkBlock(
    val isStrong: Boolean
) : Block(
    Properties
        .ofFullCopy(Blocks.BAMBOO)
        .offsetType(OffsetType.NONE)
) {

    companion object {
        val SHAPE: VoxelShape = box(6.4, 0.0, 6.4, 9.6, 16.0, 9.6)

        @JvmStatic
        fun climbingFactor(livingEntity: LivingEntity): Float {
            val blockState = livingEntity.inBlockState

            return when {
                blockState.`is`(ModBlocks.BEAN_STALK.get()) -> 3f
                blockState.`is`(ModBlocks.LESSER_BEAN_STALK.get()) -> 2f
                else -> 1f
            }
        }
    }

    override fun updateShape(state: BlockState, direction: Direction, neighborState: BlockState, level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos): BlockState {
        return if (state.canSurvive(level, pos)) {
            super.updateShape(state, direction, neighborState, level, pos, neighborPos)
        } else {
            Blocks.AIR.defaultBlockState()
        }
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val stateBelow = level.getBlockState(pos.below())

        return stateBelow.`is`(this) || stateBelow.`is`(BlockTags.DIRT)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

}