package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDestabilizerBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ToggleSpriteButton
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedBlockDestabilizerButton
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.SpriteIconButton
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.properties.Delegates

class BlockDestabilizerScreen(
    private val blockDestabilizerBlockEntity: BlockDestabilizerBlockEntity,
    title: Component = blockDestabilizerBlockEntity.blockState.block.name
) : Screen(title) {

    private var leftPos: Int by Delegates.notNull()
    private var topPos: Int by Delegates.notNull()

    private val rightPos: Int
        get() = this.leftPos + ScreenTextures.Background.BlockDestabilizer.WIDTH
    private val bottomPos: Int
        get() = this.topPos + ScreenTextures.Background.BlockDestabilizer.HEIGHT

    private lateinit var toggleLazyButton: Button
    private lateinit var showLazyShapeButton: Button
    private lateinit var resetLazyShapeButton: Button

    override fun init() {
        this.leftPos = (this.width - ScreenTextures.Background.ChatDetector.WIDTH) / 2
        this.topPos = (this.height - ScreenTextures.Background.ChatDetector.HEIGHT) / 2

        toggleLazyButton = ToggleSpriteButton(
            width = 20,
            height = 20,
            spriteWidth = ScreenTextures.Sprite.BlockDestabilizer.LAZY_WIDTH,
            spriteHeight = ScreenTextures.Sprite.BlockDestabilizer.LAZY_HEIGHT,
            spriteOn = ScreenTextures.Sprite.BlockDestabilizer.LAZY,
            spriteOff = ScreenTextures.Sprite.BlockDestabilizer.NOT_LAZY,
            messageOn = Component.literal("Lazy"),
            messageOff = Component.literal("Not Lazy"),
            currentState = this.blockDestabilizerBlockEntity.isLazy,
            onPress = ::pressToggleLazyButton,
            font = this.font
        )

        showLazyShapeButton = SpriteIconButton.builder(
            Component.empty(),
            ::pressShowLazyShapeButton,
        )
            .bounds(this.rightPos - 40, this.topPos + 5, 20, 20)
            .build()

        resetLazyShapeButton = SpriteIconButton.builder(
            Component.empty(),
            ::pressResetLazyShapeButton,
        )
            .bounds(this.rightPos - 20, this.topPos + 5, 20, 20)
            .build()
    }

    private fun pressToggleLazyButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientClickedBlockDestabilizerButton(
                blockDestabilizerBlockEntity.blockPos,
                ClientClickedBlockDestabilizerButton.Button.TOGGLE_LAZY
            )
        )
    }

    private fun pressShowLazyShapeButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientClickedBlockDestabilizerButton(
                blockDestabilizerBlockEntity.blockPos,
                ClientClickedBlockDestabilizerButton.Button.SHOW_LAZY_SHAPE
            )
        )
    }

    private fun pressResetLazyShapeButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientClickedBlockDestabilizerButton(
                blockDestabilizerBlockEntity.blockPos,
                ClientClickedBlockDestabilizerButton.Button.RESET_LAZY_SHAPE
            )
        )
    }

}