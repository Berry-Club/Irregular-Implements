package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class RedstoneRemoteUseScreen(
	menu: RedstoneRemoteUseMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<RedstoneRemoteUseMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.RedstoneRemoteUse


}