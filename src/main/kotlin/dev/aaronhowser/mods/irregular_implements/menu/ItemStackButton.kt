package dev.aaronhowser.mods.irregular_implements.menu

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import net.minecraft.world.item.ItemStack

class ItemStackButton(
	x: Int = 0,
	y: Int = 0,
	width: Int,
	height: Int,
	val itemStack: ItemStack,
	onPress: OnPress,
	message: Component = Component.empty(),
	private val font: Font
) : Button(x, y, width, height, message, onPress, DEFAULT_NARRATION) {

	override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
		baseRenderWidget(guiGraphics, mouseX, mouseY, partialTick)
		renderItemStack(guiGraphics)
	}

	private fun renderItemStack(guiGraphics: GuiGraphics) {
		if (itemStack.isEmpty) return

		guiGraphics.renderItem(
			itemStack,
			x + (width - 16) / 2,
			y + (height - 16) / 2
		)
	}

	private fun baseRenderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
		val minecraft = Minecraft.getInstance()
		guiGraphics.setColor(1.0f, 1.0f, 1.0f, this.alpha)
		RenderSystem.enableBlend()
		RenderSystem.enableDepthTest()
		guiGraphics.blitSprite(SPRITES[this.active, this.isHovered], this.x, this.y, this.getWidth(), this.getHeight())
		guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
		val i = fgColor
		this.renderString(guiGraphics, minecraft.font, i or (Mth.ceil(this.alpha * 255.0f) shl 24))
	}

}