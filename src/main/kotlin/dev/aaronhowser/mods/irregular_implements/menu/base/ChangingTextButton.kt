package dev.aaronhowser.mods.irregular_implements.menu.base

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import java.util.function.Supplier

class ChangingTextButton(
	x: Int,
	y: Int,
	width: Int,
	height: Int,
	private val messageGetter: Supplier<Component>,
	onPress: OnPress
) : Button(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION) {

	override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
		val minecraft = Minecraft.getInstance()
		guiGraphics.setColor(1.0f, 1.0f, 1.0f, this.alpha)
		RenderSystem.enableBlend()
		RenderSystem.enableDepthTest()
		guiGraphics.blitSprite(SPRITES[this.active, this.isHovered], this.x, this.y, this.getWidth(), this.getHeight())
		guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
		val i = fgColor
		this.renderString(guiGraphics, minecraft.font, i or (Mth.ceil(this.alpha * 255.0f) shl 24))
	}

	override fun getMessage(): Component {
		return this.messageGetter.get()
	}

}