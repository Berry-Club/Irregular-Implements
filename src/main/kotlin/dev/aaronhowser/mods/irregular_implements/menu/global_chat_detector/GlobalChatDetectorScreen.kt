package dev.aaronhowser.mods.irregular_implements.menu.global_chat_detector

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.ScreenWithStrings
import dev.aaronhowser.mods.aaron.menu.components.MultiStageSpriteButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.packet.c2s.ClientChangedMenuString
import dev.aaronhowser.mods.aaron.packet.c2s.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
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

	override val background: ScreenBackground = ScreenTextures.Backgrounds.GLOBAL_CHAT_DETECTOR

	override fun baseInit() {
		inventoryLabelY -= 8

		toggleMessagePassButton = MultiStageSpriteButton.Builder(font)
			.addStage(
				message = ModTooltipLang.STOPS_MESSAGE.toComponent(),
				sprite = ScreenTextures.Sprites.ChatDetector.MESSAGE_BREAK
			)
			.addStage(
				message = ModTooltipLang.DOESNT_STOP_MESSAGE.toComponent(),
				sprite = ScreenTextures.Sprites.ChatDetector.MESSAGE_CONTINUE
			)
			.size(
				width = 20,
				height = 20
			)
			.currentStageGetter(
				currentStageGetter = { if (menu.shouldMessageStop) 0 else 1 }    // 1 means it stops messages
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(GlobalChatDetectorMenu.TOGGLE_MESSAGE_PASS_BUTTON_ID)
					packet.messageServer()
				}
			)
			.build()

		toggleMessagePassButton.setPosition(
			rightPos - toggleMessagePassButton.width - 5,
			topPos + 5
		)

		val screenWidth = rightPos - leftPos

		regexStringEditBox = EditBox(
			font,
			leftPos + 5,
			topPos - 5 + 20,
			screenWidth - 5 - 5 - 20 - 5,
			20,
			ModTooltipLang.MESSAGE_REGEX.toComponent()
		)

		regexStringEditBox.setCanLoseFocus(false)
		regexStringEditBox.setTextColor(-1)
		regexStringEditBox.setTextColorUneditable(-1)
		regexStringEditBox.setResponder(::setRegexString)
		regexStringEditBox.setMaxLength(10000)

		addRenderableWidget(toggleMessagePassButton)
		addRenderableWidget(regexStringEditBox)
	}

	// Behavior

	override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		if (keyCode == 256) {
			minecraft?.player?.closeContainer()
		}

		return if (!regexStringEditBox.keyPressed(keyCode, scanCode, modifiers) && !regexStringEditBox.canConsumeInput()) {
			super.keyPressed(keyCode, scanCode, modifiers)
		} else {
			true
		}
	}

	override fun resize(minecraft: Minecraft, width: Int, height: Int) {
		val currentRegexString = regexStringEditBox.value
		super.resize(minecraft, width, height)
		regexStringEditBox.value = currentRegexString
	}

	private fun setRegexString(string: String) {
		if (menu.setRegex(string)) {
			val packet = ClientChangedMenuString(GlobalChatDetectorMenu.REGEX_STRING_ID, string)
			packet.messageServer()
		}
	}

	override fun receivedString(stringId: Int, stringReceived: String) {
		if (stringId == GlobalChatDetectorMenu.REGEX_STRING_ID) {
			regexStringEditBox.value = stringReceived
		}
	}

}