package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.core.BlockPos
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent

object CubeIndicatorRenderer {

	private class Indicator(val target: BlockPos, var duration: Int, val color: Int, val size: Float)

	private val indicators: MutableList<Indicator> = mutableListOf()

	fun addIndicator(target: BlockPos, duration: Int, color: Int, size: Float = 0.99f) {
		indicators.add(Indicator(target, duration, color, size))
	}

	fun removeIndicatorsAt(target: BlockPos) {
		indicators.removeIf { it.target == target }
	}

	fun afterClientTick(event: ClientTickEvent.Post) {
		val iterator = indicators.iterator()
		while (iterator.hasNext()) {
			val indicator = iterator.next()
			indicator.duration--

			if (indicator.duration <= 0) {
				iterator.remove()
			}
		}
	}

	fun onRenderLevel(event: RenderLevelStageEvent) {
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return

		if (indicators.isEmpty()) return

		val cameraPos = event.camera.position
		val poseStack = event.poseStack

		poseStack.pushPose()

		poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

		for (indicator in indicators) {
			RenderUtil.renderCube(
				poseStack,
				indicator.target.center,
				indicator.size,
				indicator.color
			)
		}

		poseStack.popPose()
	}

}