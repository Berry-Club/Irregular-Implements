package dev.aaronhowser.mods.irregular_implements.menu.base

import net.minecraft.world.Container
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack

class InventoryTesterSlot(
    private val inventoryTesterContainer: Container,
    x: Int,
    y: Int
) : NonInteractiveResultSlot(inventoryTesterContainer, 0, x, y) {

    override fun remove(amount: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun safeInsert(stack: ItemStack): ItemStack {
        inventoryTesterContainer.setItem(0, stack.copyWithCount(1))

        return stack
    }

    override fun isHighlightable(): Boolean {
        return true
    }

    override fun getSlotIndex(): Int {
        return 0
    }

}