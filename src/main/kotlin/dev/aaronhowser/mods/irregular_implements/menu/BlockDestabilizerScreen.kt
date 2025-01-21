package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ImprovedSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStageSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class BlockDestabilizerScreen(
    menu: BlockDestabilizerMenu,
    playerInventory: Inventory,
    title: Component
) : BaseScreen<BlockDestabilizerMenu>(menu, playerInventory, title) {

    private lateinit var toggleLazyButton: MultiStageSpriteButton
    private lateinit var showLazyShapeButton: ImprovedSpriteButton
    private lateinit var forgetLazyShapeButton: ImprovedSpriteButton

    override val background = ScreenTextures.Background.BlockDestabilizer

    override fun addWidgets() {
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

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        // Do nothing
    }

}