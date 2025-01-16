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

    override fun init() {
        this.imageWidth = background.width
        this.imageHeight = background.height

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.titleLabelX -= 4
    }

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        background.render(
            guiGraphics,
            this.leftPos,
            this.topPos
        )
    }

}