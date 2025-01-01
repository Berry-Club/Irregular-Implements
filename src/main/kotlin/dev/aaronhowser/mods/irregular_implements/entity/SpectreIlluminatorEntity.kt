package dev.aaronhowser.mods.irregular_implements.entity

import com.google.common.collect.HashMultimap
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.*
import net.minecraft.world.phys.Vec3

class SpectreIlluminatorEntity(
    entityType: EntityType<*>,
    level: Level
) : Entity(entityType, level) {

    companion object {
        private val illuminatedChunks: HashMultimap<Level, Long> = HashMultimap.create()

        //FIXME: Only works after a block update in the chunk, and resets after relog
        @JvmStatic
        fun isChunkIlluminated(blockPos: BlockPos, blockAndTintGetter: BlockAndTintGetter): Boolean {
            val level: Level = when (blockAndTintGetter) {
                is Level -> blockAndTintGetter

                // If it's something that can be accessed on server, but isn't a Level, return false before it tries to load client-only class
                is CommonLevelAccessor -> return false

                else -> ClientUtil.levelFromBlockAndTintGetter(blockAndTintGetter) ?: return false
            }

            val chunkPos = ChunkPos(blockPos)

            return illuminatedChunks[level].contains(chunkPos.toLong())
        }

        private fun forceLightUpdates(level: Level, chunkPos: ChunkPos) {
            for (dX in 0..15) for (dZ in 0..15) for (dY in level.minBuildHeight..level.maxBuildHeight) {
                val blockPos = chunkPos.getBlockAt(dX, dY, dZ)

                level.getBrightness(LightLayer.BLOCK, blockPos)
                level.getBrightness(LightLayer.SKY, blockPos)
                level.getRawBrightness(blockPos, 0)
            }
        }
    }

    override fun isPickable(): Boolean {
        return true
    }

    override fun interact(player: Player, hand: InteractionHand): InteractionResult {
        if (player.level().isClientSide) return InteractionResult.PASS

        if (player.hasInfiniteMaterials()) {
            this.discard()
        } else {
            this.kill()
        }

        return InteractionResult.SUCCESS
    }

    override fun onAddedToLevel() {
        super.onAddedToLevel()

        val chunkPos = ChunkPos(this.blockPosition())

        illuminatedChunks[level()].add(chunkPos.toLong())

        forceLightUpdates(level(), chunkPos)
    }

    override fun remove(reason: RemovalReason) {
        super.remove(reason)

        val chunkPos = ChunkPos(this.blockPosition())
        illuminatedChunks[level()].remove(chunkPos.toLong())

        forceLightUpdates(level(), chunkPos)

        if (removalReason == RemovalReason.KILLED) {
            OtherUtil.dropStackAt(ModItems.SPECTRE_ILLUMINATOR.toStack(), this)
        }
    }

    val destination: Vec3 by lazy {
        val chunkPos = ChunkPos(this.blockPosition())

        var highestBlock = level().minBuildHeight

        yScan@
        for (dY in level().minBuildHeight..level().maxBuildHeight) {

            for (dX in 0..15) {
                for (dZ in 0..15) {
                    val blockPos = chunkPos.getBlockAt(dX, dY, dZ)
                    val state = level().getBlockState(blockPos)

                    if (!state.isAir) {
                        highestBlock = dY
                        continue@yScan
                    }
                }
            }
        }

        Vec3(
            chunkPos.middleBlockX.toDouble(),
            highestBlock.toDouble() + 5,
            chunkPos.middleBlockZ.toDouble()
        )
    }

    override fun tick() {
        super.tick()

        if (this.position() == destination) return

        val distance = this.position().distanceTo(destination)
        if (distance < 0.1) {
            this.setPos(destination)
            return
        }

        val newPos = this.position().lerp(destination, 0.001)

        this.setPos(newPos)
    }

    constructor(level: Level) : this(ModEntityTypes.SPECTRE_ILLUMINATOR.get(), level)

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
    }
}