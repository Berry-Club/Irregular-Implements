package dev.aaronhowser.mods.irregular_implements.menu.notification_interface

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.ScreenWithStrings
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.packet.c2s.ClientChangedMenuString
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import org.lwjgl.glfw.GLFW

class NotificationInterfaceScreen(
	menu: NotificationInterfaceMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<NotificationInterfaceMenu>(menu, playerInventory, title), ScreenWithStrings {

	private lateinit var titleEditBox: EditBox
	private lateinit var descriptionEditBox: EditBox

	override val background: ScreenBackground = ScreenTextures.Backgrounds.NOTIFICATION_INTERFACE

	override fun baseInit() {
		val title = EditBox(
			font,
			leftPos + 34,
			topPos + 15,
			130,
			15,
			Component.empty()
		)

		title.setHint(ModMessageLang.NOTIFICATION_INTERFACE_TITLE.toGrayComponent())

		title.setResponder(::setTitle)

		titleEditBox = title

		val description = EditBox(
			font,
			leftPos + 34,
			topPos + 18 + 22,
			130,
			15,
			Component.empty()
		)

		description.setHint(ModMessageLang.NOTIFICATION_INTERFACE_DESCRIPTION.toGrayComponent())

		description.setResponder(::setDescription)

		descriptionEditBox = description

		addRenderableWidget(titleEditBox)
		addRenderableWidget(descriptionEditBox)
	}

	override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			minecraft?.player?.closeContainer()
			return true
		}

		if (keyCode == GLFW.GLFW_KEY_TAB) {
			if (titleEditBox.isFocused) {
				titleEditBox.isFocused = false
				descriptionEditBox.isFocused = true
			} else if (descriptionEditBox.isFocused) {
				descriptionEditBox.isFocused = false
				titleEditBox.isFocused = true
			}

			return true
		}

		if (titleEditBox.keyPressed(keyCode, scanCode, modifiers) || titleEditBox.canConsumeInput()) {
			return true
		}

		if (descriptionEditBox.keyPressed(keyCode, scanCode, modifiers) || descriptionEditBox.canConsumeInput()) {
			return true
		}

		return super.keyPressed(keyCode, scanCode, modifiers)
	}

	private fun setTitle(title: String) {
		if (menu.setTitle(title)) {
			val packet = ClientChangedMenuString(NotificationInterfaceMenu.TITLE_STRING_ID, title)
			packet.messageServer()
		}
	}

	private fun setDescription(description: String) {
		if (menu.setDescription(description)) {
			val packet = ClientChangedMenuString(NotificationInterfaceMenu.DESCRIPTION_STRING_ID, description)
			packet.messageServer()
		}
	}

	override var showInventoryLabel: Boolean = false

	override fun receivedString(stringId: Int, stringReceived: String) {
		when (stringId) {
			NotificationInterfaceMenu.TITLE_STRING_ID -> titleEditBox.value = stringReceived
			NotificationInterfaceMenu.DESCRIPTION_STRING_ID -> descriptionEditBox.value = stringReceived
		}
	}
}