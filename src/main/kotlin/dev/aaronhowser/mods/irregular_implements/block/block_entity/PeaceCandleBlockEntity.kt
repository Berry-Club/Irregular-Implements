package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.PeaceCandleChunks
import dev.aaronhowser.mods.irregular_implements.RainShieldChunks
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PeaceCandleBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.PEACE_CANDLE.get(), pPos, pBlockState) {

    companion object {
        @JvmStatic
        fun chunkIsPreventingMonsterSpawns(level: LevelReader, blockPos: BlockPos): Boolean {
            if (!level.isAreaLoaded(blockPos, 1)) return false
            if (level !is RainShieldChunks) return false

            val chunkPos = level.getChunk(blockPos).pos.toLong()
            return level.`irregular_implements$chunkHasRainShield`(chunkPos)
        }

        fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: RainShieldBlockEntity
        ) {
            if (level !is PeaceCandleChunks) return

            val chunkPos = ChunkPos(blockPos.x.shr(4), blockPos.z.shr(4))

            val checkRadius = ServerConfig.PEACE_CANDLE_CHUNK_RADIUS.get()
            val chunkX = chunkPos.x
            val chunkZ = chunkPos.z

            for (x in (chunkX - checkRadius)..(chunkX + checkRadius)) {
                for (z in (chunkZ - checkRadius)..(chunkZ + checkRadius)) {
                    level.`irregular_implements$addPeaceCandleChunk`(ChunkPos.asLong(x, z))
                }
            }
        }
    }

}