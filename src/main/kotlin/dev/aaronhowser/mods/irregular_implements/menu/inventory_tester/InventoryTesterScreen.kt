package dev.aaronhowser.mods.irregular_implements.menu.inventory_tester

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.components.MultiStageSpriteButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.packet.c2s.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class InventoryTesterScreen(
	menu: InventoryTesterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<InventoryTesterMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.INVENTORY_TESTER

	private lateinit var invertButton: MultiStageSpriteButton

	override fun baseInit() {
		titleLabelX = 35
		inventoryLabelX = 35
		inventoryLabelY = imageHeight - 94

		//TODO: Update placement and size
		invertButton = MultiStageSpriteButton.Builder(font)
			.size(20)
			.location(
				x = leftPos + 90,
				y = topPos + 18
			)
			.addStage(
				message = ModTooltipLang.UNINVERTED.toComponent(),
				sprite = ScreenTextures.Sprites.UNINVERTED
			)
			.addStage(
				message = ModTooltipLang.INVERTED.toComponent(),
				sprite = ScreenTextures.Sprites.INVERTED
			)
			.currentStageGetter(
				currentStageGetter = { if (menu.isInverted) 1 else 0 }
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(InventoryTesterMenu.TOGGLE_INVERSION_BUTTON_ID)
					packet.messageServer()
				}
			)
			.build()

		addRenderableWidget(invertButton)
	}

}