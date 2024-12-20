package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.core.BlockPos
import net.minecraft.world.item.Item
import net.minecraft.world.level.BlockAndTintGetter

class SpectreIlluminatorItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {

        private val illuminatedChunks: HashSet<Int> = HashSet()

        @JvmStatic
        fun isChunkIlluminated(blockPos: BlockPos, blockAndTintGetter: BlockAndTintGetter): Boolean {

            return false
        }

    }

}