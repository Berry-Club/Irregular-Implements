package dev.aaronhowser.mods.irregular_implements.menu.block_destabilizer

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ImprovedSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.MultiStageSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
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

	override fun baseInit() {
		this.toggleLazyButton = MultiStageSpriteButton.Builder(this.font)
			.addStage(
				message = ModTooltipLang.LAZY.toComponent(),
				sprite = ScreenTextures.Sprite.BlockDestabilizer.Lazy
			)
			.addStage(
				message = ModTooltipLang.NOT_LAZY.toComponent(),
				sprite = ScreenTextures.Sprite.BlockDestabilizer.NotLazy
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
					val packet = ClientClickedMenuButton(BlockDestabilizerMenu.TOGGLE_LAZY_BUTTON_ID)
					packet.messageServer()
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
				val packet = ClientClickedMenuButton(BlockDestabilizerMenu.SHOW_LAZY_SHAPE_BUTTON_ID)
				packet.messageServer()
			},
			message = ModTooltipLang.SHOW_LAZY_SHAPE.toComponent(),
			font = this.font
		)

		this.forgetLazyShapeButton = ImprovedSpriteButton(
			x = this.leftPos + 58,
			y = this.topPos + 7,
			width = 20,
			height = 20,
			menuSprite = ScreenTextures.Sprite.BlockDestabilizer.ResetLazyShape,
			onPress = {
				val packet = ClientClickedMenuButton(BlockDestabilizerMenu.RESET_LAZY_SHAPE_BUTTON_ID)
				packet.messageServer()
			},
			message = ModTooltipLang.FORGET_LAZY_SHAPE.toComponent(),
			font = this.font
		)

		this.addRenderableWidget(this.toggleLazyButton)
		this.addRenderableWidget(this.showLazyShapeButton)
		this.addRenderableWidget(this.forgetLazyShapeButton)
	}

	override var showInventoryLabel: Boolean = false
	override var showTitleLabel: Boolean = false

}