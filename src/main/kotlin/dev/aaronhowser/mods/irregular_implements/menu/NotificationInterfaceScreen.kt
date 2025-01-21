package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
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

    private val background = ScreenTextures.Background.NotificationInterface

    override fun init() {
        this.imageWidth = background.width
        this.imageHeight = background.height

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.inventoryLabelY = -1000

        this.titleEditBox = EditBox(
            this.font,
            this.leftPos + 34,
            this.topPos + 15,
            130,
            15,
            Component.empty()
        )

        this.titleEditBox.setResponder(::setTitle)

        this.descriptionEditBox = EditBox(
            this.font,
            this.leftPos + 34,
            this.topPos + 18 + 22,
            130,
            15,
            Component.empty()
        )

        this.descriptionEditBox.setResponder(::setDescription)

        this.addRenderableWidget(this.titleEditBox)
        this.addRenderableWidget(this.descriptionEditBox)
    }

    private fun setTitle(title: String) {
        if (this.menu.setTitle(title)) {
            ModPacketHandler.messageServer(
                ClientChangedMenuString(
                    NotificationInterfaceMenu.TITLE_STRING_ID,
                    title
                )
            )
        }
    }

    private fun setDescription(description: String) {
        if (this.menu.setDescription(description)) {
            ModPacketHandler.messageServer(
                ClientChangedMenuString(
                    NotificationInterfaceMenu.DESCRIPTION_STRING_ID,
                    description
                )
            )
        }
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
        when (stringId) {
            NotificationInterfaceMenu.TITLE_STRING_ID -> this.titleEditBox.value = string
            NotificationInterfaceMenu.DESCRIPTION_STRING_ID -> this.descriptionEditBox.value = string
        }
    }
}