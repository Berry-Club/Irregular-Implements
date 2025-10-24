package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent

class AirBottleItem(properties: Properties) : Item(properties) {

	override fun getUseDuration(stack: ItemStack): Int = 32
	override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.DRINK

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		return if (player.airSupply < player.maxAirSupply) {
			ItemUtils.startUsingInstantly(level, player, usedHand)
		} else {
			InteractionResultHolder.fail(player.getItemInHand(usedHand))
		}
	}

	override fun finishUsingItem(
		stack: ItemStack,
		level: Level,
		livingEntity: LivingEntity
	): ItemStack {
		if (livingEntity.airSupply == livingEntity.maxAirSupply) {
			return stack
		}

		livingEntity.airSupply = livingEntity.maxAirSupply
		livingEntity.gameEvent(GameEvent.DRINK)

		if (livingEntity is Player) {
			val bottle = ItemUtils.createFilledResult(
				stack,
				livingEntity,
				Items.GLASS_BOTTLE.defaultInstance
			)

			livingEntity.cooldowns.addCooldown(Items.GLASS_BOTTLE, 20)

			return bottle
		}

		return stack
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(16)
	}

}