package dev.aaronhowser.mods.irregular_implements.menu.ender_letter

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.ScreenWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Inventory

class EnderLetterScreen(
	menu: EnderLetterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<EnderLetterMenu>(menu, playerInventory, title), ScreenWithStrings {

	override val background: ScreenTextures.Background = ScreenTextures.Background.EnderLetter

	private lateinit var recipientEditBox: EditBox

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94

		val screenWidth = this.rightPos - this.leftPos

		val boxWidth = Mth.ceil(screenWidth * 0.5)

		recipientEditBox = EditBox(
			this.font,
			rightPos - boxWidth - 7,
			topPos + 5,
			boxWidth,
			10,
			ModTooltipLang.MESSAGE_REGEX.toComponent()
		)

		recipientEditBox.setResponder(::setRecipientString)

		addRenderableWidget(recipientEditBox)
	}

	private fun setRecipientString(newString: String) {
		if (this.menu.setNewRecipient(newString)) {
			ModPacketHandler.messageServer(
				ClientChangedMenuString(
					EnderLetterMenu.RECIPIENT_STRING_ID,
					newString
				)
			)
		}
	}

	override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		if (keyCode == 256) {
			this.minecraft?.player?.closeContainer()
		}

		return if (!recipientEditBox.keyPressed(keyCode, scanCode, modifiers) && !recipientEditBox.canConsumeInput()) {
			super.keyPressed(keyCode, scanCode, modifiers)
		} else {
			true
		}
	}

	override fun resize(minecraft: Minecraft, width: Int, height: Int) {
		val currentRegexString = recipientEditBox.value
		super.resize(minecraft, width, height)
		recipientEditBox.value = currentRegexString
	}

	override fun receivedString(stringId: Int, string: String) {
		when (stringId) {
			EnderLetterMenu.RECIPIENT_STRING_ID -> recipientEditBox.value = string
		}
	}

}