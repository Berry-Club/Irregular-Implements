package dev.aaronhowser.mods.irregular_implements.menu.auto_placer

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.IIScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

//TODO: A way to toggle between place on pulse and place while powered
class AutoPlacerScreen(
	menu: AutoPlacerMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<AutoPlacerMenu>(menu, playerInventory, title) {

	override val background: IIScreenTextures.Background = IIScreenTextures.Background.AutoPlacer

}