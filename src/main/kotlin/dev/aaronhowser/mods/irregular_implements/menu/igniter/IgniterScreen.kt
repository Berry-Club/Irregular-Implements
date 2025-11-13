package dev.aaronhowser.mods.irregular_implements.menu.igniter

import dev.aaronhowser.mods.aaron.menu.components.ChangingTextButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
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
		this.changeModeButton = ChangingTextButton(
			x = this.leftPos + 5,
			y = this.topPos + 5,
			width = this.imageWidth - 10,
			height = this.imageHeight - 10,
			messageGetter = { this.menu.mode.nameComponent },   //FIXME: For some reason this won't sync to client, it gets stuck on the first value
			onPress = {
				val packet = ClientClickedMenuButton(IgniterMenu.CYCLE_MODE_BUTTON_ID)
				packet.messageServer()
			}
		)

		this.addRenderableWidget(this.changeModeButton)
	}

	override var showInventoryLabel: Boolean = false
	override var showTitleLabel: Boolean = false

}