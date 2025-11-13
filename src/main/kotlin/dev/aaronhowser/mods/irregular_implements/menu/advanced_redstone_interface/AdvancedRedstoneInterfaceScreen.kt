package dev.aaronhowser.mods.irregular_implements.menu.advanced_redstone_interface

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class AdvancedRedstoneInterfaceScreen(
	menu: AdvancedRedstoneInterfaceMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<AdvancedRedstoneInterfaceMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.AdvancedRedstoneInterface

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94
	}

}