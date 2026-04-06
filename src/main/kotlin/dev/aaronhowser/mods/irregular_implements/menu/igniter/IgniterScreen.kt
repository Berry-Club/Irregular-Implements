package dev.aaronhowser.mods.irregular_implements.menu.igniter

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.components.ChangingTextButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.packet.c2s.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class IgniterScreen(
	menu: IgniterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<IgniterMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.IGNITER

	private lateinit var changeModeButton: ChangingTextButton

	override fun baseInit() {
		changeModeButton = ChangingTextButton(
			x = leftPos + 5,
			y = topPos + 5,
			width = imageWidth - 10,
			height = imageHeight - 10,
			messageGetter = { menu.mode.nameComponent },   //FIXME: For some reason this won't sync to client, it gets stuck on the first value
			onPress = {
				val packet = ClientClickedMenuButton(IgniterMenu.CYCLE_MODE_BUTTON_ID)
				packet.messageServer()
			}
		)

		addRenderableWidget(changeModeButton)
	}

	override var showInventoryLabel: Boolean = false
	override var showTitleLabel: Boolean = false

}