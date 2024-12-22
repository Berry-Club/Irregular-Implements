package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.properties.Delegates

class ChatDetectorScreen(
    val chatDetectorBlockEntity: ChatDetectorBlockEntity,
    title: Component = ModBlocks.CHAT_DETECTOR.get().name
) : Screen(title) {

    var leftPos: Int by Delegates.notNull()
    var topPos: Int by Delegates.notNull()

    override fun init() {
        leftPos = (width - ScreenTextures.Backgrounds.ChatDetector.WIDTH) / 2
        topPos = (height - ScreenTextures.Backgrounds.ChatDetector.HEIGHT) / 2
    }

    // Rendering

    override fun renderMenuBackground(guiGraphics: GuiGraphics) {
        guiGraphics.blit(
            ScreenTextures.Backgrounds.ChatDetector.BACKGROUND,
            leftPos,
            topPos,
            0f,
            0f,
            ScreenTextures.Backgrounds.ChatDetector.WIDTH,
            ScreenTextures.Backgrounds.ChatDetector.HEIGHT,
            ScreenTextures.Backgrounds.ChatDetector.CANVAS_SIZE,
            ScreenTextures.Backgrounds.ChatDetector.CANVAS_SIZE
        )
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)

        guiGraphics.drawString(
            font,
            title,
            leftPos + 10,
            topPos + 10,
            0x403030,
            false
        )
    }

    // Behavior

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun tick() {
        if (chatDetectorBlockEntity.isRemoved) {
            onClose()
        }

    }


}