package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.item.BiomeCapsuleItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Holder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.biome.Biome

object BiomePainterRenderer {

	fun renderCubes(player: Entity) {
		if (player !is Player || !player.isClientSide) return

		val paintingBiome = BiomeCapsuleItem.getFirstNonEmptyCapsule(player.inventory)
			?.get(ModDataComponents.BIOME_POINTS)
			?.biome
			?: return

		val goodColor = 0x6622AA00
		val badColor = 0x66AA2200

		val (goodPositions, badPositions) = getPositions(player, paintingBiome)

		// Duration is 2 because CubeIndicatorRenderer.afterClientTick is called AFTER this runs,
		// so the Indicators added here would lose 1 tick immediately

		for (pos in goodPositions) {
			CubeIndicatorRenderer.addIndicator(pos, 2, goodColor, size = 0.05f)
		}

		for (pos in badPositions) {
			CubeIndicatorRenderer.addIndicator(pos, 2, badColor, size = 0.35f)
		}
	}

	private fun getPositions(player: Player, biome: Holder<Biome>): Pair<Set<BlockPos>, Set<BlockPos>> {
		val level = player.level()
		val playerPos = player.blockPosition()

		val horizontalRadius = 10
		val verticalRadius = 5

		val goodPositions = mutableSetOf<BlockPos>()
		val badPositions = mutableSetOf<BlockPos>()

		val toCheck = arrayListOf(playerPos)

		while (toCheck.isNotEmpty()) {
			val pos = toCheck.removeLast()
			if (!level.isLoaded(pos)) continue

			val biomeAtPos = level.getBiome(pos)

			if (biomeAtPos != biome) {
				badPositions.add(pos)
				continue
			}

			goodPositions.add(pos)

			for (direction in Direction.entries) {
				val offset = pos.relative(direction)

				if (offset in goodPositions || offset in badPositions) continue

				val dy = offset.y - playerPos.y
				if (dy < -verticalRadius || dy > verticalRadius) continue

				val dx = offset.x - playerPos.x
				val dz = offset.z - playerPos.z

				val hDistSqr = dx * dx + dz * dz
				if (hDistSqr > horizontalRadius * horizontalRadius) continue

				toCheck.add(offset)
			}
		}

		return Pair(goodPositions, badPositions)
	}
}