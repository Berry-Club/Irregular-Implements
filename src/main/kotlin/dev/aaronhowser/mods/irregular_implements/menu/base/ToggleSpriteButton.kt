package dev.aaronhowser.mods.irregular_implements.menu.base

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth

class ToggleSpriteButton(
    width: Int,
    height: Int,
    private val messageOff: Component,
    private val messageOn: Component = messageOff,
    private val spriteWidth: Int,
    private val spriteHeight: Int,
    private val spriteOn: ResourceLocation,
    private val spriteOff: ResourceLocation,
    onPress: OnPress,
    narration: CreateNarration?
) : Button(
    0,
    0,
    width,
    height,
    messageOff,
    onPress,
    narration ?: DEFAULT_NARRATION
) {

    var toggledOn: Boolean = false

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        baseRenderWidget(guiGraphics, mouseX, mouseY, partialTick)

        val i = this.x + this.getWidth() / 2 - this.spriteWidth / 2
        val j = this.y + this.getHeight() / 2 - this.spriteHeight / 2
        guiGraphics.blitSprite(
            if (this.toggledOn) this.spriteOn else this.spriteOff,
            i,
            j,
            this.spriteWidth,
            this.spriteHeight
        )
    }

    override fun getMessage(): Component {
        return if (this.toggledOn) {
            this.messageOn
        } else {
            this.messageOff
        }
    }

    // Literally just a copy of AbstractButton#renderWidget
    private fun baseRenderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val minecraft = Minecraft.getInstance()
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, this.alpha)
        RenderSystem.enableBlend()
        RenderSystem.enableDepthTest()
        guiGraphics.blitSprite(SPRITES[active, this.isHoveredOrFocused], this.x, this.y, this.getWidth(), this.getHeight())
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        val i = fgColor
        this.renderString(guiGraphics, minecraft.font, i or (Mth.ceil(this.alpha * 255.0f) shl 24))
    }

}