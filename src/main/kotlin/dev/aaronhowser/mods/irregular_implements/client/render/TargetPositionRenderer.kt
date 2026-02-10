package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.event.ClientTickEvent

object TargetPositionRenderer {

	fun addCubeIndicators(event: ClientTickEvent.Post) {
		val player = AaronClientUtil.localPlayer ?: return

		fun addIndicator(stack: ItemStack) {
			if (stack.isItem(ModItems.REDSTONE_TOOL)) return

			val globalPos = stack.get(ModDataComponents.GLOBAL_POS) ?: return
			if (globalPos.dimension != player.level().dimension()) return

			CubeIndicatorRenderer.addIndicator(globalPos.pos, 1, 0x3200FF00)
		}

		addIndicator(player.mainHandItem)
		addIndicator(player.offhandItem)
	}

}