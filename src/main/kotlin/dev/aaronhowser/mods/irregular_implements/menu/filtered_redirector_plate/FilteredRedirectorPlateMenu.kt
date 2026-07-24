package dev.aaronhowser.mods.irregular_implements.menu.filtered_redirector_plate

import dev.aaronhowser.mods.aaron.menu.MenuWithInventory
import dev.aaronhowser.mods.aaron.menu.components.FilteredSlot
import dev.aaronhowser.mods.irregular_implements.block_entity.FilteredRedirectorPlateBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class FilteredRedirectorPlateMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val filterContainer: Container
) : MenuWithInventory(ModMenuTypes.FILTERED_REDIRECTOR_PLATE.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(FilteredRedirectorPlateBlockEntity.CONTAINER_SIZE)
			)

	init {
		checkContainerSize(filterContainer, FilteredRedirectorPlateBlockEntity.CONTAINER_SIZE)
		filterContainer.startOpen(playerInventory.player)
		addSlots(47)
	}

	override fun addContainerSlots() {
		val isEntityFilter: (ItemStack) -> Boolean = { it.has(ModDataComponents.ENTITY_TYPE) }
		addSlot(FilteredSlot(filterContainer, 0, 62, 10, isEntityFilter))
		addSlot(FilteredSlot(filterContainer, 1, 98, 10, isEntityFilter))
	}

	override fun quickMoveStack(player: Player, clickedSlotIndex: Int): ItemStack {
		val slot = slots.getOrNull(clickedSlotIndex) ?: return ItemStack.EMPTY
		if (!slot.hasItem()) return ItemStack.EMPTY

		val original = slot.item.copy()
		val stack = slot.item

		val moved = if (clickedSlotIndex < FilteredRedirectorPlateBlockEntity.CONTAINER_SIZE) {
			moveItemStackTo(stack, FilteredRedirectorPlateBlockEntity.CONTAINER_SIZE, slots.size, true)
		} else {
			moveItemStackTo(stack, 0, FilteredRedirectorPlateBlockEntity.CONTAINER_SIZE, false)
		}

		if (!moved) return ItemStack.EMPTY

		if (stack.isEmpty) slot.setByPlayer(ItemStack.EMPTY) else slot.setChanged()
		slot.onTake(player, stack)
		return original
	}

	override fun stillValid(player: Player): Boolean = filterContainer.stillValid(player)

}
