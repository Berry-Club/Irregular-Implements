package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.aaron.menu.components.ItemStackButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class RedstoneRemoteUseScreen(
	menu: RedstoneRemoteUseMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<RedstoneRemoteUseMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.redstoneRemoteUse

	override val showInventoryLabel: Boolean = false

	override fun baseInit() {
		addButtons()
	}

	private fun addButtons() {
		val dataComponent = menu.getHeldItemStack().get(ModDataComponents.REDSTONE_REMOTE) ?: return

		for (i in 0 until 9) {
			val (locationFilter, icon) = dataComponent.getPair(i)
			if (locationFilter.isEmpty) continue

			val component = locationFilter.get(DataComponents.CUSTOM_NAME) ?: Component.empty()

			val button = ItemStackButton(
				x = leftPos + 5 + (i * 22),
				y = topPos + 16,
				width = 20,
				height = 20,
				itemStack = icon,
				message = component,
				onPress = {
					val packet = ClientClickedMenuButton(i)
					packet.messageServer()
				},
				font = font
			)

			addRenderableWidget(button)
		}
	}

}