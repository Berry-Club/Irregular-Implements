package dev.aaronhowser.mods.irregular_implements.menu.ender_letter

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class EnderLetterScreen(
	menu: EnderLetterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<EnderLetterMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.EnderLetter

}