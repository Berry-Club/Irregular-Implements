package dev.aaronhowser.mods.irregular_implements.menu.online_detector

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.ScreenWithStrings
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.packet.c2s.ClientChangedMenuString
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class OnlineDetectorScreen(
	menu: OnlineDetectorMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<OnlineDetectorMenu>(menu, playerInventory, title), ScreenWithStrings {

	private lateinit var usernameEditBox: EditBox

	override val background: ScreenBackground = ScreenTextures.Backgrounds.ONLINE_DETECTOR

	override fun baseInit() {
		val editBoxHeight = 20

		usernameEditBox = EditBox(
			font,
			leftPos + 5,
			bottomPos - 5 - editBoxHeight,
			imageWidth - 5 - 5,
			editBoxHeight,
			Component.empty()
		)

		usernameEditBox.setCanLoseFocus(false)
		usernameEditBox.setTextColor(-1)
		usernameEditBox.setTextColorUneditable(-1)
		usernameEditBox.setResponder(::setUsername)

		addRenderableWidget(usernameEditBox)
	}

	override var showInventoryLabel: Boolean = false

	// Behavior

	override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		if (keyCode == 256) {
			minecraft?.player?.closeContainer()
		}

		return if (!usernameEditBox.keyPressed(keyCode, scanCode, modifiers) && !usernameEditBox.canConsumeInput()) {
			super.keyPressed(keyCode, scanCode, modifiers)
		} else {
			true
		}
	}

	override fun resize(minecraft: Minecraft, width: Int, height: Int) {
		val currentRegexString = usernameEditBox.value
		super.resize(minecraft, width, height)
		usernameEditBox.value = currentRegexString
	}

	private fun setUsername(string: String) {
		if (menu.setUsername(string)) {
			val packet = ClientChangedMenuString(OnlineDetectorMenu.USERNAME_STRING_ID, string)
			packet.messageServer()
		}
	}

	override fun receivedString(stringId: Int, stringReceived: String) {
		if (stringId == OnlineDetectorMenu.USERNAME_STRING_ID) {
			usernameEditBox.value = stringReceived
		}
	}

}