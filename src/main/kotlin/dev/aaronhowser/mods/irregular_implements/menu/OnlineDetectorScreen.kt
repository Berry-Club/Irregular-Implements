package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class OnlineDetectorScreen(
    menu: OnlineDetectorMenu,
    playerInventory: Inventory,
    title: Component
) : BaseScreen<OnlineDetectorMenu>(menu, playerInventory, title), ScreenWithStrings {

    private lateinit var usernameEditBox: EditBox

    override val background = ScreenTextures.Background.OnlineDetector

    override fun baseInit() {
        val editBoxHeight = 20

        this.usernameEditBox = EditBox(
            this.font,
            this.leftPos + 5,
            this.bottomPos - 5 - editBoxHeight,
            this.imageWidth - 5 - 5,
            editBoxHeight,
            Component.empty()
        )

        this.usernameEditBox.setCanLoseFocus(false)
        this.usernameEditBox.setTextColor(-1)
        this.usernameEditBox.setTextColorUneditable(-1)
        this.usernameEditBox.setResponder(::setUsername)

        this.addRenderableWidget(this.usernameEditBox)
    }

    override var showInventoryLabel: Boolean = false

    // Behavior

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == 256) {
            this.minecraft?.player?.closeContainer()
        }

        return if (!this.usernameEditBox.keyPressed(keyCode, scanCode, modifiers) && !this.usernameEditBox.canConsumeInput()) {
            super.keyPressed(keyCode, scanCode, modifiers)
        } else {
            true
        }
    }

    override fun resize(minecraft: Minecraft, width: Int, height: Int) {
        val currentRegexString = this.usernameEditBox.value
        super.resize(minecraft, width, height)
        this.usernameEditBox.value = currentRegexString
    }

    private fun setUsername(string: String) {
        if (this.menu.setUsername(string)) {
            ModPacketHandler.messageServer(
                ClientChangedMenuString(
                    OnlineDetectorMenu.USERNAME_STRING_ID,
                    string
                )
            )
        }
    }

    override fun receivedString(stringId: Int, regexString: String) {
        if (stringId == OnlineDetectorMenu.USERNAME_STRING_ID) {
            this.usernameEditBox.value = regexString
        }
    }

}