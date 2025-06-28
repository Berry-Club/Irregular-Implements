package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent

class AirBottleItem : Item(
	Properties()
		.stacksTo(16)
) {

	override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 32
	override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.DRINK

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		return ItemUtils.startUsingInstantly(level, player, usedHand)
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
			ItemUtils.createFilledResult(
				stack,
				livingEntity,
				Items.GLASS_BOTTLE.defaultInstance
			)
		}

		return stack
	}

}