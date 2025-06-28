package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class RedstoneRemoteUseScreen(
	menu: RedstoneRemoteUseMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<RedstoneRemoteUseMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.RedstoneRemoteUse

	override val showInventoryLabel: Boolean = false

}