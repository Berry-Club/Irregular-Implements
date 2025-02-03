package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration

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
    }

}