package dev.aaronhowser.mods.irregular_implements.menu.ender_energy_distributor

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.IIScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class EnderEnergyDistributorScreen(
	menu: EnderEnergyDistributorMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<EnderEnergyDistributorMenu>(menu, playerInventory, title) {

	override val background: IIScreenTextures.Background = IIScreenTextures.Background.EnderEnergyDistributor

	override fun baseInit() {
		titleLabelX += 8
		inventoryLabelX += 8
		inventoryLabelY -= 34
	}

}