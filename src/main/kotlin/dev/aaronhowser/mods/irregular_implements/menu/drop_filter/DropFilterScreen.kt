package dev.aaronhowser.mods.irregular_implements.menu.drop_filter

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.IIScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class DropFilterScreen(
	menu: DropFilterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<DropFilterMenu>(menu, playerInventory, title) {

	override val background = IIScreenTextures.Background.DropFilter

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94
	}

}