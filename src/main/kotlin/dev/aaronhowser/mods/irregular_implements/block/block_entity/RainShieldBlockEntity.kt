package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.concurrent.ConcurrentHashMap

class RainShieldBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.RAIN_SHIELD.get(), pPos, pBlockState) {

    companion object {

        val rainCache: ConcurrentHashMap<BlockPos, Boolean> = ConcurrentHashMap()
        val shields: MutableSet<RainShieldBlockEntity> = mutableSetOf()

        fun shouldBlockRain(level: Level, blockPos: BlockPos): Boolean {
            if (rainCache.getOrDefault(blockPos, false)) {
                return false
            }

            synchronized(shields) {
                for (shield in shields) {
                    if (
                        shield.level == level
                        && !shield.isRemoved
                        && shield.blockPos.closerThan(blockPos, 10.0)
                    ) {
                        rainCache[blockPos] = true
                        return true
                    }
                }
            }

            rainCache[blockPos] = false
            return false
        }

    }

    init {
        synchronized(shields) {
            shields.add(this)
        }
    }

}