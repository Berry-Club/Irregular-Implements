package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class BeanStalk(
    val isStrong: Boolean
) : Block(Properties.ofFullCopy(Blocks.BAMBOO)) {

    companion object {
        val SHAPE: VoxelShape = box(6.4, 0.0, 6.4, 9.6, 16.0, 9.6)

        @JvmStatic
        fun climbingFactor(livingEntity: LivingEntity): Float {
            val blockState = livingEntity.inBlockState

            return when {
                blockState.`is`(ModBlocks.BEAN_STALK.get()) -> 1.75f
                blockState.`is`(ModBlocks.LESSER_BEAN_STALK.get()) -> 1.5f
                else -> 1f
            }
        }

    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

}