package dev.aaronhowser.mods.irregular_implements.menu.base

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Button.OnPress
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.util.function.Supplier

class MultiStageSpriteButton(
    x: Int = 0,
    y: Int = 0,
    width: Int,
    height: Int,
    private val stages: List<Stage>,
    private val currentStageGetter: Supplier<Int>,
    private val font: Font,
    onPress: OnPress,
    narration: CreateNarration? = null
) : Button(
    x,
    y,
    width,
    height,
    stages.first().message,
    onPress,
    narration ?: DEFAULT_NARRATION
) {

    private val currentStage
        get() = stages[currentStageGetter.get()]

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        baseRenderWidget(guiGraphics)

        val sprite = currentStage.sprite

        if (sprite != null) {
            val spriteWidth = currentStage.spriteWidth
            val spriteHeight = currentStage.spriteHeight

            val spriteLeft = this.x + this.getWidth() / 2 - spriteWidth / 2
            val spriteTop = this.y + this.getHeight() / 2 - spriteHeight / 2

            guiGraphics.blitSprite(
                sprite,
                spriteLeft,
                spriteTop,
                spriteWidth,
                spriteHeight
            )
        }

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

    override fun getMessage(): Component {
        return currentStage.message
    }

    private fun baseRenderWidget(guiGraphics: GuiGraphics) {
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, this.alpha)
        RenderSystem.enableBlend()
        RenderSystem.enableDepthTest()
        guiGraphics.blitSprite(SPRITES[this.active, this.isHovered], this.x, this.y, this.getWidth(), this.getHeight())
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
    }

    class Builder(private val font: Font) {

        private var x: Int = 0
        private var y: Int = 0
        private var width: Int = 0
        private var height: Int = 0

        private var currentStageGetter: Supplier<Int> = Supplier { 0 }
        private var onPress: OnPress = OnPress { }

        private val stages: MutableList<Stage> = mutableListOf()

        fun addStage(
            message: Component,
            sprite: ScreenTextures.Sprite
        ): Builder {
            addStage(
                message = message,
                sprite = sprite.texture,
                spriteWidth = sprite.width,
                spriteHeight = sprite.height
            )

            return this
        }

        fun addStage(
            message: Component,
            sprite: ResourceLocation?,
            spriteWidth: Int = 0,
            spriteHeight: Int = 0
        ): Builder {
            stages.add(
                Stage(
                    message = message,
                    sprite = sprite,
                    spriteWidth = spriteWidth,
                    spriteHeight = spriteHeight
                )
            )
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

        fun size(size: Int): Builder {
            this.width = size
            this.height = size
            return this
        }

        fun currentStageGetter(currentStageGetter: Supplier<Int>): Builder {
            this.currentStageGetter = currentStageGetter
            return this
        }

        fun onPress(onPress: OnPress): Builder {
            this.onPress = onPress
            return this
        }

        fun onPress(onPress: () -> Unit): Builder {
            this.onPress = OnPress { onPress() }
            return this
        }

        fun build(): MultiStageSpriteButton {
            return MultiStageSpriteButton(
                x = x,
                y = y,
                width = width,
                height = height,
                stages = stages,
                currentStageGetter = currentStageGetter,
                font = font,
                onPress = onPress
            )
        }
    }

    class Stage(
        val message: Component,
        val sprite: ResourceLocation?,
        val spriteWidth: Int,
        val spriteHeight: Int
    )

}