package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.aaron.client.AaronClientUtil
import dev.aaronhowser.mods.irregular_implements.item.LavaCharmItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.util.Mth

object LavaProtectionOverlayRenderer {

	val LAYER_NAME = OtherUtil.modResource("lava_protection")
	private val SPRITE_LOCATION = OtherUtil.modResource("lava_protection")

	private const val WIDTH = 9

	fun tryRender(guiGraphics: GuiGraphics, deltaTracker: DeltaTracker) {
		val player = AaronClientUtil.localPlayer ?: return
		if (player.isCreative || player.isSpectator) return
		val lavaProtector = LavaCharmItem.getFirstLavaProtector(player) ?: return

		val charge = lavaProtector.get(ModDataComponents.CHARGE) ?: return
		val maxCharge = LavaCharmItem.MAX_CHARGE

		val percentCharged = charge.toFloat() / maxCharge.toFloat()
		val amountSprites = Mth.ceil(percentCharged * 10f)

		val rightPos = guiGraphics.guiWidth() / 2 + 91
		val height = guiGraphics.guiHeight() - Minecraft.getInstance().gui.rightHeight

		for (i in 0 until amountSprites) {

			var x = rightPos - i * 8 - WIDTH
			var uPos = 0

			if (i == amountSprites - 1) {
				val amountLeft = percentCharged * 10f - i

				x += 8 - (amountLeft * 8).toInt()
				uPos = 8 - (amountLeft * 8).toInt()
			}

			guiGraphics.blitSprite(
				SPRITE_LOCATION,
				WIDTH,
				WIDTH,
				uPos,
				0,
				x,
				height,
				WIDTH,
				WIDTH
			)
		}

		Minecraft.getInstance().gui.rightHeight += 10
	}

}