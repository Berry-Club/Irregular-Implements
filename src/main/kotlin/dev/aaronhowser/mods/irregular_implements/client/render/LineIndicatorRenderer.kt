package dev.aaronhowser.mods.irregular_implements.client.render

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.client.renderer.RenderType
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
		RedstoneToolRenderer.addLineIndicators(event)
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
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_WEATHER) return

		if (lineIndicators.isEmpty()) return

		val cameraPos = event.camera.position
		val poseStack = event.poseStack

		poseStack.pushPose()

		poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

		val bufferSource = Minecraft.getInstance().renderBuffers().bufferSource()
		val vertexConsumer = bufferSource.getBuffer(RenderType.lines())

		for (indicator in lineIndicators) {

			//TODO

			LevelRenderer.renderLineBox(
				poseStack,
				vertexConsumer,
				indicator.start.x, indicator.start.y, indicator.start.z,
				indicator.end.x, indicator.end.y, indicator.end.z,
				0.9f, 0.9f, 0.9f,
				1f,
				0.5f, 0.5f, 0.5f
			)

		}

		poseStack.popPose()
	}

}