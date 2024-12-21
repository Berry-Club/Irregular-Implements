package dev.aaronhowser.mods.irregular_implements.entity

import com.google.common.collect.HashMultimap
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.client.renderer.chunk.RenderChunkRegion        //FIXME: This crashes servers if loaded on them
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

class IlluminatorEntity(
    entityType: EntityType<*>,
    level: Level
) : Entity(entityType, level) {

    companion object {
        private val illuminatedChunks: HashMultimap<Level, Long> = HashMultimap.create()

        @JvmStatic
        fun isChunkIlluminated(blockPos: BlockPos, blockAndTintGetter: BlockAndTintGetter): Boolean {
            val level: Level = when (blockAndTintGetter) {
                is Level -> blockAndTintGetter
                is RenderChunkRegion -> blockAndTintGetter.level ?: return false
                else -> return false
            }

            val chunkPos = ChunkPos(blockPos)

            return illuminatedChunks[level].contains(chunkPos.toLong())
        }
    }

    override fun onAddedToLevel() {
        super.onAddedToLevel()

        val chunkPos = ChunkPos(this.blockPosition())

        illuminatedChunks[level()].add(chunkPos.toLong())
    }

    override fun onRemovedFromLevel() {
        super.onRemovedFromLevel()

        val chunkPos = ChunkPos(this.blockPosition())

        illuminatedChunks[level()].remove(chunkPos.toLong())
    }

    constructor(level: Level) : this(ModEntityTypes.ILLUMINATOR.get(), level)

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
    }
}