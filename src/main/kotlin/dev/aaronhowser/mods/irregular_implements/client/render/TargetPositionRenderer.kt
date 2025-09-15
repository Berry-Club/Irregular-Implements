package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.neoforged.neoforge.client.event.ClientTickEvent

object TargetPositionRenderer {

	fun afterClientTick(event: ClientTickEvent.Post) {
		val player = ClientUtil.localPlayer ?: return

		val mainHandItemLocation = player.mainHandItem.get(ModDataComponents.GLOBAL_POS)
		val offHandItemLocation = player.offhandItem.get(ModDataComponents.GLOBAL_POS)

		val level = player.level()

		if (mainHandItemLocation != null && mainHandItemLocation.dimension == level.dimension()) {
			CubeIndicatorRenderer.addIndicator(mainHandItemLocation.pos, 1, 0x3200FF00)
		}

		if (offHandItemLocation != null && offHandItemLocation.dimension == level.dimension()) {
			CubeIndicatorRenderer.addIndicator(offHandItemLocation.pos, 1, 0x3200FF00)
		}
	}

}