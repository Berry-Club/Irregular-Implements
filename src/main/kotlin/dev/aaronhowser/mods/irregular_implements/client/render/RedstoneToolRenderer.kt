package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneWireBlock
import net.neoforged.neoforge.client.event.ClientTickEvent

object RedstoneToolRenderer {

	val WIRE_STRENGTH_UI_LAYER = OtherUtil.modResource("wire_strength")

	fun afterClientTick(event: ClientTickEvent.Post) {
		val player = ClientUtil.localPlayer ?: return

		val itemInHand = player.mainHandItem
		if (!itemInHand.`is`(ModItems.REDSTONE_TOOL)) return

		val toolLocation = itemInHand.get(ModDataComponents.GLOBAL_POS) ?: return
		if (toolLocation.dimension != player.level().dimension()) return

		val toolBlockPos = toolLocation.pos

		CubeIndicatorRenderer.addIndicator(
			toolBlockPos,
			1,
			0x32FF0000
		)

		val toolBlockEntity = player.level().getBlockEntity(toolLocation.pos) as? RedstoneToolLinkable
		if (toolBlockEntity != null) {
			val linkedPos = toolBlockEntity.linkedPos
			if (linkedPos != null) {
				CubeIndicatorRenderer.addIndicator(
					linkedPos,
					1,
					0x320000FF
				)
			}
		}
	}

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