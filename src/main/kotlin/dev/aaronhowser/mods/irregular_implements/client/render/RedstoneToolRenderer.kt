package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.RenderUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneWireBlock
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent

object RedstoneToolRenderer {

	private var mainBlockPos: BlockPos? = null
	private var linkedBlockPos: BlockPos? = null

	fun afterClientTick(event: ClientTickEvent.Post) {
		this.mainBlockPos = null
		this.linkedBlockPos = null

		val player = ClientUtil.localPlayer ?: return

		val itemInHand = player.mainHandItem
		if (!itemInHand.`is`(ModItems.REDSTONE_TOOL)) return

		val toolLocation = itemInHand.get(ModDataComponents.LOCATION) ?: return
		if (toolLocation.dimension != player.level().dimension()) return

		val toolBlockPos = toolLocation.blockPos
		this.mainBlockPos = toolBlockPos

		val toolBlockEntity = player.level().getBlockEntity(toolLocation.blockPos) as? RedstoneToolLinkable ?: return
		val linkedPos = toolBlockEntity.linkedPos ?: return

		this.linkedBlockPos = linkedPos
	}

	fun onRenderLevel(event: RenderLevelStageEvent) {
		if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return

		val mainPos = this.mainBlockPos ?: return
		val linkedPos = this.linkedBlockPos

		val cameraPos = event.camera.position
		val poseStack = event.poseStack

		poseStack.pushPose()

		poseStack.mulPose(event.modelViewMatrix)
		poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)

		RenderUtil.renderDebugCube(
			poseStack,
			mainPos.center,
			0.9f,
			0x32FF0000
		)

		if (linkedPos != null) {
			RenderUtil.renderDebugCube(
				poseStack,
				linkedPos.center,
				0.9f,
				0x320000FF
			)
		}

		poseStack.popPose()
	}

	val LAYER_NAME = OtherUtil.modResource("wire_strength")

	fun tryRenderWireStrength(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
		val player = ClientUtil.localPlayer ?: return
		if (!player.isHolding(ModItems.REDSTONE_TOOL.get())) return

		val level = player.level()

		val clipResult = OtherUtil.getPovResult(level, player, player.blockInteractionRange())

		val pos = clipResult.blockPos
		val state = level.getBlockState(pos)

		if (!state.`is`(Blocks.REDSTONE_WIRE)) return

		val strength = state.getValue(RedStoneWireBlock.POWER)

		guiGraphics.drawString(
			Minecraft.getInstance().font,
			strength.toString(),
			guiGraphics.guiWidth() / 2 + 5,
			guiGraphics.guiHeight() / 2 + 5,
			0xFF0000
		)
	}

}