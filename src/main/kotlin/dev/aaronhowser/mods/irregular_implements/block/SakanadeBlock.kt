package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HugeMushroomBlock
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration

class SakanadeBlock : Block(Properties.ofFullCopy(Blocks.MOSS_CARPET)) {

    companion object {
        @JvmStatic
        fun addToMushroom(
            level: LevelAccessor,
            origin: BlockPos,
            config: HugeMushroomFeatureConfiguration,
            treeHeight: Int
        ) {
            val radius = config.foliageRadius

            for (dX in -radius..radius) for (dZ in -radius..radius) {
                val pos = origin.offset(dX, treeHeight, dZ)
                val stateThere = level.getBlockState(pos)

                if (!stateThere.`is`(Blocks.BROWN_MUSHROOM_BLOCK)) return

                val connectsDown = stateThere.getValue(HugeMushroomBlock.DOWN)
                if (!connectsDown) {
                    level.setBlock(
                        pos.below(),
                        ModBlocks.SAKANADE.get().defaultBlockState(),
                        1 or 2
                    )
                }
            }
        }
    }

}