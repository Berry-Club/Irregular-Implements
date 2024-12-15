package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.item.LavaCharmItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.util.Mth

object RenderLavaProtectionOverlay {

    val LAYER_NAME = OtherUtil.modResource("lava_protection")
    private val SPRITE_LOCATION = OtherUtil.modResource("lava_protection")


    fun tryRender(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
        val player = ClientUtil.localPlayer ?: return
        val lavaProtector = LavaCharmItem.getFirstLavaProtector(player) ?: return

        val charge = lavaProtector.get(ModDataComponents.CHARGE) ?: return
        val maxCharge = LavaCharmItem.MAX_CHARGE

        val percentCharged = charge.toFloat() / maxCharge.toFloat()
        val amountFullSprites = Mth.floor(percentCharged * 10f)

        var rightPos = guiGraphics.guiWidth() / 2 + 91
        val height = guiGraphics.guiHeight() - Minecraft.getInstance().gui.rightHeight

        for (i in 0 until amountFullSprites) {
            guiGraphics.blitSprite(
                SPRITE_LOCATION,
                9,
                9,
                0,
                0,
                rightPos - i * 8 - 9,
                height,
                9,
                9
            )
            rightPos += 9
        }

        val leftOver = percentCharged * 10 - amountFullSprites

        guiGraphics.blitSprite(
            SPRITE_LOCATION,
            9,
            9,
            0,
            0,
            rightPos,
            height,
            Mth.floor(9 * leftOver),
            9
        )

        Minecraft.getInstance().gui.rightHeight += 10
    }

}