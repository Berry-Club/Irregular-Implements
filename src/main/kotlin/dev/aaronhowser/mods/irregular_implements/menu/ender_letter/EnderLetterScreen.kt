package dev.aaronhowser.mods.irregular_implements.menu.ender_letter

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.menu.ScreenWithStrings
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
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

		recipientEditBox = EditBox(
			this.font,
			this.leftPos + 5,
			this.bottomPos - 5 - 20,
			screenWidth - 5 - 5,
			20,
			ModLanguageProvider.Tooltips.MESSAGE_REGEX.toComponent()
		)
	}

	override fun receivedString(stringId: Int, string: String) {
		when (stringId) {
			EnderLetterMenu.RECIPIENT_STRING_ID -> recipientEditBox.value = string
		}
	}

}