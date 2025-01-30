package dev.aaronhowser.mods.irregular_implements.datagen.datapack.features

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

class NatureCoreFeature : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {

    companion object {
        private val natureCore: BlockState = ModBlocks.NATURE_CORE.get().defaultBlockState()
        private val logX = Blocks.JUNGLE_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)
        private val logY = Blocks.JUNGLE_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)
        private val logZ = Blocks.JUNGLE_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z)
        private val leaves = Blocks.JUNGLE_LEAVES.defaultBlockState()
    }

    override fun place(context: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        val origin = context.origin()
        val level = context.level()

        for (dX in -1..1) for (dY in -1..1) for (dZ in -1..1) {

            // Center block
            if (dX == 0 && dY == 0 && dZ == 0) {
                level.setBlock(origin.offset(dX, dY, dZ), natureCore, 1 or 3)
                continue
            }

            if (dX == 0 || dZ == 0) {
                level.setBlock(origin.offset(dX, dY, dZ), leaves, 1 or 3)
                continue
            }

        }

        return true
    }

}