package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDestabilizerBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ToggleSpriteButton
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedBlockDestabilizerButton
import net.minecraft.client.gui.GuiGraphics
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
        this.leftPos = (this.width - ScreenTextures.Background.BlockDestabilizer.WIDTH) / 2
        this.topPos = (this.height - ScreenTextures.Background.BlockDestabilizer.HEIGHT) / 2

        toggleLazyButton = ToggleSpriteButton(
            x = this.leftPos + 5,
            y = this.topPos + 5,
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
            true
        )
            .size(20, 20)
            .sprite(
                ScreenTextures.Sprite.BlockDestabilizer.FUZZY,
                ScreenTextures.Sprite.BlockDestabilizer.FUZZY_WIDTH,
                ScreenTextures.Sprite.BlockDestabilizer.FUZZY_HEIGHT
            )
            .build()

        showLazyShapeButton.x = this.rightPos - 40
        showLazyShapeButton.y = this.topPos + 5

        resetLazyShapeButton = SpriteIconButton.builder(
            Component.empty(),
            ::pressResetLazyShapeButton,
            true
        )
            .size(20, 20)
            .sprite(
                ScreenTextures.Sprite.BlockDestabilizer.RESET_LAZY_SHAPE,
                ScreenTextures.Sprite.BlockDestabilizer.RESET_LAZY_SHAPE_WIDTH,
                ScreenTextures.Sprite.BlockDestabilizer.RESET_LAZY_SHAPE_HEIGHT
            )
            .build()

        resetLazyShapeButton.x = this.rightPos - 20
        resetLazyShapeButton.y = this.topPos + 5

        this.addRenderableWidget(toggleLazyButton)
        this.addRenderableWidget(showLazyShapeButton)
        this.addRenderableWidget(resetLazyShapeButton)
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

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)

        if (showLazyShapeButton.isHovered) {
            guiGraphics.renderComponentTooltip(
                this.font,
                listOf(
                    Component.literal("Show Lazy Shape")
                ),
                mouseX,
                mouseY
            )
        } else if (resetLazyShapeButton.isHovered) {
            guiGraphics.renderComponentTooltip(
                this.font,
                listOf(
                    Component.literal("Reset Lazy Shape")
                ),
                mouseX,
                mouseY
            )
        }

    }

    // Behavior

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun tick() {
        if (this.blockDestabilizerBlockEntity.isRemoved) {
            onClose()
        }
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