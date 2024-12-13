package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.item.LavaCharmItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics

object RenderLavaCharmOverlay {

    val LAYER_NAME = OtherUtil.modResource("lava_protection")

    val texture = OtherUtil.modResource("lava_protector.png")
    const val FILE_WIDTH = 16
    const val IMAGE_WIDTH = 11

    fun tryRender(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
        val player = ClientUtil.localPlayer ?: return
        val lavaProtector = LavaCharmItem.getFirstLavaProtector(player) ?: return

        val charge = lavaProtector.get(ModDataComponents.CHARGE) ?: return

    }

}