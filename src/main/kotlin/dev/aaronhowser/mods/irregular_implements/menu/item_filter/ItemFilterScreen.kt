package dev.aaronhowser.mods.irregular_implements.menu.item_filter

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry.Companion.isNullOrEmpty
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Inventory

class ItemFilterScreen(
	menu: ItemFilterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<ItemFilterMenu>(menu, playerInventory, title) {

	override val background = ScreenTextures.Background.ItemFilter

	private val leftButtons: MutableSet<Button> = mutableSetOf()
	private val rightButtons: MutableSet<Button> = mutableSetOf()

	private lateinit var invertBlacklistButton: Button

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94

		setButtons()
	}

	private fun setButtons() {
		this.leftButtons.clear()
		this.rightButtons.clear()

		addToggleBlacklistButton()

		for (index in 0 until 9) {
			addLeftButton(index)
			addRightButtons(index)
		}
	}

	private fun addToggleBlacklistButton() {
		val x = this.leftPos + this.imageWidth - 24
		val y = this.topPos + 5

		val onPress = {
			val packet = ClientClickedMenuButton(ItemFilterMenu.TOGGLE_BLACKLIST_BUTTON_ID)
			packet.messageServer()
		}

		this.invertBlacklistButton = MultiStageSpriteButton.Builder(this.font)
			.location(x, y)
			.size(16)
			.addStage(
				message = ModTooltipLang.WHITELIST.toComponent(),
				sprite = ScreenTextures.Sprites.ItemFilter.Whitelist
			)
			.addStage(
				message = ModTooltipLang.BLACKLIST.toComponent(),
				sprite = ScreenTextures.Sprites.ItemFilter.Blacklist
			)
			.currentStageGetter(
				currentStageGetter = { if (this.menu.getIsBlacklist()) 1 else 0 }
			)
			.onPress(onPress)
			.build()

		this.addRenderableWidget(this.invertBlacklistButton)
	}

	// Toggles between Item Filter and Tag Filter
	private fun addLeftButton(index: Int) {
		val x = this.leftPos + 8 + index * 18
		val y = this.topPos + 15

		val width = 8
		val height = 8

		val buttonId = ItemFilterMenu.getLeftButtonId(index)

		val button = ChangingColorButton(
			x = x,
			y = y,
			width = width,
			height = height,
			messagesGetter = { leftMessageGetter(index) },
			colorGetter = { leftColorGetter(index) },
			font = this.font,
			onPress = {
				val packet = ClientClickedMenuButton(buttonId)
				packet.messageServer()
			}
		)

		button.visible = !this.menu.getFilter()?.getOrNull(index).isNullOrEmpty()

		this.leftButtons.add(button)
		this.addRenderableWidget(button)
	}

	private fun leftMessageGetter(index: Int): List<MutableComponent> {
		val filterAtIndex = this.menu.getFilter()?.getOrNull(index)

		return listOf(
			ModTooltipLang.ITEM_FILTER_ITEM.toComponent().withStyle(
				if (filterAtIndex is FilterEntry.Item) ChatFormatting.GRAY else ChatFormatting.DARK_GRAY
			),
			ModTooltipLang.ITEM_FILTER_TAG.toComponent().withStyle(
				if (filterAtIndex is FilterEntry.Item) ChatFormatting.DARK_GRAY else ChatFormatting.GRAY
			)
		)
	}

	private fun leftColorGetter(index: Int): Int {
		val filterAtIndex = this.menu.getFilter()?.getOrNull(index)

		return if (filterAtIndex is FilterEntry.Item) {
			0xFF5969FF.toInt()
		} else {
			0xFF00B7A2.toInt()
		}
	}

	// If it's an Item Filter, toggles between requiring the same components or not
	// If it's a Tag Filter, cycles which Tag it's filtering
	private fun addRightButtons(index: Int) {
		val x = this.leftPos + 8 + index * 18 + 9
		val y = this.topPos + 15

		val width = 8
		val height = 8

		val buttonId = ItemFilterMenu.getRightButtonId(index)

		val button = ChangingColorButton(
			x = x,
			y = y,
			width = width,
			height = height,
			messagesGetter = { rightMessageGetter(index) },
			colorGetter = { rightColorGetter(index) },
			font = this.font,
			onPress = {
				val packet = ClientClickedMenuButton(buttonId)
				packet.messageServer()
			}
		)

		button.visible = !this.menu.getFilter()?.getOrNull(index).isNullOrEmpty()

		this.rightButtons.add(button)
		this.addRenderableWidget(button)
	}

	private fun rightMessageGetter(index: Int): List<MutableComponent> {
		return when (
			val filterAtIndex = this.menu.getFilter()?.getOrNull(index)
		) {
			is FilterEntry.Item -> {
				listOf(
					ModTooltipLang.ITEM_FILTER_IGNORE_COMPONENTS.toComponent().withStyle(
						if (filterAtIndex.requireSameComponents) ChatFormatting.DARK_GRAY else ChatFormatting.GRAY
					),
					ModTooltipLang.ITEM_FILTER_REQUIRE_COMPONENTS.toComponent().withStyle(
						if (filterAtIndex.requireSameComponents) ChatFormatting.GRAY else ChatFormatting.DARK_GRAY
					)
				)
			}

			is FilterEntry.Tag -> {
				val itemTags = filterAtIndex.backupStack.tags.toList()

				itemTags.map {
					Component
						.literal(it.location().toString())
						.withStyle(
							if (it == filterAtIndex.tagKey) ChatFormatting.GRAY else ChatFormatting.DARK_GRAY
						)
				}
			}

			else -> {
				listOf(Component.empty())
			}
		}
	}

	private fun rightColorGetter(index: Int): Int {
		return when (
			val filterAtIndex = this.menu.getFilter()?.getOrNull(index)
		) {
			is FilterEntry.Item -> {
				if (filterAtIndex.requireSameComponents) {
					0xFF37C63C.toInt()
				} else {
					0xFFFF5623.toInt()
				}
			}

			is FilterEntry.Tag -> {
				val itemTags = filterAtIndex.backupStack.tags.toList()
				val currentTagIndex = itemTags.indexOf(filterAtIndex.tagKey)

				val rgb = Mth.hsvToArgb(
					(currentTagIndex.toFloat() / itemTags.size.toFloat()),
					1.0f,
					1.0f,
					0xFF
				)

				rgb
			}

			else -> 0x00000000
		}
	}

	override fun containerTick() {
		super.containerTick()

		for (buttonIndex in this.leftButtons.indices) {
			val button = this.leftButtons.elementAtOrNull(buttonIndex) ?: continue

			button.visible = !this.menu.getFilter()?.getOrNull(buttonIndex).isNullOrEmpty()
		}

		for (buttonIndex in this.rightButtons.indices) {
			val button = this.rightButtons.elementAtOrNull(buttonIndex) ?: continue

			button.visible = !this.menu.getFilter()?.getOrNull(buttonIndex).isNullOrEmpty()
		}
	}

}