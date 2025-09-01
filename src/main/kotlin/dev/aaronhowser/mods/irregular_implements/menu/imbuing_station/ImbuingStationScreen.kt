package dev.aaronhowser.mods.irregular_implements.menu.imbuing_station

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ImbuingStationScreen(
	menu: ImbuingStationMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<ImbuingStationMenu>(menu, playerInventory, title) {

	override val background = ScreenTextures.Background.IMBUING_STATION

	override fun baseInit() {
		this.titleLabelX = 4

		this.inventoryLabelY = this.imageHeight - 94
	}

}