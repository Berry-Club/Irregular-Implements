package dev.aaronhowser.mods.irregular_implements.menu

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class IronDropperScreen(
    menu: IronDropperMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<IronDropperMenu>(menu, playerInventory, title) {

    companion object {
        private val texture = ResourceLocation.withDefaultNamespace("textures/gui/container/dispenser.png")
    }

    override fun init() {
        super.init()
        this.titleLabelX = (this.imageWidth - font.width(this.title)) / 2
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        this.renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val i = (this.width - this.imageWidth) / 2
        val j = (this.height - this.imageHeight) / 2
        guiGraphics.blit(texture, i, j, 0, 0, this.imageWidth, this.imageHeight)
    }

}