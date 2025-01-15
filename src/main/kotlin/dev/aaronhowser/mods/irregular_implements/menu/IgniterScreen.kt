package dev.aaronhowser.mods.irregular_implements.menu

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
        TODO("Not yet implemented")
    }

}