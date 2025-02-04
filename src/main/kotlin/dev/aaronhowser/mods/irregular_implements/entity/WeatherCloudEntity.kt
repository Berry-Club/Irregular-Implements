package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class WeatherCloudEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level) {

    constructor(level: Level, x: Double, y: Double, z: Double, weather: WeatherEggItem.Weather) : this(
        ModEntityTypes.WEATHER_CLOUD.get(),
        level
    ) {
        this.setPos(x, y, z)
        this.weather = weather
    }

    var weather: WeatherEggItem.Weather = WeatherEggItem.Weather.SUNNY

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        TODO("Not yet implemented")
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        TODO("Not yet implemented")
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        TODO("Not yet implemented")
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