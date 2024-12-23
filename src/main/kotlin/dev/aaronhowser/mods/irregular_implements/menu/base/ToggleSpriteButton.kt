package dev.aaronhowser.mods.irregular_implements.menu.base

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth

class ToggleSpriteButton(
    x: Int = 0,
    y: Int = 0,
    width: Int,
    height: Int,
    private var currentState: Boolean = false,
    private val messageOff: Component = Component.empty(),
    private val messageOn: Component = messageOff,
    private val spriteWidth: Int,
    private val spriteHeight: Int,
    private val spriteOn: ResourceLocation,
    private val spriteOff: ResourceLocation,
    private val font: Font,
    onPress: OnPress,
    narration: CreateNarration? = null
) : Button(
    x,
    y,
    width,
    height,
    messageOff,
    onPress,
    narration ?: DEFAULT_NARRATION
) {

    override fun onPress() {
        super.onPress()
        this.currentState = !this.currentState
    }

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        baseRenderWidget(guiGraphics, mouseX, mouseY, partialTick)

        val i = this.x + this.getWidth() / 2 - this.spriteWidth / 2
        val j = this.y + this.getHeight() / 2 - this.spriteHeight / 2
        guiGraphics.blitSprite(
            if (this.currentState) this.spriteOn else this.spriteOff,
            i,
            j,
            this.spriteWidth,
            this.spriteHeight
        )

        if (isMouseOver(mouseX.toDouble(), mouseY.toDouble())) {
            renderToolTip(guiGraphics, mouseX, mouseY)
        }
    }

    private fun renderToolTip(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        if (this.message == Component.empty()) return

        guiGraphics.renderComponentTooltip(
            font,
            listOf(this.message),
            mouseX,
            mouseY
        )
    }

    override fun renderString(guiGraphics: GuiGraphics, font: Font, color: Int) {
        // Do nothing
    }

    override fun getMessage(): Component {
        return if (this.currentState) {
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
        guiGraphics.blitSprite(SPRITES[this.active, this.isHovered], this.x, this.y, this.getWidth(), this.getHeight())
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        val i = fgColor
        this.renderString(guiGraphics, minecraft.font, i or (Mth.ceil(this.alpha * 255.0f) shl 24))
    }

}