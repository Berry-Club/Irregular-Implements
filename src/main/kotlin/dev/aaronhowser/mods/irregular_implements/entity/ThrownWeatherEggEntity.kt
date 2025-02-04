package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level

class ThrownWeatherEggEntity : ThrowableItemProjectile {

    constructor(entityType: EntityType<ThrownGoldenEggEntity>, level: Level) : super(entityType, level)
    constructor(level: Level, shooter: LivingEntity) : super(ModEntityTypes.WEATHER_EGG.get(), shooter, level)
    constructor(level: Level, x: Double, y: Double, z: Double) : super(ModEntityTypes.WEATHER_EGG.get(), x, y, z, level)

    private fun setSunny() {
        val level = this.level() as? ServerLevel ?: return
        level.setWeatherParameters(
            ServerLevel.RAIN_DELAY.sample(level.random),
            0,
            false,
            false
        )
    }

    private fun setRainy() {
        val level = this.level() as? ServerLevel ?: return
        level.setWeatherParameters(
            0,
            ServerLevel.RAIN_DURATION.sample(level.random),
            true,
            false
        )
    }

    private fun setStormy() {
        val level = this.level() as? ServerLevel ?: return
        level.setWeatherParameters(
            0,
            ServerLevel.RAIN_DURATION.sample(level.random),
            true,
            true
        )
    }

    override fun getDefaultItem(): Item {
        TODO("Not yet implemented")
    }

}