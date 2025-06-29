package dev.aaronhowser.mods.irregular_implements.item.component

import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponentType
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.ItemStackHandler

class ItemInventoryItemHandler<T>(
	val stack: ItemStack,
	val dataComponent: T
) : ItemStackHandler(dataComponent.getInventory())
		where T : ItemInventoryItemHandler.InventoryDataComponent {

	init {
		require(this.stack.has(dataComponent.getType())) { "ItemInventoryItemHandler created for stack that does not have the required data component" }
	}

	interface InventoryDataComponent {
		fun getType(): DataComponentType<*>
		fun getInventory(): NonNullList<ItemStack>
		fun setInventory(stack: ItemStack, inventory: NonNullList<ItemStack>)
	}

	override fun insertItem(
		slot: Int,
		stack: ItemStack,
		simulate: Boolean
	): ItemStack {
		if (stack.isEmpty) return ItemStack.EMPTY
		if (!isItemValid(slot, stack)) return stack

		validateSlotIndex(slot)

		val stackThere = this.stacks[slot]
		var limit = getStackLimit(slot, stack)

		if (!stackThere.isEmpty) {
			if (!ItemStack.isSameItemSameComponents(stack, stackThere)) return stack

			limit -= stackThere.count
		}

		if (limit <= 0) return stack

		val reachedLimit = stack.count > limit

		if (!simulate) {
			val newStacks = NonNullList.copyOf(this.stacks)

			if (stackThere.isEmpty) {
				newStacks[slot] = if (reachedLimit) stack.copyWithCount(limit) else stack
			} else {
				stackThere.grow(if (reachedLimit) limit else stack.count)
				newStacks[slot] = stackThere
			}

		}

	}

	override fun onContentsChanged(slot: Int) {
		super.onContentsChanged(slot)
		dataComponent.setInventory(stack, this.stacks)
	}

}