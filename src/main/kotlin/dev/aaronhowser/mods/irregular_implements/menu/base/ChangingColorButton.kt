package dev.aaronhowser.mods.irregular_implements.menu.base

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import java.util.function.Supplier

class ChangingColorButton(
    x: Int = 0,
    y: Int = 0,
    width: Int,
    height: Int,
    private val messagesGetter: Supplier<List<Component>>,
    private val colorGetter: Supplier<Int>,
    private val font: Font,
    onPress: OnPress,
    narration: CreateNarration? = null
) : Button(
    x,
    y,
    width,
    height,
    messagesGetter.get().firstOrNull() ?: Component.empty(),
    onPress,
    narration ?: DEFAULT_NARRATION
) {

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val color = this.colorGetter.get()

        val red = (color shr 16 and 255) / 255.0f
        val green = (color shr 8 and 255) / 255.0f
        val blue = (color and 255) / 255.0f

        guiGraphics.setColor(red, green, blue, this.alpha)
        RenderSystem.enableBlend()
        RenderSystem.enableDepthTest()
        guiGraphics.blitSprite(SPRITES[this.active, this.isHovered], this.x, this.y, this.getWidth(), this.getHeight())
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)

        if (isMouseOver(mouseX.toDouble(), mouseY.toDouble())) {
            renderToolTip(guiGraphics, mouseX, mouseY)
        }
    }

    private fun renderToolTip(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        guiGraphics.renderComponentTooltip(
            font,
            messagesGetter.get(),
            mouseX,
            mouseY
        )
    }

    class Stage(
        val message: Component,
        val color: Int
    )

}