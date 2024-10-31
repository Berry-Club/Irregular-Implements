package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.RainShieldBlock
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RainShieldBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.RAIN_SHIELD.get(), pPos, pBlockState) {

    companion object {

        // For if the same block is checked twice in a tick. At the end of every tick this is cleared, on both server and client
        val rainCache: ConcurrentHashMap<BlockPos, Boolean> = ConcurrentHashMap()
        val shieldsPerLevel: IdentityHashMap<ResourceKey<Level>, HashMap<BlockPos, RainShieldBlockEntity>> = IdentityHashMap()

        fun isNearActiveRainShield(level: LevelReader, blockPos: BlockPos): Boolean {
            if (level !is Level) return false

            if (rainCache.getOrDefault(blockPos, false)) {
                return true
            }

            val shieldsThisLevel = shieldsPerLevel[level.dimension()] ?: return false
            for (shield in shieldsThisLevel.values) {
                if (shield.level != level) continue
                if (shield.isRemoved) continue
                if (!shield.blockState.getValue(RainShieldBlock.ENABLED)) continue
                if (!shield.blockPos.closerThan(blockPos, 10.0)) continue

                rainCache[blockPos] = true
                return true
            }

            rainCache[blockPos] = false
            return false
        }

    }

    init {
        shieldsPerLevel.computeIfAbsent(level?.dimension()) { HashMap() }[blockPos] = this
    }

}