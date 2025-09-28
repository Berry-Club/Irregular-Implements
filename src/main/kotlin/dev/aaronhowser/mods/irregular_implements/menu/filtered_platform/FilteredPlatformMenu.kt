package dev.aaronhowser.mods.irregular_implements.menu.filtered_platform

import dev.aaronhowser.mods.irregular_implements.menu.FilteredSlot
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class FilteredPlatformMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val platformContainer: Container
) : MenuWithInventory(ModMenuTypes.FILTERED_PLATFORM.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(1)
			)

	init {
		checkContainerSize(platformContainer, 1)

		platformContainer.startOpen(playerInventory.player)

		addSlots()
		addPlayerInventorySlots(47)
	}

	override fun addSlots() {

		//TODO: Add an Item Filter outline to the slot background
		val platformSlot = FilteredSlot(platformContainer, 0, 80, 10) { it.has(ModDataComponents.ITEM_FILTER) }
		this.addSlot(platformSlot)
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		val slot = slots.getOrNull(index)

		if (slot == null || !slot.hasItem()) return ItemStack.EMPTY

		val stackThere = slot.item
		val copyStack = stackThere.copy()

		// If the slot is in the platform container
		if (index == 0) {
			if (!this.moveItemStackTo(stackThere, 1, this.slots.size, true)) {
				return ItemStack.EMPTY
			}
		} else {
			if (this.moveItemStackTo(stackThere, 0, 1, false)) {
				return ItemStack.EMPTY
			}
		}

		if (stackThere.isEmpty) {
			slot.setByPlayer(ItemStack.EMPTY)
		} else {
			slot.setChanged()
		}

		if (stackThere.count == copyStack.count) return ItemStack.EMPTY

		slot.onTake(player, stackThere)

		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return platformContainer.stillValid(player)
	}

}