package dev.aaronhowser.mods.irregular_implements.menu.iron_dropper

import dev.aaronhowser.mods.aaron.menu.components.MultiStageSpriteButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class IronDropperScreen(
	menu: IronDropperMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<IronDropperMenu>(menu, playerInventory, title) {

	private lateinit var shootModeButton: MultiStageSpriteButton
	private lateinit var toggleEffectButton: MultiStageSpriteButton
	private lateinit var delayButton: MultiStageSpriteButton
	private lateinit var redstoneModeButton: MultiStageSpriteButton

	override val background: ScreenBackground = ScreenTextures.Backgrounds.ironDropper

	override fun baseInit() {
		this.titleLabelX = (this.imageWidth - font.width(this.title)) / 2

		val buttonSize = 20
		val spaceBetween = 5

		val rightButtonX = this.rightPos - spaceBetween - buttonSize - 2
		val leftButtonX = rightButtonX - spaceBetween - buttonSize

		val topButtonY = this.topPos + spaceBetween + buttonSize / 2 + spaceBetween
		val bottomButtonY = topButtonY + buttonSize + spaceBetween

		this.redstoneModeButton = MultiStageSpriteButton.Builder(this.font)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_PULSE.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.RedstonePulse
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_CONTINUOUS_POWERED.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.RedstoneContinuousPowered
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_CONTINUOUS.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.RedstoneContinuous
			)
			.size(
				size = buttonSize
			)
			.currentStageGetter(
				currentStageGetter = { this.menu.redstoneMode.ordinal }
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(IronDropperMenu.REDSTONE_BUTTON_ID)
					packet.messageServer()
				}
			)
			.location(
				x = leftButtonX,
				y = topButtonY
			)
			.build()

		this.delayButton = MultiStageSpriteButton.Builder(this.font)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_NO_DELAY.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.PickupZero
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_FIVE_DELAY.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.PickupFive
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_TWENTY_DELAY.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.PickupTwenty
			)
			.size(
				size = buttonSize
			)
			.currentStageGetter(
				currentStageGetter = { this.menu.pickupDelay.ordinal }
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(IronDropperMenu.DELAY_BUTTON_ID)
					packet.messageServer()
				}
			)
			.location(
				x = rightButtonX,
				y = topButtonY
			)
			.build()

		this.shootModeButton = MultiStageSpriteButton.Builder(this.font)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_RANDOM_VELOCITY.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.DirectionRandom
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_EXACT_VELOCITY.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.DirectionForward
			)
			.size(
				size = buttonSize
			)
			.currentStageGetter(
				currentStageGetter = { if (this.menu.shouldShootStraight) 1 else 0 }
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(IronDropperMenu.SHOOT_MODE_BUTTON_ID)
					packet.messageServer()
				}
			)
			.location(
				x = leftButtonX,
				y = bottomButtonY
			)
			.build()

		this.toggleEffectButton = MultiStageSpriteButton.Builder(this.font)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_NO_EFFECTS.toComponent(),
				sprite = null
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_ONLY_PARTICLES.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.EffectParticle
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_ONLY_SOUND.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.EffectSound
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_BOTH_EFFECTS.toComponent(),
				sprite = ScreenTextures.Sprites.IronDropper.EffectBoth
			)
			.size(
				size = buttonSize
			)
			.currentStageGetter(
				currentStageGetter = { this.menu.shouldHaveEffects.ordinal }
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(IronDropperMenu.EFFECTS_BUTTON_ID)
					packet.messageServer()
				}
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

}