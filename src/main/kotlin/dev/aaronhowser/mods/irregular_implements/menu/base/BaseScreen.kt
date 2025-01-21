package dev.aaronhowser.mods.irregular_implements.menu.base

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu

abstract class BaseScreen<M : AbstractContainerMenu>(
    menu: M,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<M>(menu, playerInventory, title) {

    abstract val background: ScreenTextures.Background

    final override fun init() {
        this.imageWidth = background.width
        this.imageHeight = background.height

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        baseInit()
    }

    open fun addWidgets() {

    }

    open fun baseInit() {

    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        this.renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        this.background.render(
            guiGraphics = guiGraphics,
            leftPos = this.leftPos,
            topPos = this.topPos
        )
    }
}