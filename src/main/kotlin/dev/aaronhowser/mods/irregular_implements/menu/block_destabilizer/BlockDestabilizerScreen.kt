package dev.aaronhowser.mods.irregular_implements.menu.block_destabilizer

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.components.ImprovedSpriteButton
import dev.aaronhowser.mods.aaron.menu.components.MultiStageSpriteButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.packet.c2s.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
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

	override val background: ScreenBackground = ScreenTextures.Backgrounds.BLOCK_DESTABILIZER

	override fun baseInit() {
		toggleLazyButton = MultiStageSpriteButton.Builder(font)
			.addStage(
				message = ModTooltipLang.LAZY.toComponent(),
				sprite = ScreenTextures.Sprites.BlockDestabilizer.LAZY
			)
			.addStage(
				message = ModTooltipLang.NOT_LAZY.toComponent(),
				sprite = ScreenTextures.Sprites.BlockDestabilizer.NOT_LAZY
			)
			.size(
				width = 20,
				height = 20
			)
			.currentStageGetter(
				currentStageGetter = { if (menu.isLazy) 0 else 1 }
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(BlockDestabilizerMenu.TOGGLE_LAZY_BUTTON_ID)
					packet.messageServer()
				}
			)
			.location(
				x = leftPos + 7,
				y = topPos + 7
			)
			.build()

		showLazyShapeButton = ImprovedSpriteButton(
			x = leftPos + 33,
			y = topPos + 7,
			width = 20,
			height = 20,
			menuSprite = ScreenTextures.Sprites.BlockDestabilizer.SHOW_LAZY_SHAPE,
			onPress = {
				val packet = ClientClickedMenuButton(BlockDestabilizerMenu.SHOW_LAZY_SHAPE_BUTTON_ID)
				packet.messageServer()
			},
			message = ModTooltipLang.SHOW_LAZY_SHAPE.toComponent(),
			font = font
		)

		forgetLazyShapeButton = ImprovedSpriteButton(
			x = leftPos + 58,
			y = topPos + 7,
			width = 20,
			height = 20,
			menuSprite = ScreenTextures.Sprites.BlockDestabilizer.RESET_LAZY_SHAPE,
			onPress = {
				val packet = ClientClickedMenuButton(BlockDestabilizerMenu.RESET_LAZY_SHAPE_BUTTON_ID)
				packet.messageServer()
			},
			message = ModTooltipLang.FORGET_LAZY_SHAPE.toComponent(),
			font = font
		)

		addRenderableWidget(toggleLazyButton)
		addRenderableWidget(showLazyShapeButton)
		addRenderableWidget(forgetLazyShapeButton)
	}

	override var showInventoryLabel: Boolean = false
	override var showTitleLabel: Boolean = false

}