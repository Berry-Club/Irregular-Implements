package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class VoidStoneScreen(
    menu: VoidStoneMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<VoidStoneMenu>(menu, playerInventory, title) {

    override fun init() {
        this.imageWidth = ScreenTextures.Background.VoidStone.WIDTH
        this.imageHeight = ScreenTextures.Background.VoidStone.HEIGHT

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        this.renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val i = (this.width - this.imageWidth) / 2
        val j = (this.height - this.imageHeight) / 2

        guiGraphics.blit(
            ScreenTextures.Background.VoidStone.BACKGROUND,
            i,
            j,
            0,
            0,
            ScreenTextures.Background.VoidStone.CANVAS_SIZE,
            ScreenTextures.Background.VoidStone.CANVAS_SIZE,
        )
    }

}