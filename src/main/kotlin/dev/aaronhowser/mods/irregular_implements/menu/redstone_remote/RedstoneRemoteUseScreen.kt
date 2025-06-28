package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ItemStackButton
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class RedstoneRemoteUseScreen(
	menu: RedstoneRemoteUseMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<RedstoneRemoteUseMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.RedstoneRemoteUse

	override val showInventoryLabel: Boolean = false

	override fun baseInit() {
		addButtons()
	}

	private fun addButtons() {
		val dataComponent = menu.redstoneRemoteStack.get(ModDataComponents.REDSTONE_REMOTE) ?: return

		for (i in 0 until 9) {
			val (locationFilter, icon) = dataComponent.getPair(i)
			if (locationFilter.isEmpty) continue

			val button = ItemStackButton(
				x = leftPos + 5 + (i * 20),
				y = topPos + 17,
				width = 20,
				height = 20,
				itemStack = icon,
				onPress = {
					ModPacketHandler.messageServer(ClientClickedMenuButton(i))
				},
				font = font
			)

			addRenderableWidget(button)
		}
	}

}