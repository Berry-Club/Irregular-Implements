package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.ImprovedSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStageSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class BlockDestabilizerScreen(
    menu: BlockDestabilizerMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<BlockDestabilizerMenu>(menu, playerInventory, title) {

    private lateinit var toggleLazyButton: MultiStageSpriteButton
    private lateinit var showLazyShapeButton: ImprovedSpriteButton
    private lateinit var forgetLazyShapeButton: ImprovedSpriteButton

    private val background = ScreenTextures.Background.BlockDestabilizer

    override fun init() {

        this.imageWidth = background.width
        this.imageHeight = background.height

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.toggleLazyButton = MultiStageSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.LAZY.toComponent(),
                menuSprite = ScreenTextures.Sprite.BlockDestabilizer.Lazy
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.NOT_LAZY.toComponent(),
                menuSprite = ScreenTextures.Sprite.BlockDestabilizer.NotLazy
            )
            .size(
                width = 20,
                height = 20
            )
            .currentStageGetter(
                currentStageGetter = { if (this.menu.isLazy) 0 else 1 }
            )
            .onPress(
                onPress = {
                    ModPacketHandler.messageServer(
                        ClientClickedMenuButton(
                            BlockDestabilizerMenu.TOGGLE_LAZY_BUTTON_ID
                        )
                    )
                }
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
            menuSprite = ScreenTextures.Sprite.BlockDestabilizer.ShowLazyShape,
            onPress = {
                ModPacketHandler.messageServer(
                    ClientClickedMenuButton(
                        BlockDestabilizerMenu.SHOW_LAZY_SHAPE_BUTTON_ID
                    )
                )
            },
            message = ModLanguageProvider.Tooltips.SHOW_LAZY_SHAPE.toComponent(),
            font = this.font
        )

        this.forgetLazyShapeButton = ImprovedSpriteButton(
            x = this.leftPos + 58,
            y = this.topPos + 7,
            width = 20,
            height = 20,
            menuSprite = ScreenTextures.Sprite.BlockDestabilizer.ResetLazyShape,
            onPress = {
                ModPacketHandler.messageServer(
                    ClientClickedMenuButton(
                        BlockDestabilizerMenu.RESET_LAZY_SHAPE_BUTTON_ID
                    )
                )
            },
            message = ModLanguageProvider.Tooltips.FORGET_LAZY_SHAPE.toComponent(),
            font = this.font
        )

        this.addRenderableWidget(this.toggleLazyButton)
        this.addRenderableWidget(this.showLazyShapeButton)
        this.addRenderableWidget(this.forgetLazyShapeButton)
    }

    // Rendering

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        this.background.render(
            guiGraphics,
            this.leftPos,
            this.topPos
        )
    }

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        // Do nothing
    }

    // Behavior

    override fun isPauseScreen(): Boolean {
        return false
    }

}