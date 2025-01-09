package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.base.ToggleSpriteButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class IronDropperScreen(
    menu: IronDropperMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<IronDropperMenu>(menu, playerInventory, title) {

    companion object {
        private val texture = ResourceLocation.withDefaultNamespace("textures/gui/container/dispenser.png")
    }

    private val rightPos: Int
        get() = this.leftPos + ScreenTextures.Background.ChatDetector.WIDTH
    private val bottomPos: Int
        get() = this.topPos + ScreenTextures.Background.ChatDetector.HEIGHT

    private lateinit var shootModeButton: ToggleSpriteButton
    private lateinit var toggleEffectButton: ToggleSpriteButton
    private lateinit var delayButton: ToggleSpriteButton
    private lateinit var redstoneModeButton: ToggleSpriteButton

    override fun init() {
        super.init()
        this.titleLabelX = (this.imageWidth - font.width(this.title)) / 2

        this.shootModeButton = ToggleSpriteButton(
            width = 20,
            height = 20,
            spriteWidth = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM_WIDTH,
            spriteHeight = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM_HEIGHT,
            spriteOn = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM,
            spriteOff = ScreenTextures.Sprite.IronDropper.DIRECTION_FORWARD,
            messageOn = Component.literal("Random"),
            messageOff = Component.literal("Forward"),
            currentStateGetter = { this.menu.shouldShootStraight },
            onPress = { this.menu.toggleShootStraight() },
            font = this.font
        )

        this.shootModeButton.setPosition(
            this.rightPos - this.shootModeButton.width - 5,
            this.topPos + 5
        )

        this.addRenderableWidget(this.shootModeButton)
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        this.renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val i = (this.width - this.imageWidth) / 2
        val j = (this.height - this.imageHeight) / 2
        guiGraphics.blit(texture, i, j, 0, 0, this.imageWidth, this.imageHeight)
    }

}