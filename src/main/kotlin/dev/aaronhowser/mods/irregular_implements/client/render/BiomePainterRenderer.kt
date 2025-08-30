package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.item.BiomeCapsuleItem
import dev.aaronhowser.mods.irregular_implements.item.BiomePainterItem
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player

object BiomePainterRenderer {

	const val CORRECT_BIOME_CUBE_SIZE = 0.05f
	const val INCORRECT_BIOME_CUBE_SIZE = 0.35f

	const val CORRECT_BIOME_CUBE_COLOR = 0x6622AA00
	const val INCORRECT_BIOME_CUBE_COLOR = 0x66AA2200
	const val SELECTED_INCORRECT_BIOME_CUBE_COLOR = 0x662222AA

	fun indicatePositions(player: Entity) {
		if (player !is Player || !player.isClientSide) return

		val paintingBiome = BiomeCapsuleItem.getBiomeToPaint(player) ?: return
		val positions = BiomePainterItem.Positions.getPositions(player, paintingBiome)

		val goodPositions = positions.matchingPositions
		val badPositions = positions.untargetedUnmatchingPositions
		val targetPos = positions.targetedUnmatchingPosition

		// Duration is 2 because CubeIndicatorRenderer.afterClientTick is called AFTER this runs,
		// so the Indicators added here would lose 1 tick immediately

		for (pos in goodPositions) {
			CubeIndicatorRenderer.addIndicator(pos, 2, CORRECT_BIOME_CUBE_COLOR, CORRECT_BIOME_CUBE_SIZE)
		}

		for (pos in badPositions) {
			CubeIndicatorRenderer.addIndicator(pos, 2, INCORRECT_BIOME_CUBE_COLOR, INCORRECT_BIOME_CUBE_SIZE)
		}

		if (targetPos != null) {
			CubeIndicatorRenderer.addIndicator(targetPos, 2, SELECTED_INCORRECT_BIOME_CUBE_COLOR, INCORRECT_BIOME_CUBE_SIZE)
		}
	}
}