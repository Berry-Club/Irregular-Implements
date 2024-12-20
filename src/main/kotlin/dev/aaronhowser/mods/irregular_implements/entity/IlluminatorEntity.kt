package dev.aaronhowser.mods.irregular_implements.entity

import com.google.common.collect.HashMultimap
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.SectionPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LightLayer

class IlluminatorEntity(
    entityType: EntityType<*>,
    level: Level
) : Entity(entityType, level) {

    companion object {
        private val illuminatedChunks: HashMultimap<Level, Long> = HashMultimap.create()

        @JvmStatic
        fun isChunkIlluminated(blockPos: BlockPos, blockAndTintGetter: BlockAndTintGetter): Boolean {
            if (blockAndTintGetter !is Level) return false

            val chunkPos = ChunkPos(blockPos)

            return illuminatedChunks[blockAndTintGetter].contains(chunkPos.toLong())
        }

        private fun updateLightLevels(level: Level, chunkPos: ChunkPos) {
            for (i in level.lightEngine.minLightSection..level.lightEngine.maxLightSection) {
                level.lightEngine.queueSectionData(LightLayer.SKY, SectionPos.of(chunkPos, i), null)
                level.lightEngine.queueSectionData(LightLayer.BLOCK, SectionPos.of(chunkPos, i), null)
            }
        }
    }

    override fun onAddedToLevel() {
        super.onAddedToLevel()

        val chunkPos = ChunkPos(this.blockPosition())

        illuminatedChunks[level()].add(chunkPos.toLong())
        updateLightLevels(level(), chunkPos)
    }

    override fun onRemovedFromLevel() {
        super.onRemovedFromLevel()

        val chunkPos = ChunkPos(this.blockPosition())

        illuminatedChunks[level()].remove(chunkPos.toLong())
        updateLightLevels(level(), chunkPos)
    }

    constructor(level: Level) : this(ModEntityTypes.ILLUMINATOR.get(), level)

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
    }
}