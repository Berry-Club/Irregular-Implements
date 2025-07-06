package dev.aaronhowser.mods.irregular_implements.menu.ender_mailbox

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class EnderMailboxScreen(
	menu: EnderMailboxMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<EnderMailboxMenu>(menu, playerInventory, title) {
	override val background: ScreenTextures.Background = ScreenTextures.Background.EnderMailbox
}