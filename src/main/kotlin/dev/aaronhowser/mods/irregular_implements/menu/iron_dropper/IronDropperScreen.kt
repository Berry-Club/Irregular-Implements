package dev.aaronhowser.mods.irregular_implements.menu.iron_dropper

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.IIScreenTextures
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

	override val background = IIScreenTextures.Background.IronDropper

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
				sprite = IIScreenTextures.Sprite.IronDropper.RedstonePulse
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_CONTINUOUS_POWERED.toComponent(),
				sprite = IIScreenTextures.Sprite.IronDropper.RedstoneContinuousPowered
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_CONTINUOUS.toComponent(),
				sprite = IIScreenTextures.Sprite.IronDropper.RedstoneContinuous
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
				sprite = IIScreenTextures.Sprite.IronDropper.PickupZero
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_FIVE_DELAY.toComponent(),
				sprite = IIScreenTextures.Sprite.IronDropper.PickupFive
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_TWENTY_DELAY.toComponent(),
				sprite = IIScreenTextures.Sprite.IronDropper.PickupTwenty
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
				sprite = IIScreenTextures.Sprite.IronDropper.DirectionRandom
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_EXACT_VELOCITY.toComponent(),
				sprite = IIScreenTextures.Sprite.IronDropper.DirectionForward
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
				sprite = IIScreenTextures.Sprite.IronDropper.EffectParticle
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_ONLY_SOUND.toComponent(),
				sprite = IIScreenTextures.Sprite.IronDropper.EffectSound
			)
			.addStage(
				message = ModTooltipLang.IRON_DROPPER_BOTH_EFFECTS.toComponent(),
				sprite = IIScreenTextures.Sprite.IronDropper.EffectBoth
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