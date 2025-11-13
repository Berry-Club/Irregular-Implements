package dev.aaronhowser.mods.irregular_implements.menu.drop_filter

import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class DropFilterScreen(
	menu: DropFilterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<DropFilterMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.DROP_FILTER

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94
	}

}