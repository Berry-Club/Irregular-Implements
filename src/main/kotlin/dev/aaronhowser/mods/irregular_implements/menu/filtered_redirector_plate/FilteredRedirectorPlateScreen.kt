package dev.aaronhowser.mods.irregular_implements.menu.filtered_redirector_plate

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class FilteredRedirectorPlateScreen(
	menu: FilteredRedirectorPlateMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<FilteredRedirectorPlateMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.FILTERED_REDIRECTOR_PLATE
	override var showTitleLabel: Boolean = false

}
