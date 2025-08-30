package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.item.BiomeCapsuleItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.neoforged.neoforge.client.event.ClientTickEvent

object BiomePainterRenderer {

	fun afterClientTick(event: ClientTickEvent.Post) {
		val player = ClientUtil.localPlayer ?: return

		if (!player.isHolding(ModItems.BIOME_PAINTER.get())) return
		val paintingBiome = BiomeCapsuleItem.getFirstNonEmptyCapsule(player.inventory)
			?.get(ModDataComponents.BIOME_POINTS)
			?.biome
			?: return

		val level = player.level()

		val horizontalRadius = 15
		val verticalRadius = 5

		val goodColor = 0x6622AA00
		val badColor = 0x66AA2200

		val iterator = BlockPos.betweenClosed(
			player.blockPosition().offset(-horizontalRadius, -verticalRadius, -horizontalRadius),
			player.blockPosition().offset(horizontalRadius, verticalRadius, horizontalRadius)
		)

		for (pos in iterator) {
			val biomeAtPos = level.getBiome(pos)
			if (biomeAtPos == paintingBiome) {
				CubeIndicatorRenderer.addIndicator(pos.immutable(), 1, goodColor, 0.03f)
			} else {
				CubeIndicatorRenderer.addIndicator(pos.immutable(), 1, badColor, 0.2f)
			}
		}
	}

}