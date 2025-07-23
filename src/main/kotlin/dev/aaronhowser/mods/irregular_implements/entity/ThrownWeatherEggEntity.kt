package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.particles.ItemParticleOption
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult

class ThrownWeatherEggEntity : ThrowableItemProjectile {

	constructor(entityType: EntityType<ThrownWeatherEggEntity>, level: Level) : super(entityType, level)
	constructor(level: Level, shooter: LivingEntity) : super(ModEntityTypes.WEATHER_EGG.get(), shooter, level)
	constructor(level: Level, x: Double, y: Double, z: Double) : super(ModEntityTypes.WEATHER_EGG.get(), x, y, z, level)

	var weather: WeatherEggItem.Weather = WeatherEggItem.Weather.SUNNY

	override fun onHit(result: HitResult) {
		val level = this.level() as? ServerLevel ?: return

        val currentWeather = if (level.isRaining) {
            if (level.isThundering) WeatherEggItem.Weather.STORMY else WeatherEggItem.Weather.RAINY
        } else {
            WeatherEggItem.Weather.SUNNY
        }

        if (currentWeather == this.weather) {
            val itemEntity = ItemEntity(level, this.x, this.y, this.z, this.item)
            level.addFreshEntity(itemEntity)

            this.discard()
            return
        }

		val weatherCloud = WeatherCloudEntity(level, this.x, this.y, this.z, this.weather)
		weatherCloud.weather = this.weather
		level.addFreshEntity(weatherCloud)

		level().broadcastEntityEvent(this, 3.toByte())
		this.discard()
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
		return ModItems.WEATHER_EGG.get()
	}

}