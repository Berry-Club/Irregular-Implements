package dev.aaronhowser.mods.irregular_implements.menu.base

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Button.OnPress
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import java.util.function.Supplier

class MultiStateSpriteButton(
    x: Int = 0,
    y: Int = 0,
    width: Int,
    height: Int,
    private val amountStages: Int,
    private val currentStateGetter: Supplier<Int>,
    private val sprites: List<ResourceLocation>,
    private val messages: List<Component>,
    private val spriteWidth: Int,
    private val spriteHeight: Int,
    private val font: Font,
    onPress: OnPress,
    narration: CreateNarration? = null
) : Button(
    x,
    y,
    width,
    height,
    messages.first(),
    onPress,
    narration ?: DEFAULT_NARRATION
) {

    init {
        require(amountStages == sprites.size) { "Amount of sprites must match amount of stages" }
        require(amountStages == messages.size) { "Amount of messages must match amount of stages" }
    }

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        baseRenderWidget(guiGraphics, mouseX, mouseY, partialTick)

        val i = this.x + this.getWidth() / 2 - this.spriteWidth / 2
        val j = this.y + this.getHeight() / 2 - this.spriteHeight / 2

        val sprite = sprites[currentStateGetter.get()]

        guiGraphics.blitSprite(
            sprite,
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
        return messages[currentStateGetter.get()]
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

    class Builder(private val font: Font) {
        private val sprites: MutableList<ResourceLocation> = mutableListOf()
        private val messages: MutableList<Component> = mutableListOf()

        private var amountStages = 0

        private var x: Int = 0
        private var y: Int = 0
        private var width: Int = 0
        private var height: Int = 0
        private var spriteWidth: Int = 0
        private var spriteHeight: Int = 0

        private var currentStateGetter: Supplier<Int> = Supplier { 0 }
        private var onPress: OnPress = OnPress { }


        fun addStage(sprite: ResourceLocation, message: Component): Builder {
            sprites.add(sprite)
            messages.add(message)
            amountStages++
            return this
        }

        fun spriteDimensions(width: Int, height: Int): Builder {
            spriteWidth = width
            spriteHeight = height
            return this
        }

        fun location(x: Int, y: Int): Builder {
            this.x = x
            this.y = y
            return this
        }

        fun size(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            return this
        }

        fun currentStateGetter(currentStateGetter: Supplier<Int>): Builder {
            this.currentStateGetter = currentStateGetter
            return this
        }

        fun onPress(onPress: OnPress): Builder {
            this.onPress = onPress
            return this
        }

        fun build(): MultiStateSpriteButton {
            return MultiStateSpriteButton(
                x,
                y,
                width,
                height,
                amountStages,
                currentStateGetter,
                sprites,
                messages,
                spriteWidth,
                spriteHeight,
                font,
                onPress
            )
        }
    }

}