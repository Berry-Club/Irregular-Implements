package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStageSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ChatDetectorScreen(
    menu: ChatDetectorMenu,
    playerInventory: Inventory,
    title: Component
) : BaseScreen<ChatDetectorMenu>(menu, playerInventory, title), ScreenWithStrings {

    private lateinit var toggleMessagePassButton: MultiStageSpriteButton
    private lateinit var regexStringEditBox: EditBox

    override val background = ScreenTextures.Background.ChatDetector

    override fun baseInit() {
        this.titleLabelX = 10
        this.titleLabelY = 10

        this.toggleMessagePassButton = MultiStageSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.STOPS_MESSAGE.toComponent(),
                menuSprite = ScreenTextures.Sprite.ChatDetector.MessageStop
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.DOESNT_STOP_MESSAGE.toComponent(),
                menuSprite = ScreenTextures.Sprite.ChatDetector.MessageContinue
            )
            .size(
                width = 20,
                height = 20
            )
            .currentStageGetter(
                currentStageGetter = { if (this.menu.shouldMessageStop) 0 else 1 }    // 1 means it stops messages
            )
            .onPress(
                onPress = {
                    ModPacketHandler.messageServer(
                        ClientClickedMenuButton(
                            ChatDetectorMenu.TOGGLE_MESSAGE_PASS_BUTTON_ID
                        )
                    )
                }
            )
            .build()

        this.toggleMessagePassButton.setPosition(
            this.rightPos - this.toggleMessagePassButton.width - 5,
            this.topPos + 5
        )

        val screenWidth = this.rightPos - this.leftPos

        this.regexStringEditBox = EditBox(
            this.font,
            this.leftPos + 5,
            this.bottomPos - 5 - 20,
            screenWidth - 5 - 5,
            20,
            ModLanguageProvider.Tooltips.MESSAGE_REGEX.toComponent()
        )

        this.regexStringEditBox.setCanLoseFocus(false)
        this.regexStringEditBox.setTextColor(-1)
        this.regexStringEditBox.setTextColorUneditable(-1)
        this.regexStringEditBox.setResponder(::setRegexString)
        this.regexStringEditBox.setMaxLength(10000)

        addRenderableWidget(this.toggleMessagePassButton)
        addRenderableWidget(this.regexStringEditBox)
    }

    // Rendering

    override var showInventoryLabel: Boolean = false

    // Behavior

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == 256) {
            this.minecraft?.player?.closeContainer()
        }

        return if (!this.regexStringEditBox.keyPressed(keyCode, scanCode, modifiers) && !this.regexStringEditBox.canConsumeInput()) {
            super.keyPressed(keyCode, scanCode, modifiers)
        } else {
            true
        }
    }

    override fun resize(minecraft: Minecraft, width: Int, height: Int) {
        val currentRegexString = this.regexStringEditBox.value
        super.resize(minecraft, width, height)
        this.regexStringEditBox.value = currentRegexString
    }

    private fun setRegexString(string: String) {
        if (this.menu.setRegex(string)) {
            ModPacketHandler.messageServer(
                ClientChangedMenuString(
                    ChatDetectorMenu.REGEX_STRING_ID,
                    string
                )
            )
        }
    }

    override fun receivedString(stringId: Int, string: String) {
        if (stringId == ChatDetectorMenu.REGEX_STRING_ID) {
            this.regexStringEditBox.value = string
        }
    }

}