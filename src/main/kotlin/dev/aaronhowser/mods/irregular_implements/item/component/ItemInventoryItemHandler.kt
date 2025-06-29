package dev.aaronhowser.mods.irregular_implements.item.component

import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponentType
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.ItemStackHandler

open class ItemInventoryItemHandler<T>(
	val stack: ItemStack,
	val dataComponentType: DataComponentType<T>
) : ItemStackHandler(stack.get(dataComponentType)?.getInventory() ?: NonNullList.create())
		where T : ItemInventoryItemHandler.InventoryDataComponent {

	init {
		require(this.stack.has(dataComponentType)) { "ItemInventoryItemHandler created for stack that does not have the required data component" }
	}

	interface InventoryDataComponent {
		fun getType(): DataComponentType<*>
		fun getInventory(): NonNullList<ItemStack>
		fun setInventory(stack: ItemStack, inventory: NonNullList<ItemStack>)
	}

	val copiedStacks: NonNullList<ItemStack> = NonNullList.withSize(stacks.size, ItemStack.EMPTY)

	init {
		for (i in stacks.indices) {
			copiedStacks[i] = stacks[i].copy()
		}
	}

	override fun getSlots(): Int = copiedStacks.size
	override fun getStackInSlot(slot: Int): ItemStack = copiedStacks[slot]
	override fun setStackInSlot(slot: Int, stack: ItemStack) {
		validateSlotIndex(slot)
		copiedStacks[slot] = stack
		onContentsChanged(slot)
	}

	override fun insertItem(
		slot: Int,
		stack: ItemStack,
		simulate: Boolean
	): ItemStack {
		if (stack.isEmpty) return ItemStack.EMPTY
		if (!isItemValid(slot, stack)) return stack

		validateSlotIndex(slot)

		val existing = getStackInSlot(slot)
		var limit = getStackLimit(slot, stack)

		if (!existing.isEmpty) {
			if (!ItemStack.isSameItemSameComponents(stack, existing)) return stack

			limit -= existing.count
		}

		if (limit <= 0) return stack

		val reachedLimit = stack.count > limit

		if (!simulate) {
			if (existing.isEmpty) {
				copiedStacks[slot] = if (reachedLimit) stack.copyWithCount(limit) else stack
			} else {
				existing.grow(if (reachedLimit) limit else stack.count)
			}

			onContentsChanged(slot)
		}

		return if (reachedLimit) stack.copyWithCount(stack.count - limit) else ItemStack.EMPTY
	}

	override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
		if (amount == 0) return ItemStack.EMPTY
		validateSlotIndex(slot)

		val existing = getStackInSlot(slot)
		if (existing.isEmpty) return ItemStack.EMPTY

		val toExtract = minOf(amount, existing.maxStackSize)

		if (existing.count <= toExtract) {
			if (!simulate) {
				setStackInSlot(slot, ItemStack.EMPTY)
				onContentsChanged(slot)
				return existing
			} else {
				return existing.copy()
			}
		} else {
			if (!simulate) {
				copiedStacks[slot] = existing.copyWithCount(existing.count - toExtract)
				onContentsChanged(slot)
			}
			return existing.copyWithCount(toExtract)
		}
	}

	override fun onContentsChanged(slot: Int) {
		super.onContentsChanged(slot)
		stack.get(dataComponentType)?.setInventory(stack, NonNullList.copyOf(copiedStacks))
	}

}