package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class VoidStoneScreen(
	menu: VoidStoneMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<VoidStoneMenu>(menu, playerInventory, title) {

	override val background = ScreenTextures.Background.VoidStone

}