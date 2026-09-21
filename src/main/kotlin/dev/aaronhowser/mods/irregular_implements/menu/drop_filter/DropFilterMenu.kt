package dev.aaronhowser.mods.irregular_implements.menu.drop_filter

import dev.aaronhowser.mods.aaron.menu.HeldItemMenu
import dev.aaronhowser.mods.aaron.menu.components.ContainerSlot
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponents
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.InteractionHand
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents

class DropFilterMenu(
	containerId: Int,
	playerInventory: Inventory,
	usedHand: InteractionHand
) : HeldItemMenu(
	ModMenuTypes.DROP_FILTER.get(),
	containerId,
	playerInventory,
	usedHand
) {

	constructor(containerId: Int, playerInventory: Inventory, data: RegistryFriendlyByteBuf) :
			this(containerId, playerInventory, data.readEnum(InteractionHand::class.java))

	private fun getHeldItemStack(): ItemStack = playerInventory.player.getItemInHand(usedHand)

	override fun isValidHeldItem(heldItem: ItemStack): Boolean = heldItem.isItem(ModItems.DROP_FILTER)

	val container: ItemContainerContents?
		get() = getHeldItemStack().get(DataComponents.CONTAINER)

	val filterContainer: SimpleContainer = DropFilterContainer()

	init {
		addSlots(51)
	}

	override fun addContainerSlots() {
		val filterSlot = DropFilterSlot(80, 18)
		this.addSlot(filterSlot)
	}

	private inner class DropFilterContainer : SimpleContainer(1) {

		override fun canPlaceItem(slot: Int, stack: ItemStack): Boolean {
			return stack.has(ModDataComponents.ITEM_FILTER)
		}

		override fun getItems(): NonNullList<ItemStack> {
			val items = NonNullList.withSize(1, ItemStack.EMPTY)
			val container = this@DropFilterMenu.container ?: return items
			val nonEmptyItems = container.nonEmptyItems().toList()

			for (index in nonEmptyItems.indices) {
				items[index] = nonEmptyItems[index]
			}

			return items
		}

		override fun getItem(index: Int): ItemStack {
			return getItems()[index]
		}

		override fun removeItem(index: Int, count: Int): ItemStack {
			val container = container ?: return ItemStack.EMPTY

			if (index !in 0 until container.slots) return ItemStack.EMPTY

			val stack = container.getStackInSlot(index).copy()

			getHeldItemStack().set(
				DataComponents.CONTAINER,
				ItemContainerContents.EMPTY
			)

			return stack
		}

			override fun addItem(stack: ItemStack): ItemStack {
			getHeldItemStack().set(
				DataComponents.CONTAINER,
				ItemContainerContents.fromItems(listOf(stack))
			)

			return ItemStack.EMPTY
		}
	}

	private inner class DropFilterSlot(x: Int, y: Int) : ContainerSlot(filterContainer, 0, x, y) {

		override fun set(stack: ItemStack) {
			filterContainer.addItem(stack)
		}

	}

}