package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.EscapeRopeHandler
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

class EscapeRopeItem(properties: Properties) : Item(properties) {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)
		player.startUsingItem(usedHand)
		return InteractionResultHolder.consume(usedStack)
	}

	override fun onUseTick(level: Level, livingEntity: LivingEntity, stack: ItemStack, remainingUseDuration: Int) {
		if (livingEntity is ServerPlayer && !level.canSeeSky(livingEntity.blockPosition())) {
			EscapeRopeHandler.addTask(livingEntity)
		}
	}

	override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int {
		return 72000
	}

	override fun getUseAnimation(stack: ItemStack): UseAnim {
		return UseAnim.BOW
	}

	override fun isFoil(stack: ItemStack): Boolean {
		val localPlayer = ClientUtil.localPlayer ?: return false
		if (localPlayer.level().canSeeSky(localPlayer.blockPosition())) return false

		return localPlayer.useItem == stack
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().durability(20)
	}

}