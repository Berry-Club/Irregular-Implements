package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.RainShieldChunks
import dev.aaronhowser.mods.irregular_implements.block.RainShieldBlock
import dev.aaronhowser.mods.irregular_implements.config.CommonConfig
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

/**
 * The majority of the logic is in [dev.aaronhowser.mods.irregular_implements.mixin.LevelMixin],
 * but some is also in [dev.aaronhowser.mods.irregular_implements.mixin.BiomeMixin] and [dev.aaronhowser.mods.irregular_implements.mixin.LevelRendererMixin]
 */
class RainShieldBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.RAIN_SHIELD.get(), pPos, pBlockState) {

    companion object {

        fun chunkHasActiveRainShield(level: LevelReader, blockPos: BlockPos): Boolean {
            if (!level.isAreaLoaded(blockPos, 1)) return false
            if (level !is RainShieldChunks) return false

            val chunkPos = level.getChunk(blockPos).pos.toLong()
            return level.`irregular_implements$chunkPosHasRainShields`(chunkPos)
        }

        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState) {
            if (level !is RainShieldChunks) return

            if (blockState.getValue(RainShieldBlock.ENABLED)) {
                val chunkPos = ChunkPos(blockPos.x.shr(4), blockPos.z.shr(4))

                val checkRadius = CommonConfig.RAIN_SHIELD_CHUNK_RADIUS.get()
                val chunkX = chunkPos.x
                val chunkZ = chunkPos.z

                for (x in (chunkX - checkRadius)..(chunkX + checkRadius)) {
                    for (z in (chunkZ - checkRadius)..(chunkZ + checkRadius)) {
                        level.`irregular_implements$addChunkPos`(ChunkPos.asLong(x, z))
                    }
                }
            }
        }
    }

}