package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class GlobalChatDetectorScreen(
    menu: GlobalChatDetectorMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<GlobalChatDetectorMenu>(menu, playerInventory, title) {

    private val rightPos: Int
        get() = this.leftPos + this.imageWidth
    private val bottomPos: Int
        get() = this.topPos + this.imageHeight

    override fun init() {
        this.imageWidth = ScreenTextures.Background.GlobalChatDetector.WIDTH
        this.imageHeight = ScreenTextures.Background.GlobalChatDetector.HEIGHT

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.inventoryLabelY -= 8
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        guiGraphics.blit(
            ScreenTextures.Background.GlobalChatDetector.BACKGROUND,
            this.leftPos,
            this.topPos,
            0f,
            0f,
            ScreenTextures.Background.GlobalChatDetector.WIDTH,
            ScreenTextures.Background.GlobalChatDetector.HEIGHT,
            ScreenTextures.Background.GlobalChatDetector.CANVAS_SIZE,
            ScreenTextures.Background.GlobalChatDetector.CANVAS_SIZE
        )
    }
}