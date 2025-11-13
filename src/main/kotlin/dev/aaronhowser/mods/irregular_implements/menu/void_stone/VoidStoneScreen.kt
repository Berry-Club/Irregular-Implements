package dev.aaronhowser.mods.irregular_implements.menu.void_stone

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.IIScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class VoidStoneScreen(
	menu: VoidStoneMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<VoidStoneMenu>(menu, playerInventory, title) {

	override val background = IIScreenTextures.Background.VoidStone

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94
	}

}