package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.ImprovedSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStateSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientBlockDestabilizer
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
        this.leftPos = (this.width - ScreenTextures.Background.BlockDestabilizer.WIDTH) / 2
        this.topPos = (this.height - ScreenTextures.Background.BlockDestabilizer.HEIGHT) / 2

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
                currentStateGetter = { 1 }     //TODO: Update this to use a menu for containerdata
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

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        TODO("Not yet implemented")
    }

//    override fun tick() {
//        if (this.blockDestabilizerBlockEntity.isRemoved) {
//            onClose()
//        }
//
//        if (this.blockDestabilizerBlockEntity.isLazy != UpdateClientBlockDestabilizer.isLazy) {
//            this.blockDestabilizerBlockEntity.isLazy = UpdateClientBlockDestabilizer.isLazy
//        }
//    }

    override fun onClose() {
        UpdateClientBlockDestabilizer.unset()

        super.onClose()
    }

    private fun pressToggleLazyButton(button: Button) {

    }

    private fun pressShowLazyShapeButton(button: Button) {
    }

    private fun pressForgetLazyShapeButton(button: Button) {
    }

}