package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.aaron.AaronExtensions.isClientSide
import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
import dev.aaronhowser.mods.irregular_implements.item.BiomeCapsuleItem
import dev.aaronhowser.mods.irregular_implements.item.BiomePainterItem
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player

object BiomePainterRenderer {

	fun indicatePositions(player: Entity) {
		if (player !is Player || !player.isClientSide) return

		val paintingBiome = BiomeCapsuleItem.getBiomeToPaint(player) ?: return
		val positions = BiomePainterItem.Positions.getPositions(player, paintingBiome)

		val goodPositions = positions.matchingPositions
		val badPositions = positions.untargetedUnmatchingPositions
		val targetPos = positions.targetedUnmatchingPosition

		// Duration is 2 because CubeIndicatorRenderer.afterClientTick is called AFTER this runs,
		// so the Indicators added here would lose 1 tick immediately

		val correctBiomeColor = ClientConfig.CONFIG.biomePainterCorrectBiomeCubeColor.get()
		val incorrectBiomeColor = ClientConfig.CONFIG.biomePainterIncorrectBiomeCubeColor.get()
		val selectedColor = ClientConfig.CONFIG.biomePainterSelectedIncorrectBiomeCubeColor.get()

		val correctSize = ClientConfig.CONFIG.biomePainterCorrectBiomeCubeSize.get().toFloat()
		val incorrectSize = ClientConfig.CONFIG.biomePainterIncorrectBiomeCubeSize.get().toFloat()

		for (pos in goodPositions) {
			CubeIndicatorRenderer.addIndicator(pos, 2, correctBiomeColor, correctSize)
		}

		for (pos in badPositions) {
			CubeIndicatorRenderer.addIndicator(pos, 2, incorrectBiomeColor, incorrectSize)
		}

		if (targetPos != null) {
			CubeIndicatorRenderer.addIndicator(targetPos, 2, selectedColor, incorrectSize)
		}
	}
}