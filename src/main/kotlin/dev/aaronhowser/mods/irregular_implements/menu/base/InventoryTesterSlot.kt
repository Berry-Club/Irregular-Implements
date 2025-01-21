package dev.aaronhowser.mods.irregular_implements.menu.base

import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack

class InventoryTesterSlot(
    private val inventoryTesterContainer: Container,
    x: Int,
    y: Int
) : NonInteractiveResultSlot(SimpleContainer(0), 0, x, y) {

    var stack: ItemStack
        get() = this.inventoryTesterContainer.getItem(0)
        set(value) {
            this.inventoryTesterContainer.setItem(0, value)
        }

    override fun mayPickup(player: Player): Boolean {
        this.stack = ItemStack.EMPTY

        return false
    }

    override fun safeInsert(stack: ItemStack): ItemStack {
        this.stack = stack.copyWithCount(1)

        return stack
    }

    override fun getItem(): ItemStack {
        return this.stack
    }

    override fun remove(amount: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun set(stack: ItemStack) {
        // Do nothing
    }

    override fun isHighlightable(): Boolean {
        return true
    }

    override fun getSlotIndex(): Int {
        return 0
    }

}