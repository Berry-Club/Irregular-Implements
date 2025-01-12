package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.ImprovedSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStateSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class BlockDestabilizerScreen(
    menu: BlockDestabilizerMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<BlockDestabilizerMenu>(menu, playerInventory, title) {

    private lateinit var toggleLazyButton: MultiStateSpriteButton
    private lateinit var showLazyShapeButton: ImprovedSpriteButton
    private lateinit var forgetLazyShapeButton: ImprovedSpriteButton

    override fun init() {
        this.imageWidth = ScreenTextures.Background.BlockDestabilizer.WIDTH
        this.imageHeight = ScreenTextures.Background.BlockDestabilizer.HEIGHT

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.toggleLazyButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.LAZY.toComponent(),
                sprite = ScreenTextures.Sprite.BlockDestabilizer.LAZY,
                spriteWidth = ScreenTextures.Sprite.BlockDestabilizer.LAZY_WIDTH,
                spriteHeight = ScreenTextures.Sprite.BlockDestabilizer.LAZY_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.NOT_LAZY.toComponent(),
                sprite = ScreenTextures.Sprite.BlockDestabilizer.NOT_LAZY,
                spriteWidth = ScreenTextures.Sprite.BlockDestabilizer.NOT_LAZY_WIDTH,
                spriteHeight = ScreenTextures.Sprite.BlockDestabilizer.NOT_LAZY_HEIGHT
            )
            .size(
                width = 20,
                height = 20
            )
            .currentStateGetter(
                currentStateGetter = { if (this.menu.isLazy) 0 else 1 }
            )
            .onPress(
                onPress = ::pressToggleLazyButton
            )
            .location(
                x = this.leftPos + 7,
                y = this.topPos + 7
            )
            .build()

        this.showLazyShapeButton = ImprovedSpriteButton(
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

        this.forgetLazyShapeButton = ImprovedSpriteButton(
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

        this.addRenderableWidget(this.toggleLazyButton)
        this.addRenderableWidget(this.showLazyShapeButton)
        this.addRenderableWidget(this.forgetLazyShapeButton)
    }

    // Rendering

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
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

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        // Do nothing
    }

    // Behavior

    override fun isPauseScreen(): Boolean {
        return false
    }

    private fun pressToggleLazyButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientClickedMenuButton(
                BlockDestabilizerMenu.TOGGLE_LAZY_BUTTON_ID
            )
        )
    }

    private fun pressShowLazyShapeButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientClickedMenuButton(
                BlockDestabilizerMenu.SHOW_LAZY_SHAPE_BUTTON_ID
            )
        )
    }

    private fun pressForgetLazyShapeButton(button: Button) {
        ModPacketHandler.messageServer(
            ClientClickedMenuButton(
                BlockDestabilizerMenu.RESET_LAZY_SHAPE_BUTTON_ID
            )
        )
    }

}