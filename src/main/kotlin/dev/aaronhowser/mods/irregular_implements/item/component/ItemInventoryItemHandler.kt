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

	override fun onContentsChanged(slot: Int) {
		super.onContentsChanged(slot)
		dataComponent.setInventory(stack, this.stacks)
	}

}