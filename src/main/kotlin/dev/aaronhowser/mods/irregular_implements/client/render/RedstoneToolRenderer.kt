package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneWireBlock

object RedstoneToolRenderer {

	val WIRE_STRENGTH_UI_LAYER = OtherUtil.modResource("wire_strength")

	fun tryRenderWireStrength(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
		val player = AaronClientUtil.localPlayer ?: return
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