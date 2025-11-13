package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.handler.redstone_signal.RedstoneHandlerSavedData
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.aaron.AaronExtensions.status
import net.minecraft.ChatFormatting
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import java.util.function.Supplier

class RedstoneActivatorItem(properties: Properties) : Item(properties) {

	override fun use(
		level: Level,
		player: Player,
		usedHand: InteractionHand
	): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)
		if (level.isClientSide) return InteractionResultHolder.pass(usedStack)
		if (!player.isSecondaryUseActive) return InteractionResultHolder.pass(usedStack)

		cycleDuration(usedStack)

		val newDuration = usedStack.get(ModDataComponents.DURATION) ?: return InteractionResultHolder.fail(usedStack)
		val component = newDuration.toString()
			.toComponent()
			.withStyle(ChatFormatting.RED)

		player.status(component)

		return InteractionResultHolder.success(usedStack)
	}

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level as? ServerLevel ?: return InteractionResult.PASS

		val usedStack = context.itemInHand
		val duration = usedStack.get(ModDataComponents.DURATION) ?: return InteractionResult.PASS

		val clickedPos = context.clickedPos

		RedstoneHandlerSavedData.addSignal(
			level = level,
			blockPos = clickedPos,
			duration = duration,
			strength = 15
		)

		return InteractionResult.SUCCESS
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val duration = stack.get(ModDataComponents.DURATION) ?: return

		val component = "Duration: $duration"
			.toGrayComponent()

		tooltipComponents.add(component)
	}

	companion object {
		val DEFAULT_PROPERTIES: Supplier<Properties> =
			Supplier {
				Properties()
					.stacksTo(1)
					.component(ModDataComponents.DURATION, 20)
			}

		val DURATION = OtherUtil.modResource("duration")

		const val SHORT = 2
		const val MEDIUM = 20
		const val LONG = 100

		fun cycleDuration(stack: ItemStack) {
			val currentDuration = stack.get(ModDataComponents.DURATION) ?: 0
			val newDuration = when (currentDuration) {
				SHORT -> MEDIUM
				MEDIUM -> LONG
				LONG -> SHORT
				else -> MEDIUM
			}

			stack.set(ModDataComponents.DURATION, newDuration)
		}

		fun getDurationFloat(
			stack: ItemStack,
			localLevel: ClientLevel?,
			holdingEntity: LivingEntity?,
			int: Int
		): Float {
			return stack.get(ModDataComponents.DURATION)?.toFloat() ?: MEDIUM.toFloat()
		}
	}

}