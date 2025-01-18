package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.item.DropFilterItem
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponents
import net.minecraft.world.InteractionHand
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents

class DropFilterMenu(
    containerId: Int,
    private val playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.DROP_FILTER.get(), containerId) {

    private val filterStack: ItemStack
        get() = if (DropFilterItem.stackIsDropFilter(playerInventory.player.mainHandItem)) {
            playerInventory.player.mainHandItem
        } else {
            playerInventory.player.offhandItem
        }

    private var usingMainHand = playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === filterStack

    val container: ItemContainerContents?
        get() = filterStack.get(DataComponents.CONTAINER)

    val filterContainer = object : SimpleContainer(1) {
        override fun getItems(): NonNullList<ItemStack> {
            val items = NonNullList.withSize(1, ItemStack.EMPTY)

            val container = this@DropFilterMenu.container

            container
                ?.nonEmptyItems()
                ?.forEachIndexed { index, stack ->
                    items[index] = stack
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

            filterStack.set(
                DataComponents.CONTAINER,
                ItemContainerContents.EMPTY
            )

            return stack
        }

        override fun addItem(stack: ItemStack): ItemStack {

            filterStack.set(
                DataComponents.CONTAINER,
                ItemContainerContents.fromItems(listOf(stack))
            )

            return ItemStack.EMPTY

        }
    }

    init {

        val filterX = 80
        val filterY = 18

        val filterSlot = object : Slot(this.filterContainer, 0, filterX, filterY) {

            override fun mayPlace(stack: ItemStack): Boolean {
                return stack.`is`(ModItems.ITEM_FILTER)
            }

            override fun set(stack: ItemStack) {
                this@DropFilterMenu.filterContainer.addItem(stack)
            }
        }

        this.addSlot(filterSlot)

        // Add the 27 slots of the player inventory
        for (row in 0..2) {
            for (column in 0..8) {
                val slotIndex = column + row * 9 + 9
                val x = 8 + column * 18
                val y = 51 + row * 18

                this.addSlot(Slot(playerInventory, slotIndex, x, y))
            }
        }

        // Add the 9 slots of the player hotbar
        for (hotbarIndex in 0..8) {
            val x = 8 + hotbarIndex * 18
            val y = 109

            this.addSlot(Slot(playerInventory, hotbarIndex, x, y))
        }
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val slot = slots.getOrNull(index)

        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY

        val stackThere = slot.item
        val copyStack = stackThere.copy()

        if (index == 0) {
            if (!this.moveItemStackTo(stackThere, 1, 37, true)) {
                return ItemStack.EMPTY
            }
        } else if (!this.moveItemStackTo(stackThere, 0, 1, false)) {
            return ItemStack.EMPTY
        }

        if (stackThere.isEmpty) {
            slot.setByPlayer(ItemStack.EMPTY)
        } else {
            slot.setChanged()
        }

        if (stackThere.count == copyStack.count) return ItemStack.EMPTY

        slot.onTake(player, stackThere)

        return copyStack
    }

    override fun stillValid(player: Player): Boolean {
        val itemInHand = player.getItemInHand(
            if (usingMainHand) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND
        )

        return itemInHand === filterStack
    }
}