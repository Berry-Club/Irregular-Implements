package dev.aaronhowser.mods.irregular_implements.menu.base

import dev.aaronhowser.mods.irregular_implements.block.block_entity.InventoryTesterBlockEntity
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack

class InventoryTesterSlot(
    containerLevelAccess: ContainerLevelAccess,
    x: Int,
    y: Int
) : NonInteractiveResultSlot(SimpleContainer(0), 0, x, y) {

    val blockEntity: InventoryTesterBlockEntity? =
        containerLevelAccess.evaluate { level, pos ->
            level.getBlockEntity(pos) as? InventoryTesterBlockEntity
        }.orElse(null)

    var stack: ItemStack
        get() = blockEntity?.itemStack ?: ItemStack.EMPTY
        set(value) {
            blockEntity?.itemStack = value
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