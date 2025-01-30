package dev.aaronhowser.mods.irregular_implements.datagen.datapack.features

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

class NatureCoreFeature : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {

    companion object {
        private val natureCore: BlockState = ModBlocks.NATURE_CORE.get().defaultBlockState()
        private val log = Blocks.JUNGLE_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)
        private val leaves = Blocks.JUNGLE_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)
    }

    override fun place(context: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        val origin = context.origin()
        val level = context.level()



        for (dX in -1..1) for (dY in -1..1) for (dZ in -1..1) {

            val offsetPos = origin.offset(dX, dY, dZ)

            if (dY == 0) {
                if (dX == 0 && dZ == 0) {
                    level.setBlock(offsetPos, natureCore, 1 or 3)
                } else if (dX == 0 || dZ == 0) {
                    level.setBlock(offsetPos, leaves, 1 or 3)
                } else {
                    level.setBlock(offsetPos, log, 1 or 3)
                }

                continue
            }

            if ((dX == 0 || dZ == 0) && dX != dZ) {
                level.setBlock(offsetPos, log, 1 or 3)
            } else {
                level.setBlock(offsetPos, leaves, 1 or 3)
            }

        }

        return true
    }

}