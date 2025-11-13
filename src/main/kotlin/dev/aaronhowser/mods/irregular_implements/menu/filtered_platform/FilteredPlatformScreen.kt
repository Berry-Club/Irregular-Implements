package dev.aaronhowser.mods.irregular_implements.menu.filtered_platform

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.IIScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class FilteredPlatformScreen(
	menu: FilteredPlatformMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<FilteredPlatformMenu>(menu, playerInventory, title) {

	override val background = IIScreenTextures.Background.FilteredSuperLubricantPlatform

	override var showInventoryLabel: Boolean = false
	override var showTitleLabel: Boolean = false

}