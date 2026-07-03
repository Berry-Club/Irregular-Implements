package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.aaron.client.render.AaronRenderUtil
import dev.aaronhowser.mods.aaron.misc.AaronDsls.withPose
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent

object CubeIndicatorRenderer {

	private class CubeIndicator(val target: BlockPos, var ticksLeft: Int, val color: Int, val size: Float)

	private val cubeIndicators: MutableList<CubeIndicator> = mutableListOf()

	fun addIndicator(target: BlockPos, duration: Int, color: Int, size: Float = 0.99f) {
		cubeIndicators.add(CubeIndicator(target, duration, color, size))
	}

	fun removeIndicatorsAt(target: BlockPos) {
		cubeIndicators.removeIf { it.target == target }
	}

	fun afterClientTick(event: ClientTickEvent.Post) {
		if (Minecraft.getInstance().isPaused) return

		tickIndicators()

		DiviningRodRenderer.addCubeIndicators(event)
		TargetPositionRenderer.addCubeIndicators(event)
	}

	private fun tickIndicators() {
		val iterator = cubeIndicators.iterator()

		while (iterator.hasNext()) {
			val indicator = iterator.next()
			indicator.ticksLeft--

			if (indicator.ticksLeft <= 0) {
				iterator.remove()
			}
		}
	}

	fun onRenderLevel(event: RenderLevelStageEvent) {
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_WEATHER) return

		if (cubeIndicators.isEmpty()) return

		val cameraPos = event.camera.position
		val poseStack = event.poseStack

		poseStack.withPose {
			poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

			for (indicator in cubeIndicators) {
				val radius = indicator.size / 2
				val center = indicator.target.center
				val minX = center.x - radius
				val minY = center.y - radius
				val minZ = center.z - radius
				val maxX = center.x + radius
				val maxY = center.y + radius
				val maxZ = center.z + radius

				AaronRenderUtil.renderCubeThroughWalls(
					poseStack,
					minX, minY, minZ,
					maxX, maxY, maxZ,
					indicator.color
				)
			}
		}
	}

}