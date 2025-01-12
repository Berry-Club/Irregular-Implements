package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStateSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedChatDetectorString
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class GlobalChatDetectorScreen(
    menu: GlobalChatDetectorMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<GlobalChatDetectorMenu>(menu, playerInventory, title) {

    private val rightPos: Int
        get() = this.leftPos + this.imageWidth
    private val bottomPos: Int
        get() = this.topPos + this.imageHeight

    private lateinit var toggleMessagePassButton: MultiStateSpriteButton
    lateinit var regexStringEditBox: EditBox
        private set

    override fun init() {
        this.imageWidth = ScreenTextures.Background.GlobalChatDetector.WIDTH
        this.imageHeight = ScreenTextures.Background.GlobalChatDetector.HEIGHT

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.inventoryLabelY -= 8

        this.toggleMessagePassButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.STOPS_MESSAGE.toComponent(),
                sprite = ScreenTextures.Sprite.ChatDetector.MESSAGE_STOP,
                spriteWidth = ScreenTextures.Sprite.ChatDetector.WIDTH,
                spriteHeight = ScreenTextures.Sprite.ChatDetector.HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.DOESNT_STOP_MESSAGE.toComponent(),
                sprite = ScreenTextures.Sprite.ChatDetector.MESSAGE_CONTINUE,
                spriteWidth = ScreenTextures.Sprite.ChatDetector.WIDTH,
                spriteHeight = ScreenTextures.Sprite.ChatDetector.HEIGHT
            )
            .size(
                width = 20,
                height = 20
            )
            .currentStateGetter(
                currentStateGetter = { if (this.menu.shouldMessageStop) 0 else 1 }    // 1 means it stops messages
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
            this.topPos - 5 + 20,
            screenWidth - 5 - 5 - 20 - 5,
            20,
            ModLanguageProvider.Tooltips.MESSAGE_REGEX.toComponent()
        )

        this.regexStringEditBox.setCanLoseFocus(false)
        this.regexStringEditBox.setTextColor(-1)
        this.regexStringEditBox.setTextColorUneditable(-1)
//        this.regexStringEditBox.setResponder(::setRegexString)
        this.regexStringEditBox.setMaxLength(10000)

        addRenderableWidget(this.toggleMessagePassButton)
        addRenderableWidget(this.regexStringEditBox)

    }

    // Rendering

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        this.renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        guiGraphics.blit(
            ScreenTextures.Background.GlobalChatDetector.BACKGROUND,
            this.leftPos,
            this.topPos,
            0f,
            0f,
            ScreenTextures.Background.GlobalChatDetector.WIDTH,
            ScreenTextures.Background.GlobalChatDetector.HEIGHT,
            ScreenTextures.Background.GlobalChatDetector.CANVAS_SIZE,
            ScreenTextures.Background.GlobalChatDetector.CANVAS_SIZE
        )
    }

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

    override fun isPauseScreen(): Boolean {
        return false
    }

    private fun setRegexString(string: String) {
        if (this.menu.setRegex(string)) {
            ModPacketHandler.messageServer(
                ClientChangedChatDetectorString(
                    string
                )
            )
        }
    }

}