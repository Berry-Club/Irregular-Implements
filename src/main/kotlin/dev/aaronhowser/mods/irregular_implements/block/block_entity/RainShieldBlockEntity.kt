package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.RainShieldBlock
import dev.aaronhowser.mods.irregular_implements.mixin.RainShieldsPerChunk
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
            val chunk = level.getChunk(blockPos) as? RainShieldsPerChunk ?: return false

            return chunk.`irregular_implements$getRainShieldCount`() > 0
        }

        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState) {
            if (!blockState.getValue(RainShieldBlock.ENABLED)) return
            val chunk = level.getChunkAt(blockPos) as? RainShieldsPerChunk ?: return

            val currentAmount = chunk.`irregular_implements$getRainShieldCount`()
            chunk.`irregular_implements$setRainShieldCount`(currentAmount + 1)
        }

    }

}