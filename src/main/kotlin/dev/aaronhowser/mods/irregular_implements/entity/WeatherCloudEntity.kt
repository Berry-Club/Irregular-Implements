package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

class WeatherCloudEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level) {

    constructor(level: Level, x: Double, y: Double, z: Double, weather: WeatherEggItem.Weather) : this(
        ModEntityTypes.WEATHER_CLOUD.get(),
        level
    ) {
        this.setPos(x, y, z)
        this.weather = weather
    }

    companion object {
        val AGE: EntityDataAccessor<Int> = SynchedEntityData.defineId(WeatherCloudEntity::class.java, EntityDataSerializers.INT)
        const val AGE_NBT = "Age"
    }

    init {
        this.noPhysics = true
    }

    var age: Int
        private set(value) = this.entityData.set(AGE, value)
        get() = this.entityData.get(AGE)

    var weather: WeatherEggItem.Weather = WeatherEggItem.Weather.SUNNY

    override fun tick() {
        super.tick()

        if (this.age >= 200) {
            this.addDeltaMovement(Vec3(0.0, 0.001, 0.0))
            this.deltaMovement = this.deltaMovement.scale(1.02)

            if (this.position().y < this.level().maxBuildHeight) return

            setWeather()
            this.discard()
            return
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
        builder.define(AGE, 0)
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        this.age = compound.getInt(AGE_NBT)
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        compound.putInt(AGE_NBT, this.age)
    }


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

}