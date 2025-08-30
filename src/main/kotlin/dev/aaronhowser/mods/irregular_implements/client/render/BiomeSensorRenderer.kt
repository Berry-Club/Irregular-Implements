package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

object BiomeSensorRenderer {

	val LAYER_NAME = OtherUtil.modResource("biome_sensor_gui_layer")

	fun tryRenderBiomeName(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
		val player = ClientUtil.localPlayer ?: return
		if (!player.isHolding(ModItems.BIOME_SENSOR.get())) return

		val level = player.level()
		val biome = level.getBiome(player.blockPosition())

		val biomeName = OtherUtil.getBiomeComponent(biome)

		guiGraphics.drawString(
			Minecraft.getInstance().font,
			biomeName.copy().withColor(biome.value().foliageColor),
			guiGraphics.guiWidth() / 2 + 5,
			guiGraphics.guiHeight() / 2 + 5,
			0xFFFFFF
		)
	}

}