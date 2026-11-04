package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isClientSide
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isServerSide
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
		setPos(x, y, z)
		this.weather = weather
	}

	init {
		noPhysics = true
	}

	var age: Int
		private set(value) = entityData.set(AGE, value)
		get() = entityData.get(AGE)

	var weather: WeatherEggItem.Weather
		set(value) = entityData.set(WEATHER_TYPE, value.ordinal)
		get() = WeatherEggItem.Weather.entries[entityData.get(WEATHER_TYPE)]

	override fun tick() {
		super.tick()

		if (age < 200) {
			deltaMovement = Vec3(0.0, 0.007, 0.0)
		} else {
			val dm = deltaMovement
			var dy = dm.y + 0.001
			dy *= 1.02

			deltaMovement = Vec3(dm.x, dy, dm.z)

			if (position().y >= level().maxBuildHeight) {
				setWeather()
				discard()
				return
			}
		}

		age++
		move(MoverType.SELF, deltaMovement)

		if (level().isClientSide) {
			spawnParticles()
		}
	}

	private fun spawnParticles() {
		if (!isClientSide) return

		when (weather) {
			WeatherEggItem.Weather.SUNNY -> spawnNiceCloud()
			WeatherEggItem.Weather.RAINY -> spawnRainyCloud()
			WeatherEggItem.Weather.STORMY -> spawnStormyCloud()
		}
	}

	private fun spawnDefaultCloud() {
		for (y in -1..1) {
			var t = 0.0
			while (t < Math.PI * 2) {
				val yDouble = y.toDouble()

				var a = 0.25
				var b = 0.35

				val divisor = abs(yDouble) * 0.5 + 1
				a /= divisor
				b /= divisor

				val elX = a * cos(t)
				val elZ = b * sin(t)

				level().addParticle(
					ParticleTypes.SMOKE,
					true,
					x + elX,
					y + yDouble / 8.0,
					z + elZ,
					0.0,
					-0.03,
					0.0
				)

				t += Math.PI / 5
			}
		}
	}

	private fun spawnRainyCloud() {
		spawnDefaultCloud()

		for (i in 0 until 2) {
			val t = Math.PI * 2 * random.nextDouble()

			var a = 0.25
			var b = 0.35

			a /= 1.5 + random.nextDouble()
			b /= 1.5 + random.nextDouble()

			val elX = a * cos(t)
			val elZ = b * sin(t)

			level().addParticle(
				ParticleTypes.FISHING,
				true,
				x + elX,
				y - 0.2,
				z + elZ,
				0.0,
				-0.05,
				0.0
			)
		}
	}

	private fun spawnStormyCloud() {
		spawnDefaultCloud()

		val t = Math.PI * 2 * random.nextDouble()

		var a = 0.25
		var b = 0.35

		a /= 1.5 + random.nextDouble()
		b /= 1.5 + random.nextDouble()

		val elX = a * cos(t)
		val elZ = b * sin(t)

		// Temporary fallback using smoke; replace with custom particle if needed
		level().addParticle(
			ParticleTypes.SMOKE,
			true,
			x + elX,
			y,
			z + elZ,
			random.nextDouble() * 0.1 - 0.05,
			random.nextDouble() * 0.2 - 0.1,
			random.nextDouble() * 0.1 - 0.05
		)
	}

	private fun spawnNiceCloud() {
		val level = level()
		if (level.isServerSide) return

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
					x + elX,
					this.y + y.toFloat() / 8,
					z + elZ,
					0.0, 0.0, 0.0
				)

			}
		}
	}


	private fun setWeather() {
		val duration = (300 + random.nextInt(600)) * 20
		val level = level() as? ServerLevel ?: return

		when (weather) {
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
		age = compound.getInt(AGE_NBT)
		weather = WeatherEggItem.Weather.entries[compound.getInt(WEATHER_NBT)]
	}

	override fun addAdditionalSaveData(compound: CompoundTag) {
		compound.putInt(AGE_NBT, age)
		compound.putInt(WEATHER_NBT, weather.ordinal)
	}

	companion object {
		val AGE: EntityDataAccessor<Int> = SynchedEntityData.defineId(WeatherCloudEntity::class.java, EntityDataSerializers.INT)
		val WEATHER_TYPE: EntityDataAccessor<Int> = SynchedEntityData.defineId(WeatherCloudEntity::class.java, EntityDataSerializers.INT)

		const val AGE_NBT = "Age"
		const val WEATHER_NBT = "Weather"
	}
}