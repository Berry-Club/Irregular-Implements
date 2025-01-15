package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class IgniterScreen(
    menu: IgniterMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<IgniterMenu>(menu, playerInventory, title) {

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val i = (this.width - this.imageWidth) / 2
        val j = (this.height - this.imageHeight) / 2

        guiGraphics.blit(
            ScreenTextures.Background.Igniter.BACKGROUND,
            i,
            j,
            0,
            0,
            ScreenTextures.Background.Igniter.CANVAS_SIZE,
            ScreenTextures.Background.Igniter.CANVAS_SIZE,
        )
    }

}