package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.FastColor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MoverType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class WeatherCloudEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level) {

	constructor(level: Level, x: Double, y: Double, z: Double, weather: WeatherEggItem.Weather) : this(
		ModEntityTypes.WEATHER_CLOUD.get(),
		level
	) {
		this.setPos(x, y, z)
		this.weather = weather
	}

	init {
		this.noPhysics = true
	}

	var age: Int
		private set(value) = this.entityData.set(AGE, value)
		get() = this.entityData.get(AGE)

	var weather: WeatherEggItem.Weather
		set(value) = this.entityData.set(WEATHER_TYPE, value.ordinal)
		get() = WeatherEggItem.Weather.entries[this.entityData.get(WEATHER_TYPE)]

	override fun tick() {
		super.tick()

		if (this.age >= 200) {
			this.addDeltaMovement(Vec3(0.0, 0.001, 0.0))
			this.deltaMovement = this.deltaMovement.scale(1.02)

			if (this.position().y >= this.level().maxBuildHeight) {
				setWeather()
				this.discard()
				return
			}
		}

		this.age++
		this.move(MoverType.SELF, this.deltaMovement)
		if (this.level().isClientSide) spawnParticles()
	}

	private fun spawnParticles() {
		val level = this.level()
		if (!level.isClientSide) return

		if (this.weather == WeatherEggItem.Weather.SUNNY) {
			spawnNiceCloud()
			return
		}

		for (y in -1..1) {
			var t = 0.0
			while (t < Math.PI * 2) {
				t += Math.PI / 5

				var a = 0.25
				var b = 0.35

				a /= abs(y) * 0.5 + 1
				b /= abs(y) * 0.5 + 1

				val elX = a * cos(t)
				val elZ = b * sin(t)

				level.addParticle(
					ParticleTypes.SMOKE,    //TODO: Just a colored particle instead of smoke
					true,
					this.x + elX,
					this.y + y.toFloat() / 8,
					this.z + elZ,
					0.0, 0.0, 0.0
				)
			}
		}

		val isRain = this.weather == WeatherEggItem.Weather.RAINY
		val iterations = if (isRain) 2 else 1

		for (i in 0 until iterations) {

			val t = Math.PI * 2 * this.random.nextDouble()

			var a = 0.25
			var b = 0.35

			a /= 1.5 + this.random.nextDouble()
			b /= 1.5 + this.random.nextDouble()

			val elX = a * cos(t)
			val elZ = b * sin(t)

			val particle = if (isRain) {
				ParticleTypes.FISHING
			} else {
				val shade = (this.random.nextFloat() * 0.1 - 0.025).toInt()
				val color = FastColor.ARGB32.color(255, shade, shade, shade)

				DustParticleOptions(Vec3.fromRGB24(color).toVector3f(), 1.0F)
			}

			level.addParticle(
				particle,
				true,
				this.x + elX,
				this.y + 0.5,
				this.z + elZ,
				0.0, 0.0, 0.0
			)
		}

	}

	private fun spawnNiceCloud() {
		val level = this.level()
		if (!level.isClientSide) return

		for (y in -1..1) {

			var t = 0.0
			while (t < Math.PI * 2) {
				t += Math.PI / 3

				var a = 0.25
				var b = 0.35

				a /= abs(y) * 0.5 + 1
				b /= abs(y) * 0.5 + 1

				val elX = a * cos(t)
				val elZ = b * sin(t)

				val shade = (0xFF - (level.random.nextFloat() * 0.5 - 0.025)).toInt()
				val color = FastColor.ARGB32.color(255, shade, shade, shade)

				level.addParticle(
					DustParticleOptions(Vec3.fromRGB24(color).toVector3f(), 1.0F),
					true,
					this.x + elX,
					this.y + y.toFloat() / 8,
					this.z + elZ,
					0.0, 0.0, 0.0
				)

			}
		}

	}

	private fun setWeather() {
		val duration = (300 + this.random.nextInt(600)) * 20
		val level = this.level() as? ServerLevel ?: return

		when (this.weather) {
			WeatherEggItem.Weather.SUNNY -> level.setWeatherParameters(duration, 0, false, false)
			WeatherEggItem.Weather.RAINY -> level.setWeatherParameters(0, duration, true, false)
			WeatherEggItem.Weather.STORMY -> level.setWeatherParameters(0, duration, true, true)
		}
	}

	override fun defineSynchedData(builder: SynchedEntityData.Builder) {
		builder
			.define(AGE, 0)
			.define(WEATHER_TYPE, WeatherEggItem.Weather.SUNNY.ordinal)
	}

	override fun readAdditionalSaveData(compound: CompoundTag) {
		this.age = compound.getInt(AGE_NBT)
		this.weather = WeatherEggItem.Weather.entries[compound.getInt(WEATHER_NBT)]
	}

	override fun addAdditionalSaveData(compound: CompoundTag) {
		compound.putInt(AGE_NBT, this.age)
		compound.putInt(WEATHER_NBT, this.weather.ordinal)
	}

	companion object {
		val AGE: EntityDataAccessor<Int> = SynchedEntityData.defineId(WeatherCloudEntity::class.java, EntityDataSerializers.INT)
		val WEATHER_TYPE: EntityDataAccessor<Int> = SynchedEntityData.defineId(WeatherCloudEntity::class.java, EntityDataSerializers.INT)

		const val AGE_NBT = "Age"
		const val WEATHER_NBT = "Weather"
	}
}