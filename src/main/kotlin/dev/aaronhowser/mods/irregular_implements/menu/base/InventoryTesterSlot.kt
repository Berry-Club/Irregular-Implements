package dev.aaronhowser.mods.irregular_implements.menu.base

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack
import java.util.*

class InventoryTesterSlot(
    private val inventoryTesterContainer: Container,
    x: Int,
    y: Int
) : NonInteractiveResultSlot(inventoryTesterContainer, 0, x, y) {

    override fun mayPickup(player: Player): Boolean {
        return true
    }

    override fun tryRemove(count: Int, decrement: Int, player: Player): Optional<ItemStack> {
        this.inventoryTesterContainer.removeItem(0, count)

        return Optional.empty()
    }

    override fun safeInsert(stack: ItemStack): ItemStack {
        this.inventoryTesterContainer.setItem(0, stack.copyWithCount(1))

        return stack
    }

    override fun isHighlightable(): Boolean {
        return true
    }

    override fun getSlotIndex(): Int {
        return 0
    }

}