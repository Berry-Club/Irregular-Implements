package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

//TODO: Make it throw a projectile
class WeatherEggItem private constructor(
    private val weather: Weather
) : Item(Properties()) {

    enum class Weather { SUNNY, RAINY, STORMY }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)
        if (level !is ServerLevel) return InteractionResultHolder.pass(usedStack)

        val currentWeather = if (level.isRaining) {
            if (level.isThundering) Weather.STORMY else Weather.RAINY
        } else {
            Weather.SUNNY
        }

        if (currentWeather == this.weather) return InteractionResultHolder.fail(usedStack)

        when (weather) {
            Weather.SUNNY -> setSunny(level)
            Weather.RAINY -> setRainy(level)
            Weather.STORMY -> setStormy(level)
        }

        usedStack.consume(1, player)

        return InteractionResultHolder.success(usedStack)
    }

    //TODO: Custom entities with fancy particles etc

    companion object {
        private fun setSunny(level: ServerLevel) {
            level.setWeatherParameters(
                ServerLevel.RAIN_DELAY.sample(level.random),
                0,
                false,
                false
            )
        }

        private fun setRainy(level: ServerLevel) {
            level.setWeatherParameters(
                0,
                ServerLevel.RAIN_DURATION.sample(level.random),
                true,
                false
            )
        }

        private fun setStormy(level: ServerLevel) {
            level.setWeatherParameters(
                0,
                ServerLevel.RAIN_DURATION.sample(level.random),
                true,
                true
            )
        }

        val SUNNY = WeatherEggItem(Weather.SUNNY)
        val RAINY = WeatherEggItem(Weather.RAINY)
        val STORMY = WeatherEggItem(Weather.STORMY)
    }

}