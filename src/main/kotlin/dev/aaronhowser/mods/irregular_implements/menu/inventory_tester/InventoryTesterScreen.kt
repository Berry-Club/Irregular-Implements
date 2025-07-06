package dev.aaronhowser.mods.irregular_implements.menu.inventory_tester

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.MultiStageSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class InventoryTesterScreen(
	menu: InventoryTesterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<InventoryTesterMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.InventoryTester

	private lateinit var invertButton: MultiStageSpriteButton

	override fun baseInit() {
		this.titleLabelX = 35
		this.inventoryLabelX = 35
		this.inventoryLabelY = this.imageHeight - 94

		//TODO: Update placement and size
		this.invertButton = MultiStageSpriteButton.Builder(this.font)
			.size(20)
			.location(
				x = this.leftPos + 90,
				y = this.topPos + 18
			)
			.addStage(
				message = ModTooltipLang.INVENTORY_TESTER_UNINVERTED.toComponent(),
				sprite = ScreenTextures.Sprite.InventoryTester.Uninverted
			)
			.addStage(
				message = ModTooltipLang.INVENTORY_TESTER_INVERTED.toComponent(),
				sprite = ScreenTextures.Sprite.InventoryTester.Inverted
			)
			.currentStageGetter(
				currentStageGetter = { if (this.menu.isInverted) 1 else 0 }
			)
			.onPress(
				onPress = { ModPacketHandler.messageServer(ClientClickedMenuButton(InventoryTesterMenu.TOGGLE_INVERSION_BUTTON_ID)) }
			)
			.build()

		this.addRenderableWidget(this.invertButton)
	}

}