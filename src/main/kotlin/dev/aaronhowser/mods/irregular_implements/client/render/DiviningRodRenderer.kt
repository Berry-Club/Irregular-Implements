package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.neoforged.neoforge.client.event.ClientTickEvent

object DiviningRodRenderer {

	fun addCubeIndicators(event: ClientTickEvent.Post) {
		val player = AaronClientUtil.localPlayer ?: return
		val playerPos = player.blockPosition()
		val level = player.level()

		val offHandItem = player.offhandItem
		val mainHandItem = player.mainHandItem

		val offHandTag = if (offHandItem.`is`(ModItems.DIVINING_ROD)) offHandItem.get(ModDataComponents.DIVINE_BLOCKS) else null
		val mainHandTag = if (mainHandItem.`is`(ModItems.DIVINING_ROD)) mainHandItem.get(ModDataComponents.DIVINE_BLOCKS) else null

		if (offHandTag == null && mainHandTag == null) {
			return
		}

		val radius = ServerConfig.CONFIG.diviningRodCheckRadius.get()

		val positions = BlockPos.betweenClosedStream(
			playerPos.offset(-radius, -radius, -radius),
			playerPos.offset(radius, radius, radius)
		)

		for (checkedPos in positions) {
			if (!level.isLoaded(checkedPos)) continue

			val checkedState = level.getBlockState(checkedPos)

			val matchesOffHand = offHandTag != null && checkedState.`is`(offHandTag)
			val matchesMainHand = mainHandTag != null && checkedState.`is`(mainHandTag)

			if (!matchesOffHand && !matchesMainHand) continue

			CubeIndicatorRenderer.addIndicator(
				checkedPos.immutable(),
				1,
				DiviningRodItem.getOverlayColor(checkedState)
			)
		}
	}
}