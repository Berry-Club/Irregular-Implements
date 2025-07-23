package dev.aaronhowser.mods.irregular_implements.menu.notification_interface

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.ScreenWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class NotificationInterfaceScreen(
	menu: NotificationInterfaceMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<NotificationInterfaceMenu>(menu, playerInventory, title), ScreenWithStrings {

	private lateinit var titleEditBox: EditBox
	private lateinit var descriptionEditBox: EditBox

	override val background = ScreenTextures.Background.NotificationInterface

	override fun baseInit() {
		this.titleEditBox = EditBox(
			this.font,
			this.leftPos + 34,
			this.topPos + 15,
			130,
			15,
			Component.empty()
		)

		this.titleEditBox.setResponder(::setTitle)

		this.descriptionEditBox = EditBox(
			this.font,
			this.leftPos + 34,
			this.topPos + 18 + 22,
			130,
			15,
			Component.empty()
		)

		this.descriptionEditBox.setResponder(::setDescription)

		this.addRenderableWidget(this.titleEditBox)
		this.addRenderableWidget(this.descriptionEditBox)
	}

	private fun setTitle(title: String) {
		if (this.menu.setTitle(title)) {
			ModPacketHandler.messageServer(
				ClientChangedMenuString(
					NotificationInterfaceMenu.TITLE_STRING_ID,
					title
				)
			)
		}
	}

	private fun setDescription(description: String) {
		if (this.menu.setDescription(description)) {
			ModPacketHandler.messageServer(
				ClientChangedMenuString(
					NotificationInterfaceMenu.DESCRIPTION_STRING_ID,
					description
				)
			)
		}
	}

	override var showInventoryLabel: Boolean = false

	override fun receivedString(stringId: Int, stringReceived: String) {
		when (stringId) {
			NotificationInterfaceMenu.TITLE_STRING_ID -> this.titleEditBox.value = stringReceived
			NotificationInterfaceMenu.DESCRIPTION_STRING_ID -> this.descriptionEditBox.value = stringReceived
		}
	}
}