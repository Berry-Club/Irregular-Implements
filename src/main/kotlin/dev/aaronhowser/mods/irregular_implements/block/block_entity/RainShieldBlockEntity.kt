package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.RainShieldChunks
import dev.aaronhowser.mods.irregular_implements.block.RainShieldBlock
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RainShieldBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.RAIN_SHIELD.get(), pPos, pBlockState) {

    companion object {

        fun chunkHasActiveRainShield(level: LevelReader, blockPos: BlockPos): Boolean {
            if (!level.isAreaLoaded(blockPos, 1)) return false
            if (level !is RainShieldChunks) return false

            val chunkPos = level.getChunk(blockPos).pos.toLong()
            if (level is Level) {
                println("AAAAAAAAAAAAA CHECKING RAIN SHIELD CHUNK POS ${level.gameTime}")
            }
            return level.`irregular_implements$chunkPosHasRainShields`(chunkPos)
        }

        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState) {
            if (level !is RainShieldChunks) return

            val chunkPos = level.getChunk(blockPos).pos.toLong()
            if (blockState.getValue(RainShieldBlock.ENABLED)) {
                println("AAAAAAAAAAAAA ADDING RAIN SHIELD CHUNK POS ON TICK ${level.gameTime}")
                level.`irregular_implements$addChunkPos`(chunkPos)
            }
        }
    }

}