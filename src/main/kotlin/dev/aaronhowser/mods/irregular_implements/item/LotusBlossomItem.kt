package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

class LotusBlossomItem : Item(
	Properties()
		.food(FOOD_PROPERTIES)
) {

	companion object {
		private val FOOD_PROPERTIES = FoodProperties.Builder()
			.alwaysEdible()
			.build()
	}

	override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int {
		return 10
	}

	override fun getUseAnimation(stack: ItemStack): UseAnim {
		return UseAnim.EAT
	}

	override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
		val amountToEat = if (livingEntity.isShiftKeyDown) stack.count else 1

		if (livingEntity is Player) {
			var amountXp = 0
			for (i in 0 until amountToEat) {
				amountXp += 3 + level.random.nextInt(5) + level.random.nextInt(5)
			}

			livingEntity.giveExperiencePoints(amountXp)
		}

		val remainder = stack.copy()
		remainder.consume(amountToEat, livingEntity)
		return remainder
	}

}