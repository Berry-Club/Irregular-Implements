package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ChangingTextButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class IgniterScreen(
    menu: IgniterMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<IgniterMenu>(menu, playerInventory, title) {

    private val rightPos: Int
        get() = this.leftPos + this.imageWidth
    private val bottomPos: Int
        get() = this.topPos + this.imageHeight

    private lateinit var changeModeButton: ChangingTextButton

    private val background = ScreenTextures.Backgrounds.Igniter

    override fun init() {
        this.imageWidth = background.width
        this.imageHeight = background.height

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.changeModeButton = ChangingTextButton(
            x = this.leftPos + 5,
            y = this.topPos + 5,
            width = this.imageWidth - 10,
            height = this.imageHeight - 10,
            messageGetter = { this.menu.mode.nameComponent },   //FIXME: For some reason this won't sync to client, it gets stuck on the first value
            onPress = { ModPacketHandler.messageServer(ClientClickedMenuButton(IgniterMenu.CYCLE_MODE_BUTTON_ID)) }
        )

        this.addRenderableWidget(this.changeModeButton)
    }

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        // Do nothing
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val i = (this.width - this.imageWidth) / 2
        val j = (this.height - this.imageHeight) / 2

        this.background.render(
            guiGraphics,
            i,
            j
        )
    }

}