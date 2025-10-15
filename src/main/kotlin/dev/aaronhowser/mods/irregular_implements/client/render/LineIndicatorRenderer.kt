package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.Minecraft
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent

object LineIndicatorRenderer {

	private class LineIndicator(val start: Vec3, val end: Vec3, var ticksLeft: Int, val color: Int)

	private val lineIndicators: MutableList<LineIndicator> = mutableListOf()

	fun addIndicator(start: Vec3, end: Vec3, duration: Int, color: Int) {
		lineIndicators.add(LineIndicator(start, end, duration, color))
	}

	fun removeIndicators(start: Vec3, end: Vec3) {
		lineIndicators.removeIf { it.start == start && it.end == end }
	}

	fun afterClientTick(event: ClientTickEvent.Post) {
		if (Minecraft.getInstance().isPaused) return

		tickIndicators()
		collectIndicators(event)
	}

	fun collectIndicators(event: ClientTickEvent.Post) {
	}

	private fun tickIndicators() {
		val iterator = lineIndicators.iterator()

		while (iterator.hasNext()) {
			val indicator = iterator.next()
			indicator.ticksLeft--

			if (indicator.ticksLeft <= 0) {
				iterator.remove()
			}
		}
	}

	fun onRenderLevel(event: RenderLevelStageEvent) {
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return

		if (lineIndicators.isEmpty()) return

		val cameraPos = event.camera.position
		val poseStack = event.poseStack

		poseStack.pushPose()

		poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

		for (indicator in lineIndicators) {
//			RenderUtil.renderDebugCube(
//				poseStack,
//				indicator.target.center,
//				indicator.size,
//				indicator.color
//			)
		}

		poseStack.popPose()
	}

}