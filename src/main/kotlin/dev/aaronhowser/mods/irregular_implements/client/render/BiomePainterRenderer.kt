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
import net.minecraft.world.phys.AABB
import kotlin.jvm.optionals.getOrNull

object BiomePainterRenderer {

	data class BiomePainterPositions(
		val correctBiomePositions: Set<BlockPos>,
		val incorrectBiomePositions: Set<BlockPos>,
		val targetedIncorrectBiomePosition: BlockPos?,
	) {
		val unselectedIncorrectBiomePositions: Set<BlockPos> =
			if (targetedIncorrectBiomePosition != null) {
				incorrectBiomePositions - targetedIncorrectBiomePosition
			} else {
				incorrectBiomePositions
			}
	}

	const val CORRECT_BIOME_CUBE_SIZE = 0.05f
	const val INCORRECT_BIOME_CUBE_SIZE = 0.35f

	const val CORRECT_BIOME_CUBE_COLOR = 0x6622AA00
	const val INCORRECT_BIOME_CUBE_COLOR = 0x66AA2200
	const val SELECTED_INCORRECT_BIOME_CUBE_COLOR = 0x662222AA

	fun renderCubes(player: Entity) {
		if (player !is Player || !player.isClientSide) return

		val paintingBiome = BiomeCapsuleItem.getFirstNonEmptyCapsule(player.inventory)
			?.get(ModDataComponents.BIOME_POINTS)
			?.biome
			?: return

		val positions = getPositions(player, paintingBiome)

		val goodPositions = positions.correctBiomePositions
		val badPositions = positions.unselectedIncorrectBiomePositions
		val targetPos = positions.targetedIncorrectBiomePosition

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

	private fun getPositions(player: Player, biome: Holder<Biome>): BiomePainterPositions {
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

		val targetedIncorrectBiomePosition = getTargetedPos(player, badPositions)

		return BiomePainterPositions(
			correctBiomePositions = goodPositions,
			incorrectBiomePositions = badPositions,
			targetedIncorrectBiomePosition = targetedIncorrectBiomePosition,
		)
	}

	private fun getTargetedPos(player: Player, positions: Set<BlockPos>): BlockPos? {
		val eyePos = player.eyePosition
		val lookVec = player.lookAngle.normalize()
		val endPos = eyePos.add(lookVec.scale(20.0))

		var closestHit: Pair<Double, BlockPos>? = null

		for (pos in positions) {
			val aabb = AABB(pos)

			val hitPos = aabb.clip(eyePos, endPos).getOrNull() ?: continue
			val distSqr = eyePos.distanceToSqr(hitPos)

			if (closestHit == null || distSqr < closestHit.first) {
				closestHit = Pair(distSqr, pos)
			}
		}

		return closestHit?.second
	}
}