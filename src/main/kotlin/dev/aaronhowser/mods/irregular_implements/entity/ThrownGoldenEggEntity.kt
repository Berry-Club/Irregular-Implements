package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.particles.ItemParticleOption
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult

class ThrownGoldenEggEntity(
    entityType: EntityType<ThrownGoldenEggEntity>,
    level: Level
) : ThrowableItemProjectile(entityType, level) {

    companion object {
        private val ZERO_SIZED_DIMENSIONS: EntityDimensions = EntityDimensions.fixed(0.0f, 0.0f)
    }

    override fun onHit(result: HitResult) {
        super.onHit(result)

        if (level().isClientSide) return

        if (this.random.nextInt(8) == 0) spawnGoldenChicken()

        level().broadcastEntityEvent(this, 3.toByte())
        discard()
    }

    private fun spawnGoldenChicken() {
        var i = 1
        if (this.random.nextInt(32) == 0) i = 4

        for (j in 0 until i) {
            val chicken = EntityType.CHICKEN.create(level())
            if (chicken != null) {
                chicken.age = -24000
                chicken.moveTo(this.x, this.y, this.z, this.yRot, 0.0f)

                if (!chicken.fudgePositionAfterSizeChange(ZERO_SIZED_DIMENSIONS)) break

                level().addFreshEntity(chicken)
            }
        }
    }

    override fun onHitEntity(result: EntityHitResult) {
        super.onHitEntity(result)
        result.entity.hurt(damageSources().thrown(this, this.owner), 0f)
    }

    override fun handleEntityEvent(id: Byte) {
        if (id.toInt() == 3) {
            for (i in 0..7) {
                level()
                    .addParticle(
                        ItemParticleOption(ParticleTypes.ITEM, this.item),
                        this.x,
                        this.y,
                        this.z,
                        (this.random.nextDouble() - 0.5) * 0.08,
                        (this.random.nextDouble() - 0.5) * 0.08,
                        (this.random.nextDouble() - 0.5) * 0.08
                    )
            }
        }
    }

    override fun getDefaultItem(): Item {
        return ModItems.GOLDEN_EGG.get()
    }

}