package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenWithStrings
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class NotificationInterfaceScreen(
    menu: NotificationInterfaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<NotificationInterfaceMenu>(menu, playerInventory, title), ScreenWithStrings {

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        guiGraphics.blit(
            ScreenTextures.Background.OnlineDetector.BACKGROUND,
            this.leftPos,
            this.topPos,
            0f,
            0f,
            ScreenTextures.Background.OnlineDetector.WIDTH,
            ScreenTextures.Background.OnlineDetector.HEIGHT,
            ScreenTextures.Background.OnlineDetector.CANVAS_SIZE,
            ScreenTextures.Background.OnlineDetector.CANVAS_SIZE
        )
    }

    override fun receivedString(stringId: Int, string: String) {
        TODO("Not yet implemented")
    }
}