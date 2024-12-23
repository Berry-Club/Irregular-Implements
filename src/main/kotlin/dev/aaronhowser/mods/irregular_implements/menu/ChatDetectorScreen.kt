package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedChatDetector
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.TellClientChatDetectorChanged
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.SpriteIconButton
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.properties.Delegates

class ChatDetectorScreen(
    private val chatDetectorBlockEntity: ChatDetectorBlockEntity,
    title: Component = ModBlocks.CHAT_DETECTOR.get().name
) : Screen(title) {

    private var leftPos: Int by Delegates.notNull()
    private var topPos: Int by Delegates.notNull()

    private lateinit var toggleMessagePassButton: Button
    private lateinit var regexStringEditBox: EditBox

    override fun init() {
        this.leftPos = (this.width - ScreenTextures.Background.ChatDetector.WIDTH) / 2
        this.topPos = (this.height - ScreenTextures.Background.ChatDetector.HEIGHT) / 2

        toggleMessagePassButton = SpriteIconButton
            .builder(Component.literal("test"), ::pressToggleMessagePassButton, true)
            .sprite(
                ScreenTextures.Sprite.ChatDetector.MESSAGE_CONTINUE,
                ScreenTextures.Sprite.ChatDetector.CANVAS_SIZE,
                ScreenTextures.Sprite.ChatDetector.CANVAS_SIZE
            )
            .size(16, 16)
            .build()

        toggleMessagePassButton.x = this.leftPos + 10
        toggleMessagePassButton.y = this.topPos + 30

        regexStringEditBox = EditBox(
            this.font,
            this.leftPos,
            this.topPos - 20,
            72,
            14,
            Component.empty()
        )

        regexStringEditBox.setHint(Component.literal("the hint"))
        regexStringEditBox.setResponder(::setRegexString)

        addRenderableWidget(toggleMessagePassButton)
        addRenderableWidget(regexStringEditBox)
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

        if (this.regexStringEditBox.value != TellClientChatDetectorChanged.regexString) {
            this.regexStringEditBox.value = TellClientChatDetectorChanged.regexString
        }

    }

    private fun pressToggleMessagePassButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientChangedChatDetector(
                this.chatDetectorBlockEntity.blockPos,
                !this.chatDetectorBlockEntity.stopsMessage,
                this.chatDetectorBlockEntity.regexString
            )
        )
    }

    private fun setRegexString(string: String) {
        this.regexStringEditBox.value = string

        ModPacketHandler.messageServer(
            ClientChangedChatDetector(
                this.chatDetectorBlockEntity.blockPos,
                this.chatDetectorBlockEntity.stopsMessage,
                string
            )
        )
    }

}