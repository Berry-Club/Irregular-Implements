package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.StringRepresentable
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

//TODO: information recipes
class WeatherEggItem : Item(
    Properties()
        .component(ModDataComponents.WEATHER, Weather.SUNNY)
) {

    enum class Weather(private val realName: String) : StringRepresentable {
        SUNNY("sunny"),
        RAINY("rainy"),
        STORMY("stormy");

        override fun getSerializedName(): String = realName
    }

    companion object {
        val WEATHER: ResourceLocation = OtherUtil.modResource("weather")

        fun getAngleFloat(
            stack: ItemStack,
            localLevel: ClientLevel?,
            holdingEntity: LivingEntity?,
            int: Int
        ): Float {

            val weather = stack.get(ModDataComponents.WEATHER) ?: return 0f

            return when (weather) {
                Weather.SUNNY -> 0f
                Weather.RAINY -> 0.5f
                Weather.STORMY -> 1f
            }
        }

        fun fromWeather(weather: Weather): ItemStack {
            val stack = ModItems.WEATHER_EGG.toStack()

            stack.set(
                ModDataComponents.WEATHER,
                weather
            )

            return stack
        }

    }


    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)
        if (level !is ServerLevel) return InteractionResultHolder.pass(usedStack)

        val currentWeather = if (level.isRaining) {
            if (level.isThundering) Weather.STORMY else Weather.RAINY
        } else {
            Weather.SUNNY
        }

        usedStack.consume(1, player)

        return InteractionResultHolder.success(usedStack)
    }

}