package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.item.LavaCharmItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.neoforged.neoforge.client.event.RenderGuiEvent

object RenderLavaCharmOverlay {

    val texture = OtherUtil.modResource("lava_protector.png")
    const val FILE_WIDTH = 16
    const val IMAGE_WIDTH = 11

    fun tryRender(event: RenderGuiEvent.Post) {
        val player = ClientUtil.localPlayer ?: return
        val lavaProtector = LavaCharmItem.getFirstLavaProtector(player) ?: return

        val charge = lavaProtector.get(ModDataComponents.CHARGE) ?: return

        event.guiGraphics.blitSprite(
            texture,
            0,
            0,
            IMAGE_WIDTH,
            IMAGE_WIDTH
        )

    }

}