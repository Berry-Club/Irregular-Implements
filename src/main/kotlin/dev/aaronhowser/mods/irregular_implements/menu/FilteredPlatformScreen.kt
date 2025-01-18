package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class FilteredPlatformScreen(
    menu: FilteredPlatformMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<FilteredPlatformMenu>(menu, playerInventory, title) {

    private val background = ScreenTextures.Backgrounds.FilteredSuperLubricantPlatform

    override fun init() {
        this.imageWidth = background.width
        this.imageHeight = background.height

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        this.background.render(
            guiGraphics,
            (this.width - this.imageWidth) / 2,
            (this.height - this.imageHeight) / 2
        )
    }

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        // Do nothing
    }
}