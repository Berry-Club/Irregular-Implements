package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent

object TargetPositionRenderer {

	private val positions: MutableList<BlockPos> = mutableListOf()

	fun afterClientTick(event: ClientTickEvent.Post) {
		positions.clear()

		val player = ClientUtil.localPlayer ?: return

		val mainHandItemLocation = player.mainHandItem.get(ModDataComponents.LOCATION)
		val offHandItemLocation = player.offhandItem.get(ModDataComponents.LOCATION)

		val level = player.level()

		if (mainHandItemLocation != null && mainHandItemLocation.dimension == level.dimension()) {
			positions.add(mainHandItemLocation.blockPos)
		}

		if (offHandItemLocation != null && offHandItemLocation.dimension == level.dimension()) {
			positions.add(offHandItemLocation.blockPos)
		}
	}

	fun onRenderLevel(event: RenderLevelStageEvent) {
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return

		val cameraPos = event.camera.position
		val poseStack = event.poseStack

		poseStack.pushPose()

		poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

		val buffer = Minecraft.getInstance()
			.renderBuffers()
			.bufferSource()
			.getBuffer(RenderType.debugQuads())

		for (position in positions) {
			RenderUtil.renderCube(
				poseStack,
				buffer,
				position.center,
				0.9f,
				0x3200FF00
			)
		}

		poseStack.popPose()
	}

}