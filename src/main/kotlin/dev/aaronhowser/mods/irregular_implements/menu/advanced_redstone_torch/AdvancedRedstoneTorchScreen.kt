package dev.aaronhowser.mods.irregular_implements.menu.advanced_redstone_torch

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class AdvancedRedstoneTorchScreen(
	menu: AdvancedRedstoneTorchMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<AdvancedRedstoneTorchMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.AdvancedRedstoneTorch
	override val showInventoryLabel: Boolean = false
	override val showTitleLabel: Boolean = false

}