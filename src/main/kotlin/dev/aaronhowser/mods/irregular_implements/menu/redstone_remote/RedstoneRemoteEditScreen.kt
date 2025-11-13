package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class RedstoneRemoteEditScreen(
	menu: RedstoneRemoteEditMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<RedstoneRemoteEditMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.redstoneRemoteEdit

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94
	}

}