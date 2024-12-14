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

    private const val IMAGE_SCALE = 14
    private const val IMAGE_WIDTH = 9

    fun tryRender(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
        val player = ClientUtil.localPlayer ?: return
        val lavaProtector = LavaCharmItem.getFirstLavaProtector(player) ?: return

        val charge = lavaProtector.get(ModDataComponents.CHARGE) ?: return
        val maxCharge = LavaCharmItem.MAX_CHARGE

        val percentCharged = charge.toFloat() / maxCharge.toFloat()
        val amountFullSprites = Mth.floor(percentCharged * 10f)

        var leftPos = guiGraphics.guiWidth() / 2
        val height = guiGraphics.guiHeight() - Minecraft.getInstance().gui.rightHeight

        for (i in 0 until amountFullSprites) {
            guiGraphics.blitSprite(
                SPRITE_LOCATION,
                IMAGE_SCALE,
                IMAGE_SCALE,
                0,
                0,
                leftPos,
                height,
                IMAGE_WIDTH,
                IMAGE_WIDTH
            )
            leftPos += IMAGE_WIDTH
        }

        val leftOver = percentCharged * 10 - amountFullSprites

        guiGraphics.blitSprite(
            SPRITE_LOCATION,
            IMAGE_SCALE,
            IMAGE_SCALE,
            0,
            0,
            leftPos,
            height,
            Mth.floor(IMAGE_WIDTH * leftOver),
            IMAGE_WIDTH
        )

        Minecraft.getInstance().gui.rightHeight += 10
    }

}