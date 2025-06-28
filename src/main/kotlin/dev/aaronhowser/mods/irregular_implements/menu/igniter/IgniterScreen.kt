package dev.aaronhowser.mods.irregular_implements.menu.igniter

import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ChangingTextButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class IgniterScreen(
	menu: IgniterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<IgniterMenu>(menu, playerInventory, title) {

	private lateinit var changeModeButton: ChangingTextButton

	override val background = ScreenTextures.Background.Igniter

	override fun baseInit() {
		this.changeModeButton = ChangingTextButton(
			x = this.leftPos + 5,
			y = this.topPos + 5,
			width = this.imageWidth - 10,
			height = this.imageHeight - 10,
			messageGetter = { this.menu.mode.nameComponent },   //FIXME: For some reason this won't sync to client, it gets stuck on the first value
			onPress = { ModPacketHandler.messageServer(ClientClickedMenuButton(IgniterMenu.CYCLE_MODE_BUTTON_ID)) }
		)

		this.addRenderableWidget(this.changeModeButton)
	}

	override var showInventoryLabel: Boolean = false
	override var showTitleLabel: Boolean = false

}