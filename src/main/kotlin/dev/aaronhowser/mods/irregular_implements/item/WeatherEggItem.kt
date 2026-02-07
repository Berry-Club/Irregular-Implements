package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isServerSide
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModItemLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.entity.ThrownWeatherEggEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.Direction
import net.minecraft.core.Position
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.util.StringRepresentable
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ProjectileItem
import net.minecraft.world.level.Level
import java.util.function.Supplier

//TODO: information recipes
class WeatherEggItem(properties: Properties) : Item(properties), ProjectileItem {

	enum class Weather(private val realName: String) : StringRepresentable {
		SUNNY("sunny"),
		RAINY("rainy"),
		STORMY("stormy");

		override fun getSerializedName(): String = realName

		companion object {
			val CODEC: StringRepresentable.StringRepresentableCodec<Weather> = StringRepresentable.fromEnum(Weather::values)
			val STREAM_CODEC: StreamCodec<ByteBuf, Weather> = ByteBufCodecs.fromCodec(CODEC)
		}
	}

	override fun getName(stack: ItemStack): Component {
		val weather = stack.get(ModDataComponents.WEATHER) ?: Weather.SUNNY
		return when (weather) {
			Weather.SUNNY -> ModItemLang.WEATHER_EGG_SUNNY.toComponent()
			Weather.RAINY -> ModItemLang.WEATHER_EGG_RAINY.toComponent()
			Weather.STORMY -> ModItemLang.WEATHER_EGG_STORMY.toComponent()
		}
	}

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)
		level.playSound(
			null,
			player.x,
			player.y,
			player.z,
			SoundEvents.EGG_THROW,
			SoundSource.PLAYERS,
			0.5f,
			0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f)
		)

		if (level.isServerSide) {
			val thrownWeatherEgg = ThrownWeatherEggEntity(level, player)

			thrownWeatherEgg.item = usedStack
			thrownWeatherEgg.weather = usedStack.get(ModDataComponents.WEATHER) ?: Weather.SUNNY
			thrownWeatherEgg.shootFromRotation(player, player.xRot, player.yRot, 0.0f, 1.5f, 1.0f)

			level.addFreshEntity(thrownWeatherEgg)
		}

		player.awardStat(Stats.ITEM_USED[this])
		usedStack.consume(1, player)
		return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide())
	}

	override fun asProjectile(level: Level, pos: Position, stack: ItemStack, direction: Direction): Projectile {
		val thrownWeatherEgg = ThrownWeatherEggEntity(level, pos.x(), pos.y(), pos.z())
		thrownWeatherEgg.item = stack
		thrownWeatherEgg.weather = stack.get(ModDataComponents.WEATHER) ?: Weather.SUNNY

		return thrownWeatherEgg
	}

	companion object {
		val DEFAULT_PROPERTIES: Supplier<Properties> = Supplier {
			Properties().component(ModDataComponents.WEATHER, Weather.SUNNY)
		}

		val WEATHER_PROPERTY: ResourceLocation = OtherUtil.modResource("weather")

		fun getWeatherFloat(
			stack: ItemStack,
			localLevel: Level?,
			holdingEntity: LivingEntity?,
			int: Int
		): Float {
			val weather = stack.get(ModDataComponents.WEATHER) ?: return 0f

			return when (weather) {
				Weather.SUNNY -> 0f
				Weather.RAINY -> 1f
				Weather.STORMY -> 2f
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

}