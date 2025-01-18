package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ItemFilterScreen(
    menu: ItemFilterMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ItemFilterMenu>(menu, playerInventory, title) {

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        ScreenTextures.Backgrounds.ItemFilter.render(
            guiGraphics,
            this.leftPos,
            this.topPos,
        )
    }

}