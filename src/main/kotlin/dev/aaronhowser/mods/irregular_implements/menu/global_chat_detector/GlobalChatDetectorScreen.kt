package dev.aaronhowser.mods.irregular_implements.menu.global_chat_detector

import dev.aaronhowser.mods.aaron.menu.ScreenWithStrings
import dev.aaronhowser.mods.aaron.menu.components.MultiStageSpriteButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class GlobalChatDetectorScreen(
	menu: GlobalChatDetectorMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<GlobalChatDetectorMenu>(menu, playerInventory, title), ScreenWithStrings {

	private lateinit var toggleMessagePassButton: MultiStageSpriteButton
	private lateinit var regexStringEditBox: EditBox

	override val background: ScreenBackground = ScreenTextures.Backgrounds.globalChatDetector

	override fun baseInit() {
		this.inventoryLabelY -= 8

		this.toggleMessagePassButton = MultiStageSpriteButton.Builder(this.font)
			.addStage(
				message = ModTooltipLang.STOPS_MESSAGE.toComponent(),
				sprite = ScreenTextures.Sprites.ChatDetector.MessageStop
			)
			.addStage(
				message = ModTooltipLang.DOESNT_STOP_MESSAGE.toComponent(),
				sprite = ScreenTextures.Sprites.ChatDetector.MessageContinue
			)
			.size(
				width = 20,
				height = 20
			)
			.currentStageGetter(
				currentStageGetter = { if (this.menu.shouldMessageStop) 0 else 1 }    // 1 means it stops messages
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(GlobalChatDetectorMenu.TOGGLE_MESSAGE_PASS_BUTTON_ID)
					packet.messageServer()
				}
			)
			.build()

		this.toggleMessagePassButton.setPosition(
			this.rightPos - this.toggleMessagePassButton.width - 5,
			this.topPos + 5
		)

		val screenWidth = this.rightPos - this.leftPos

		this.regexStringEditBox = EditBox(
			this.font,
			this.leftPos + 5,
			this.topPos - 5 + 20,
			screenWidth - 5 - 5 - 20 - 5,
			20,
			ModTooltipLang.MESSAGE_REGEX.toComponent()
		)

		this.regexStringEditBox.setCanLoseFocus(false)
		this.regexStringEditBox.setTextColor(-1)
		this.regexStringEditBox.setTextColorUneditable(-1)
		this.regexStringEditBox.setResponder(::setRegexString)
		this.regexStringEditBox.setMaxLength(10000)

		addRenderableWidget(this.toggleMessagePassButton)
		addRenderableWidget(this.regexStringEditBox)
	}

	// Behavior

	override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		if (keyCode == 256) {
			this.minecraft?.player?.closeContainer()
		}

		return if (!this.regexStringEditBox.keyPressed(keyCode, scanCode, modifiers) && !this.regexStringEditBox.canConsumeInput()) {
			super.keyPressed(keyCode, scanCode, modifiers)
		} else {
			true
		}
	}

	override fun resize(minecraft: Minecraft, width: Int, height: Int) {
		val currentRegexString = this.regexStringEditBox.value
		super.resize(minecraft, width, height)
		this.regexStringEditBox.value = currentRegexString
	}

	private fun setRegexString(string: String) {
		if (this.menu.setRegex(string)) {
			val packet = ClientChangedMenuString(GlobalChatDetectorMenu.REGEX_STRING_ID, string)
			packet.messageServer()
		}
	}

	override fun receivedString(stringId: Int, stringReceived: String) {
		if (stringId == GlobalChatDetectorMenu.REGEX_STRING_ID) {
			this.regexStringEditBox.value = stringReceived
		}
	}

}