package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndPortalBlock
import net.minecraft.world.level.block.EndRodBlock
import net.minecraft.world.phys.AABB
import net.neoforged.neoforge.common.Tags

class ArtificialEndPortalEntity(
    entityType: EntityType<*>,
    level: Level
) : Entity(entityType, level) {

    companion object {
        private fun isValidPosition(
            level: Level,
            entityPos: BlockPos,
            checkForOtherPortals: Boolean
        ): Boolean {

            //TODO: Does this detect itself?
            if (checkForOtherPortals) {
                val entities = level.getEntitiesOfClass(
                    ArtificialEndPortalEntity::class.java,
                    AABB.ofSize(entityPos.bottomCenter, 3.0, 1.0, 3.0)
                )

                if (entities.isNotEmpty()) return false
            }

            var endRodPos: BlockPos? = null
            for (dY in 0..10) {
                val pos = entityPos.above(dY)
                val state = level.getBlockState(pos)

                if (state.`is`(Blocks.END_ROD) && state.getValue(EndRodBlock.FACING) == Direction.DOWN) {
                    endRodPos = pos
                    break
                }
            }

            if (endRodPos == null) return false

            if (!level.getBlockState(endRodPos.above()).`is`(Tags.Blocks.END_STONES)) return false

            for (dX in -1..1) for (dZ in -1..1) {
                val posThere = entityPos.offset(dX, 0, dZ)

                val isAir = level.getBlockState(posThere).isAir
                val isAboveEndStone = level.getBlockState(posThere.below()).`is`(Tags.Blocks.END_STONES)

                if (!isAir || !isAboveEndStone) return false

                if (dX != 0) {
                    val posDx = posThere.offset(dX, 0, 0)
                    val stateDx = level.getBlockState(posDx)
                    if (!stateDx.`is`(Tags.Blocks.OBSIDIANS)) return false
                }

                if (dZ != 0) {
                    val posDz = posThere.offset(0, 0, dZ)
                    val stateDz = level.getBlockState(posDz)
                    if (!stateDz.`is`(Tags.Blocks.OBSIDIANS)) return false
                }
            }

            return true
        }
    }

    constructor(level: Level, blockPos: BlockPos) : this(ModEntityTypes.ARTIFICIAL_END_PORTAL.get(), level) {
        this.setPos(blockPos.x.toDouble() + 0.5, blockPos.y.toDouble(), blockPos.z.toDouble() + 0.5)
    }

    var actionTimer = 0
        private set

    override fun tick() {
        super.tick()

        if (this.actionTimer < 200) {
            actionTimer++

            if (this.level().isClientSide && this.actionTimer > 40) {
                spawnParticles()
            }
        }

        if (this.level().isClientSide) {
            if (this.actionTimer == 85) {
                this.level().playSound(
                    null,
                    this.blockPosition(),
                    SoundEvents.PORTAL_TRAVEL,
                    SoundSource.BLOCKS
                )
            }
        } else {
            if (this.tickCount % 40 == 0) {
//                if (!isValidPosition(this.level(), this.blockPosition(), false)) {
//                    this.kill()
//                }
            }
        }

        if (this.actionTimer >= 200 && this.tickCount % 20 == 0) {
            teleportEntitiesToEnd()
        }

    }

    private fun teleportEntitiesToEnd() {
        val entities = this.level().getEntitiesOfClass(Entity::class.java, this.boundingBox)

        for (entity in entities) {
            if (entity is ArtificialEndPortalEntity) continue

            entity.setAsInsidePortal(
                Blocks.END_PORTAL as EndPortalBlock,
                this.blockPosition()
            )
        }
    }

    private fun spawnParticles() {
        for (i in 0..5) {

            val modX = this.random.nextFloat() * 0.05f - 0.025f
            val modZ = this.random.nextFloat() * 0.05f - 0.025f

            level().addParticle(
                ParticleTypes.ENCHANT,
                this.x + modX,
                this.y + 2,
                this.z + modZ,
                modX.toDouble() * 2,
                1.0,
                modZ.toDouble() * 2
            )
        }
    }

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {

    }

    override fun readAdditionalSaveData(compound: CompoundTag) {

    }

    override fun addAdditionalSaveData(compound: CompoundTag) {

    }
}