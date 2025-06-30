package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.Mth
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import java.time.LocalDate
import java.time.Month

class FlooPouchItem(properties: Properties) : Item(properties) {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)

		if (level.isClientSide || !player.isSecondaryUseActive) {
			return InteractionResultHolder.pass(usedStack)
		}

		val currentFlooPowder = usedStack.getOrDefault(ModDataComponents.FLOO_POWDER, 0)
		if (currentFlooPowder >= MAX_FLOO_POWDER) return InteractionResultHolder.fail(usedStack)

		for (invStack in player.inventory.items) {
			if (!invStack.`is`(ModItems.FLOO_POWDER)) continue

			val newCurrentFlooPowder = usedStack.getOrDefault(ModDataComponents.FLOO_POWDER, 0)

			val toTransfer = minOf(MAX_FLOO_POWDER - newCurrentFlooPowder, invStack.count)
			invStack.shrink(toTransfer)

			val newFlooPowder = newCurrentFlooPowder + toTransfer
			usedStack.set(ModDataComponents.FLOO_POWDER, newFlooPowder)

			if (newFlooPowder >= MAX_FLOO_POWDER) break
		}

		val newFlooPowder = usedStack.getOrDefault(ModDataComponents.FLOO_POWDER, 0)
		val pitch = Mth.lerp(
			1f - newFlooPowder / MAX_FLOO_POWDER,
			0.7f,
			1.5f
		)

		level.playSound(
			null,
			player.blockPosition(),
			SoundEvents.BUNDLE_DROP_CONTENTS,
			player.soundSource,
			0.75f,
			pitch
		)

		return InteractionResultHolder.success(usedStack)
	}

	override fun getBarColor(stack: ItemStack): Int = 0x00FF00
	override fun isBarVisible(stack: ItemStack): Boolean = true
	override fun getBarWidth(stack: ItemStack): Int {
		val currentFlooPowder = stack.get(ModDataComponents.FLOO_POWDER) ?: 0
		return (13.0f * currentFlooPowder / MAX_FLOO_POWDER).toInt()
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val currentFlooPowder = stack.getOrDefault(ModDataComponents.FLOO_POWDER, 0)
		tooltipComponents.add(
			Component.literal("$currentFlooPowder / $MAX_FLOO_POWDER Floo Powder")
		)

		fuckJkr(tooltipComponents)
	}

	companion object {
		const val MAX_FLOO_POWDER = 128

		val DEFAULT_PROPERTIES: () -> Properties = {
			Properties()
				.stacksTo(1)
				.component(ModDataComponents.FLOO_POWDER, 0)
		}

		fun fuckJkr(tooltipComponents: MutableList<Component>) {
			val now = LocalDate.now()
			if ((now.month == Month.JULY && now.dayOfMonth == 31)
			) {
				tooltipComponents.add(Component.literal("Trans rights are human rights!"))
			}
		}
	}

}