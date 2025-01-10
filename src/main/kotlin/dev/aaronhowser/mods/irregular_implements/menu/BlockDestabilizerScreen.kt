package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDestabilizerBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.ImprovedSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStateSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedBlockDestabilizerButton
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientBlockDestabilizer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.properties.Delegates

class BlockDestabilizerScreen(
    private val blockDestabilizerBlockEntity: BlockDestabilizerBlockEntity,
    title: Component = blockDestabilizerBlockEntity.blockState.block.name
) : Screen(title) {

    private var leftPos: Int by Delegates.notNull()
    private var topPos: Int by Delegates.notNull()

    private lateinit var toggleLazyButton: Button
    private lateinit var showLazyShapeButton: Button
    private lateinit var forgetLazyShapeButton: Button

    override fun init() {
        this.leftPos = (this.width - ScreenTextures.Background.BlockDestabilizer.WIDTH) / 2
        this.topPos = (this.height - ScreenTextures.Background.BlockDestabilizer.HEIGHT) / 2

        this.toggleLazyButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                sprite = ScreenTextures.Sprite.BlockDestabilizer.LAZY,
                message = ModLanguageProvider.Tooltips.LAZY.toComponent()
            )
            .addStage(
                sprite = ScreenTextures.Sprite.BlockDestabilizer.NOT_LAZY,
                message = ModLanguageProvider.Tooltips.NOT_LAZY.toComponent()
            )
            .size(
                width = 20,
                height = 20
            )
            .spriteDimensions(
                width = ScreenTextures.Sprite.BlockDestabilizer.LAZY_WIDTH,
                height = ScreenTextures.Sprite.BlockDestabilizer.LAZY_HEIGHT
            )
            .currentStateGetter(
                currentStateGetter = { if (this.blockDestabilizerBlockEntity.isLazy) 0 else 1 }     //TODO: Update this to use a menu for containerdata
            )
            .onPress(
                onPress = ::pressToggleLazyButton
            )
            .location(
                x = this.leftPos + 7,
                y = this.topPos + 7
            )
            .build()

        showLazyShapeButton = ImprovedSpriteButton(
            x = this.leftPos + 33,
            y = this.topPos + 7,
            width = 20,
            height = 20,
            spriteWidth = ScreenTextures.Sprite.BlockDestabilizer.SHOW_LAZY_SHAPE_WIDTH,
            spriteHeight = ScreenTextures.Sprite.BlockDestabilizer.SHOW_LAZY_SHAPE_HEIGHT,
            sprite = ScreenTextures.Sprite.BlockDestabilizer.SHOW_LAZY_SHAPE,
            onPress = ::pressShowLazyShapeButton,
            message = ModLanguageProvider.Tooltips.SHOW_LAZY_SHAPE.toComponent(),
            font = this.font
        )

        forgetLazyShapeButton = ImprovedSpriteButton(
            x = this.leftPos + 58,
            y = this.topPos + 7,
            width = 20,
            height = 20,
            spriteWidth = ScreenTextures.Sprite.BlockDestabilizer.RESET_LAZY_SHAPE_WIDTH,
            spriteHeight = ScreenTextures.Sprite.BlockDestabilizer.RESET_LAZY_SHAPE_HEIGHT,
            sprite = ScreenTextures.Sprite.BlockDestabilizer.RESET_LAZY_SHAPE,
            onPress = ::pressForgetLazyShapeButton,
            message = ModLanguageProvider.Tooltips.FORGET_LAZY_SHAPE.toComponent(),
            font = this.font
        )

        this.addRenderableWidget(toggleLazyButton)
        this.addRenderableWidget(showLazyShapeButton)
        this.addRenderableWidget(forgetLazyShapeButton)
    }

    // Rendering

    override fun renderMenuBackground(guiGraphics: GuiGraphics) {
        guiGraphics.blit(
            ScreenTextures.Background.BlockDestabilizer.BACKGROUND,
            this.leftPos,
            this.topPos,
            0f,
            0f,
            ScreenTextures.Background.BlockDestabilizer.WIDTH,
            ScreenTextures.Background.BlockDestabilizer.HEIGHT,
            ScreenTextures.Background.BlockDestabilizer.CANVAS_SIZE,
            ScreenTextures.Background.BlockDestabilizer.CANVAS_SIZE
        )
    }

    // Behavior

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun tick() {
        if (this.blockDestabilizerBlockEntity.isRemoved) {
            onClose()
        }

        if (this.blockDestabilizerBlockEntity.isLazy != UpdateClientBlockDestabilizer.isLazy) {
            this.blockDestabilizerBlockEntity.isLazy = UpdateClientBlockDestabilizer.isLazy
        }
    }

    override fun onClose() {
        UpdateClientBlockDestabilizer.unset()

        super.onClose()
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

    private fun pressForgetLazyShapeButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientClickedBlockDestabilizerButton(
                blockDestabilizerBlockEntity.blockPos,
                ClientClickedBlockDestabilizerButton.Button.RESET_LAZY_SHAPE
            )
        )
    }

}