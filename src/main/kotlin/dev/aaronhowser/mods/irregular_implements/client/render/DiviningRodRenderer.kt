package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent

//FIXME: The projection matrix or whatever is broken
object DiviningRodRenderer {

	private class Indicator(val target: BlockPos, var duration: Int, val color: Int)

	private val indicators: MutableList<Indicator> = mutableListOf()

	//TODO: Probably laggy, maybe make it only check once a second?
	fun afterClientTick(event: ClientTickEvent.Post) {
		val iterator = indicators.iterator()
		while (iterator.hasNext()) {
			val indicator = iterator.next()
			indicator.duration--

			if (indicator.duration <= 0) {
				iterator.remove()
			}
		}

		val player = ClientUtil.localPlayer ?: return
		val playerPos = player.blockPosition()
		val level = player.level()

		val offHandItem = player.offhandItem
		val mainHandItem = player.mainHandItem

		val offHandTag = if (offHandItem.`is`(ModItems.DIVINING_ROD)) offHandItem.get(ModDataComponents.BLOCK_TAG) else null
		val mainHandTag = if (mainHandItem.`is`(ModItems.DIVINING_ROD)) mainHandItem.get(ModDataComponents.BLOCK_TAG) else null

		if (offHandTag == null && mainHandTag == null) {
			indicators.clear()
			return
		}

		val radius = ServerConfig.DIVINING_ROD_CHECK_RADIUS.get()

		val positions = BlockPos.betweenClosedStream(
			playerPos.offset(-radius, -radius, -radius),
			playerPos.offset(radius, radius, radius)
		)

		for (checkedPos in positions) {
			if (!level.isLoaded(checkedPos)) continue

			val checkedState = level.getBlockState(checkedPos)

			val matchesOffHand = offHandTag != null && checkedState.`is`(offHandTag)
			val matchesMainHand = mainHandTag != null && checkedState.`is`(mainHandTag)

			if (!matchesOffHand && !matchesMainHand) continue
			if (indicators.any { it.target == checkedPos }) continue

			val indicator = Indicator(
				checkedPos.immutable(),
				160,
				DiviningRodItem.getOverlayColor(checkedState)
			)

			indicators.add(indicator)
		}
	}

	fun onRenderLevel(event: RenderLevelStageEvent) {
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return

		if (indicators.isEmpty()) return

		val cameraPos = Minecraft.getInstance().entityRenderDispatcher.camera.position
		val poseStack = event.poseStack

		poseStack.pushPose()

		poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

		val buffer = Minecraft.getInstance()
			.renderBuffers()
			.bufferSource()
			.getBuffer(RenderType.debugQuads())

		for (indicator in indicators) {
			RenderUtil.renderCube(
				poseStack,
				buffer,
				indicator.target.center,
				0.99f,
				indicator.color
			)
		}

		poseStack.popPose()
	}

}