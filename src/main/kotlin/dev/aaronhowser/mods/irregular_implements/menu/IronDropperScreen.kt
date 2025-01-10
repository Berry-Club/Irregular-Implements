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
import net.minecraft.world.entity.player.Inventory

class IronDropperScreen(
    menu: IronDropperMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<IronDropperMenu>(menu, playerInventory, title) {

    private val rightPos: Int
        get() = this.leftPos + this.imageWidth
    private val bottomPos: Int
        get() = this.topPos + this.imageHeight

    private lateinit var shootModeButton: MultiStateSpriteButton
    private lateinit var toggleEffectButton: MultiStateSpriteButton
    private lateinit var delayButton: MultiStateSpriteButton
    private lateinit var redstoneModeButton: MultiStateSpriteButton

    override fun init() {
        this.imageWidth = ScreenTextures.Background.IronDropper.WIDTH
        this.imageHeight = ScreenTextures.Background.IronDropper.HEIGHT

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        this.titleLabelX = (this.imageWidth - font.width(this.title)) / 2

        val buttonSize = 20
        val spaceBetween = 5

        val rightButtonX = this.rightPos - spaceBetween - buttonSize - 2
        val leftButtonX = rightButtonX - spaceBetween - buttonSize

        val topButtonY = this.topPos + spaceBetween + buttonSize / 2 + spaceBetween
        val bottomButtonY = topButtonY + buttonSize + spaceBetween

        this.redstoneModeButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_PULSE.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.REDSTONE_PULSE,
                spriteWidth = ScreenTextures.Sprite.IronDropper.REDSTONE_PULSE_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.REDSTONE_PULSE_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_CONTINUOUS_POWERED.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.REDSTONE_CONTINUOUS_POWERED,
                spriteWidth = ScreenTextures.Sprite.IronDropper.REDSTONE_CONTINUOUS_POWERED_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.REDSTONE_CONTINUOUS_POWERED_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_CONTINUOUS.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.REDSTONE_CONTINUOUS,
                spriteWidth = ScreenTextures.Sprite.IronDropper.REDSTONE_CONTINUOUS_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.REDSTONE_CONTINUOUS_HEIGHT
            )
            .size(
                size = buttonSize
            )
            .currentStateGetter(
                currentStateGetter = { this.menu.redstoneMode.ordinal }
            )
            .onPress(
                onPress = { ModPacketHandler.messageServer(ClientClickedIronDropperButton(IronDropperMenu.REDSTONE_BUTTON_ID)) }
            )
            .location(
                x = leftButtonX,
                y = topButtonY
            )
            .build()

        this.delayButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_NO_DELAY.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.PICKUP_ZERO,
                spriteWidth = ScreenTextures.Sprite.IronDropper.PICKUP_ZERO_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.PICKUP_ZERO_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_FIVE_DELAY.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.PICKUP_FIVE,
                spriteWidth = ScreenTextures.Sprite.IronDropper.PICKUP_FIVE_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.PICKUP_FIVE_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_TWENTY_DELAY.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.PICKUP_TWENTY,
                spriteWidth = ScreenTextures.Sprite.IronDropper.PICKUP_TWENTY_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.PICKUP_TWENTY_HEIGHT
            )
            .size(
                size = buttonSize
            )
            .currentStateGetter(
                currentStateGetter = { this.menu.pickupDelay.ordinal }
            )
            .onPress(
                onPress = { ModPacketHandler.messageServer(ClientClickedIronDropperButton(IronDropperMenu.DELAY_BUTTON_ID)) }
            )
            .location(
                x = rightButtonX,
                y = topButtonY
            )
            .build()

        this.shootModeButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_RANDOM_VELOCITY.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM,
                spriteWidth = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.DIRECTION_RANDOM_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_EXACT_VELOCITY.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.DIRECTION_FORWARD,
                spriteWidth = ScreenTextures.Sprite.IronDropper.DIRECTION_FORWARD_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.DIRECTION_FORWARD_HEIGHT
            )
            .size(
                size = buttonSize
            )
            .currentStateGetter(
                currentStateGetter = { if (this.menu.shouldShootStraight) 1 else 0 }
            )
            .onPress(
                onPress = { ModPacketHandler.messageServer(ClientClickedIronDropperButton(IronDropperMenu.SHOOT_MODE_BUTTON_ID)) }
            )
            .location(
                x = leftButtonX,
                y = bottomButtonY
            )
            .build()

        this.toggleEffectButton = MultiStateSpriteButton.Builder(this.font)
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_NO_EFFECTS.toComponent(),
                sprite = null
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_ONLY_PARTICLES.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.EFFECT_PARTICLE,
                spriteWidth = ScreenTextures.Sprite.IronDropper.EFFECT_PARTICLE_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.EFFECT_PARTICLE_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_ONLY_SOUND.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.EFFECT_SOUND,
                spriteWidth = ScreenTextures.Sprite.IronDropper.EFFECT_SOUND_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.EFFECT_SOUND_HEIGHT
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.IRON_DROPPER_BOTH_EFFECTS.toComponent(),
                sprite = ScreenTextures.Sprite.IronDropper.EFFECT_BOTH,
                spriteWidth = ScreenTextures.Sprite.IronDropper.EFFECT_BOTH_WIDTH,
                spriteHeight = ScreenTextures.Sprite.IronDropper.EFFECT_BOTH_HEIGHT
            )
            .size(
                size = buttonSize
            )
            .currentStateGetter(
                currentStateGetter = { this.menu.shouldHaveEffects.ordinal }
            )
            .onPress(
                onPress = { ModPacketHandler.messageServer(ClientClickedIronDropperButton(IronDropperMenu.EFFECTS_BUTTON_ID)) }
            )
            .location(
                x = rightButtonX,
                y = bottomButtonY
            )
            .build()

        this.addRenderableWidget(this.shootModeButton)
        this.addRenderableWidget(this.toggleEffectButton)
        this.addRenderableWidget(this.delayButton)
        this.addRenderableWidget(this.redstoneModeButton)
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        this.renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val i = (this.width - this.imageWidth) / 2
        val j = (this.height - this.imageHeight) / 2

        guiGraphics.blit(
            ScreenTextures.Background.IronDropper.BACKGROUND,
            i,
            j,
            0,
            0,
            ScreenTextures.Background.IronDropper.CANVAS_SIZE,
            ScreenTextures.Background.IronDropper.CANVAS_SIZE,
        )
    }

}