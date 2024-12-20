package dev.aaronhowser.mods.irregular_implements.item

import com.google.common.collect.HashMultimap
import net.minecraft.core.BlockPos
import net.minecraft.world.item.Item
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

class SpectreIlluminatorItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {

        private val illuminatedChunks: HashMultimap<Level, Long> = HashMultimap.create()

        @JvmStatic
        fun isChunkIlluminated(blockPos: BlockPos, blockAndTintGetter: BlockAndTintGetter): Boolean {
            if (blockAndTintGetter !is Level) return false

            val chunkPos = ChunkPos(blockPos)

            return illuminatedChunks[blockAndTintGetter].contains(chunkPos.toLong())
        }

    }

}