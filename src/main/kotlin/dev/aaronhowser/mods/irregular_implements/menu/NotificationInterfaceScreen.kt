package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenWithStrings
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class NotificationInterfaceScreen(
    menu: NotificationInterfaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<NotificationInterfaceMenu>(menu, playerInventory, title), ScreenWithStrings {

    private lateinit var titleEditBox: EditBox
    private lateinit var descriptionEditBox: EditBox

    private val background = ScreenTextures.Backgrounds.NotificationInterface

    override fun init() {
        this.imageWidth = background.width
        this.imageHeight = background.height

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.inventoryLabelY = -1000
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        background.render(
            guiGraphics,
            this.leftPos,
            this.topPos
        )
    }

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false)
    }

    override fun receivedString(stringId: Int, string: String) {
        TODO("Not yet implemented")
    }
}