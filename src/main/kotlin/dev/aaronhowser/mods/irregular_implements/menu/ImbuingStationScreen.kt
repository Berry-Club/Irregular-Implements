package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ImbuingStationScreen(
    menu: ImbuingStationMenu,
    playerInventory: Inventory,
    title: Component
) : BaseScreen<ImbuingStationMenu>(menu, playerInventory, title) {

    override val background = ScreenTextures.Background.ImbuingStation

    override fun baseInit() {
        this.titleLabelX -= 4
    }

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false)
    }

}