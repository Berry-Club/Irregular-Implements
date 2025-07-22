package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModDimensions
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

class SpectreKeyItem(properties: Properties) : Item(properties) {

	override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.BOW
	override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 100

	override fun isFoil(stack: ItemStack): Boolean {
		return ClientUtil.localLevel?.dimension() == ModDimensions.SPECTRE_LEVEL_KEY
	}

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack?> {
		player.startUsingItem(usedHand)
		val usedStack = player.getItemInHand(usedHand)
		return InteractionResultHolder.success(usedStack)
	}

}