package dev.aaronhowser.mods.irregular_implements.handler.ender_letter

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.ContainerHelper
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler

class EnderMailboxInventory(
	val enderLetterHandler: EnderLetterHandler
) : IItemHandler {

	val enderLetters: NonNullList<ItemStack> = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY)

	fun hasItems(): Boolean = enderLetters.any { !it.isEmpty }
	fun hasRoom(): Boolean = enderLetters.any(ItemStack::isEmpty)

	override fun getStackInSlot(slot: Int): ItemStack {
		return if (slot in 0 until INVENTORY_SIZE) {
			enderLetters[slot]
		} else {
			ItemStack.EMPTY
		}
	}

	override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
		if (!isItemValid(slot, stack) || !getStackInSlot(slot).isEmpty) return stack

		if (!simulate) {
			enderLetters[slot] = stack
			enderLetterHandler.setDirty()
		}

		return ItemStack.EMPTY
	}

	override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
		if (amount == 0) return ItemStack.EMPTY

		val existing = getStackInSlot(slot)
		if (existing.isEmpty) return ItemStack.EMPTY

		if (!simulate) {
			enderLetters[slot] = ItemStack.EMPTY
			enderLetterHandler.setDirty()
		}

		return existing
	}

	override fun getSlots(): Int = enderLetters.size
	override fun getSlotLimit(slot: Int): Int = 1

	override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
		return stack.isItem(ModItems.ENDER_LETTER)
	}

	fun saveAsTag(registry: HolderLookup.Provider): CompoundTag {
		val tag = CompoundTag()
		ContainerHelper.saveAllItems(tag, enderLetters, registry)
		return tag
	}

	companion object {
		const val INVENTORY_SIZE = 9

		fun loadFromTag(handler: EnderLetterHandler, tag: CompoundTag, registry: HolderLookup.Provider): EnderMailboxInventory {
			val inventory = EnderMailboxInventory(handler)

			ContainerHelper.loadAllItems(tag, inventory.enderLetters, registry)

			return inventory
		}
	}

}