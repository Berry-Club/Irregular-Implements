package dev.aaronhowser.mods.irregular_implements.menu.item_filter

import dev.aaronhowser.mods.aaron.menu.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.HeldItemContainerMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.core.NonNullList
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class ItemFilterMenu(
	containerId: Int,
	playerInventory: Inventory
) : HeldItemContainerMenu(
	ModItems.ITEM_FILTER,
	ModMenuTypes.ITEM_FILTER.get(),
	containerId, playerInventory
), MenuWithButtons {

	init {
		addSlots()
		addPlayerInventorySlots(59)
	}

	private fun getFilterComponent(): ItemFilterDataComponent? {
		return getHeldItemStack().get(ModDataComponents.ITEM_FILTER)
	}

	fun getFilter(): NonNullList<FilterEntry>? {
		return getFilterComponent()?.entries
	}

	fun getIsBlacklist(): Boolean = getFilterComponent()?.isBlacklist ?: false

	private fun setIsBlacklist(value: Boolean) {
		val filterComponent = getFilterComponent() ?: return

		if (value == filterComponent.isBlacklist) return

		getHeldItemStack().set(
			ModDataComponents.ITEM_FILTER,
			filterComponent.copy(isBlacklist = value)
		)
	}

	override fun addSlots() {
		val holderLookup = this.playerInventory.player.registryAccess()

		for (index in 0 until 9) {
			val x = 8 + index * 18
			val y = 26

			val slot = ItemFilterSlot(::getHeldItemStack, holderLookup, x, y)

			this.addSlot(slot)
		}

	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return player.getItemInHand(hand).`is`(ModItems.ITEM_FILTER)
	}

	override fun handleButtonPressed(buttonId: Int) {
		when (buttonId) {
			TOGGLE_BLACKLIST_BUTTON_ID -> setIsBlacklist(!getIsBlacklist())

			in 1..9 -> pressLeftButton(buttonId - 1)

			in 10..18 -> pressRightButton(buttonId - 10)
		}
	}

	// Toggles between Item Filter and Tag Filter
	private fun pressLeftButton(slotIndex: Int) {
		val filter = this.getFilter() ?: return
		val entry = filter.getOrNull(slotIndex) ?: return

		val newEntry = when (entry) {

			// If it's an ItemTag, return a SpecificItem
			is FilterEntry.Tag -> entry.getAsSpecificItemEntry()

			// If it's a SpecificItem, return an ItemTag
			is FilterEntry.Item -> {
				val tag = entry.stack.tags.toList().firstOrNull() ?: return
				FilterEntry.Tag(
					tag,
					entry.stack.copy()
				)
			}

			else -> return
		}

		val newFilter = filter.toMutableList()
		newFilter[slotIndex] = newEntry

		getHeldItemStack().set(
			ModDataComponents.ITEM_FILTER,
			ItemFilterDataComponent(newFilter, getIsBlacklist())
		)
	}

	// If it's an Item Filter, toggles between requiring the same components or not
	// If it's a Tag Filter, cycles which Tag it's filtering
	private fun pressRightButton(slotIndex: Int) {
		val filter = this.getFilter() ?: return
		val entry = filter.getOrNull(slotIndex) ?: return

		if (entry is FilterEntry.Item) {
			toggleNeedsComponents(slotIndex, filter, entry)
		} else if (entry is FilterEntry.Tag) {
			cycleTag(slotIndex, filter, entry)
		}

	}

	private fun toggleNeedsComponents(slotIndex: Int, filter: NonNullList<FilterEntry>, entry: FilterEntry.Item) {
		val newEntry = entry.copy(requireSameComponents = !entry.requireSameComponents)

		val newFilter = filter.toMutableList()
		newFilter[slotIndex] = newEntry

		getHeldItemStack().set(
			ModDataComponents.ITEM_FILTER,
			ItemFilterDataComponent(newFilter, getIsBlacklist())
		)
	}

	private fun cycleTag(slotIndex: Int, filter: NonNullList<FilterEntry>, entry: FilterEntry.Tag) {

		val nextTag = entry.getNextTag()
		val newEntry = entry.copy(tagKey = nextTag)

		val newFilter = filter.toMutableList()
		newFilter[slotIndex] = newEntry

		getHeldItemStack().set(
			ModDataComponents.ITEM_FILTER,
			ItemFilterDataComponent(newFilter, getIsBlacklist())
		)
	}

	companion object {
		// Toggles between Item Filter and Tag Filter
		fun getLeftButtonId(slotIndex: Int): Int {
			return slotIndex + 1
		}

		// If it's an Item Filter, toggles between requiring the same components or not
		// If it's a Tag Filter, cycles which Tag it's filtering
		fun getRightButtonId(slotIndex: Int): Int {
			return slotIndex + 10
		}

		const val TOGGLE_BLACKLIST_BUTTON_ID = 0
	}

}