package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class SakanadeBlock : Block(Properties.ofFullCopy(Blocks.MOSS_CARPET)) {

    companion object {
        @JvmStatic
        fun addToMushroom(
            level: LevelAccessor,
            origin: BlockPos,
            config: HugeMushroomFeatureConfiguration,
            mutablePos: BlockPos.MutableBlockPos
        ) {
            if (mutablePos.x == origin.x && mutablePos.z == origin.z) return

            if (level.getBlockState(mutablePos.below()).canBeReplaced()) {
                level.setBlock(
                    mutablePos.below(),
                    ModBlocks.SAKANADE.get().defaultBlockState(),
                    1 or 2
                )
            }
        }

        val SHAPE: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.empty()
    }

}