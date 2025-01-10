package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStateSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedChatDetector
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientChatDetector
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.properties.Delegates

class ChatDetectorScreen(
    private val chatDetectorBlockEntity: ChatDetectorBlockEntity,
    title: Component = chatDetectorBlockEntity.blockState.block.name
) : Screen(title) {

    private var leftPos: Int by Delegates.notNull()
    private var topPos: Int by Delegates.notNull()

    private val rightPos: Int
        get() = this.leftPos + ScreenTextures.Background.ChatDetector.WIDTH
    private val bottomPos: Int
        get() = this.topPos + ScreenTextures.Background.ChatDetector.HEIGHT

    private lateinit var toggleMessagePassButton: MultiStateSpriteButton
    private lateinit var regexStringEditBox: EditBox

    override fun init() {
        this.leftPos = (this.width - ScreenTextures.Background.ChatDetector.WIDTH) / 2
        this.topPos = (this.height - ScreenTextures.Background.ChatDetector.HEIGHT) / 2

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
                currentStateGetter = { if (this.chatDetectorBlockEntity.stopsMessage) 0 else 1 }    // On means it stops messages      //TODO: Update this to use a menu for containerdata
            )
            .onPress(
                onPress = ::pressToggleMessagePassButton
            )
            .build()

        this.toggleMessagePassButton.setPosition(
            this.rightPos - this.toggleMessagePassButton.width - 5,
            this.topPos + 5
        )

        val width = this.rightPos - this.leftPos

        this.regexStringEditBox = EditBox(
            this.font,
            this.leftPos + 5,
            this.bottomPos - 5 - 20,
            width - 5 - 5,
            20,
            Component.literal(this.chatDetectorBlockEntity.regexString)
        )

        this.regexStringEditBox.setResponder(::setRegexString)
        this.regexStringEditBox.setMaxLength(10000)
        this.regexStringEditBox.setHint(ModLanguageProvider.Tooltips.MESSAGE_REGEX.toComponent())

        addRenderableWidget(this.toggleMessagePassButton)
        addRenderableWidget(this.regexStringEditBox)
    }

    // Rendering

    override fun renderMenuBackground(guiGraphics: GuiGraphics) {
        guiGraphics.blit(
            ScreenTextures.Background.ChatDetector.BACKGROUND,
            this.leftPos,
            this.topPos,
            0f,
            0f,
            ScreenTextures.Background.ChatDetector.WIDTH,
            ScreenTextures.Background.ChatDetector.HEIGHT,
            ScreenTextures.Background.ChatDetector.CANVAS_SIZE,
            ScreenTextures.Background.ChatDetector.CANVAS_SIZE
        )
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)

        guiGraphics.drawString(
            this.font,
            this.title,
            this.leftPos + 10,
            this.topPos + 10,
            0x403030,
            false
        )
    }

    // Behavior

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun tick() {
        if (this.chatDetectorBlockEntity.isRemoved) {
            onClose()
        }

        if (this.regexStringEditBox.value != UpdateClientChatDetector.regexString) {
            this.regexStringEditBox.value = UpdateClientChatDetector.regexString
        }

        if (this.chatDetectorBlockEntity.stopsMessage != UpdateClientChatDetector.stopsMessage) {
            this.chatDetectorBlockEntity.stopsMessage = UpdateClientChatDetector.stopsMessage
        }
    }

    override fun onClose() {
        UpdateClientChatDetector.unset()

        super.onClose()
    }

    private fun pressToggleMessagePassButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientChangedChatDetector(
                this.chatDetectorBlockEntity.blockPos,
                !this.chatDetectorBlockEntity.stopsMessage,
                regexStringEditBox.value
            )
        )
    }

    private var isChangingRegexString = false
    private fun setRegexString(string: String) {
        if (isChangingRegexString) return
        isChangingRegexString = true
        this.regexStringEditBox.value = string
        isChangingRegexString = false

        ModPacketHandler.messageServer(
            ClientChangedChatDetector(
                this.chatDetectorBlockEntity.blockPos,
                this.chatDetectorBlockEntity.stopsMessage,
                string
            )
        )
    }

}