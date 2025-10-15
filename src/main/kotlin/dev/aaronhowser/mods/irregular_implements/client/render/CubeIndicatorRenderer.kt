package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
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
		collectIndicators(event)
	}

	fun collectIndicators(event: ClientTickEvent.Post) {
		RedstoneToolRenderer.addCubeIndicators(event)
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

		poseStack.pushPose()

		poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

		for (indicator in cubeIndicators) {
			RenderUtil.renderDebugCube(
				poseStack,
				indicator.target.center,
				indicator.size,
				indicator.color
			)
		}

		poseStack.popPose()
	}

}