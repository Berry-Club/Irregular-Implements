package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ImbuingStationScreen(
    menu: ImbuingStationMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ImbuingStationMenu>(menu, playerInventory, title) {

    private val background = ScreenTextures.Backgrounds.ImbuingStation

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        background.render(
            guiGraphics,
            this.leftPos,
            this.topPos
        )
    }

}