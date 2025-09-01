package dev.aaronhowser.mods.irregular_implements.menu.online_detector

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.ScreenWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
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

	override val background = ScreenTextures.Background.ONLINE_DETECTOR

	override fun baseInit() {
		val editBoxHeight = 20

		this.usernameEditBox = EditBox(
			this.font,
			this.leftPos + 5,
			this.bottomPos - 5 - editBoxHeight,
			this.imageWidth - 5 - 5,
			editBoxHeight,
			Component.empty()
		)

		this.usernameEditBox.setCanLoseFocus(false)
		this.usernameEditBox.setTextColor(-1)
		this.usernameEditBox.setTextColorUneditable(-1)
		this.usernameEditBox.setResponder(::setUsername)

		this.addRenderableWidget(this.usernameEditBox)
	}

	override var showInventoryLabel: Boolean = false

	// Behavior

	override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		if (keyCode == 256) {
			this.minecraft?.player?.closeContainer()
		}

		return if (!this.usernameEditBox.keyPressed(keyCode, scanCode, modifiers) && !this.usernameEditBox.canConsumeInput()) {
			super.keyPressed(keyCode, scanCode, modifiers)
		} else {
			true
		}
	}

	override fun resize(minecraft: Minecraft, width: Int, height: Int) {
		val currentRegexString = this.usernameEditBox.value
		super.resize(minecraft, width, height)
		this.usernameEditBox.value = currentRegexString
	}

	private fun setUsername(string: String) {
		if (this.menu.setUsername(string)) {
			val packet = ClientChangedMenuString(OnlineDetectorMenu.USERNAME_STRING_ID, string)
			packet.messageServer()
		}
	}

	override fun receivedString(stringId: Int, stringReceived: String) {
		if (stringId == OnlineDetectorMenu.USERNAME_STRING_ID) {
			this.usernameEditBox.value = stringReceived
		}
	}

}