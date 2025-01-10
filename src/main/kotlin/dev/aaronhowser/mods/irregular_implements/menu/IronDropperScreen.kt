package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStateSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedIronDropperButton
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

    private lateinit var shootModeButton: MultiStateSpriteButton
    private lateinit var toggleEffectButton: MultiStateSpriteButton
    private lateinit var delayButton: MultiStateSpriteButton
    private lateinit var redstoneModeButton: MultiStateSpriteButton

    override fun init() {
        super.init()
        this.titleLabelX = (this.imageWidth - font.width(this.title)) / 2

        this.shootModeButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                sprite = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM,
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_RANDOM_VELOCITY.toComponent()
            )
            .addStage(
                sprite = ScreenTextures.Sprite.IronDropper.DIRECTION_FORWARD,
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_EXACT_VELOCITY.toComponent()
            )
            .size(
                width = 20,
                height = 20
            )
            .spriteDimensions(
                width = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM_WIDTH,
                height = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM_HEIGHT
            )
            .currentStateGetter(
                currentStateGetter = { if (this.menu.shouldShootStraight) 1 else 0 }
            )
            .onPress(
                onPress = { ModPacketHandler.messageServer(ClientClickedIronDropperButton(IronDropperMenu.SHOOT_MODE_BUTTON_ID)) }
            )
            .location(
                x = this.rightPos - 25,
                y = this.topPos + 5
            )
            .build()

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