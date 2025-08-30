package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
import dev.aaronhowser.mods.irregular_implements.item.BiomeCapsuleItem
import dev.aaronhowser.mods.irregular_implements.item.BiomePainterItem
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
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

		val correctBiomeColor = ClientConfig.BIOME_PAINTER_CORRECT_BIOME_CUBE_COLOR.get()
		val incorrectBiomeColor = ClientConfig.BIOME_PAINTER_INCORRECT_BIOME_CUBE_COLOR.get()
		val selectedColor = ClientConfig.BIOME_PAINTER_SELECTED_INCORRECT_BIOME_CUBE_COLOR.get()

		val correctSize = ClientConfig.BIOME_PAINTER_CORRECT_BIOME_CUBE_SIZE.get().toFloat()
		val incorrectSize = ClientConfig.BIOME_PAINTER_INCORRECT_BIOME_CUBE_SIZE.get().toFloat()

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