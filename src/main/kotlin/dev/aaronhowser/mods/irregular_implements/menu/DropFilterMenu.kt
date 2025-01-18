package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.item.DropFilterItem
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterEntryListDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
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
    playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.DROP_FILTER.get(), containerId) {

    private val filterStack: ItemStack =
        if (DropFilterItem.stackIsFilter(playerInventory.player.mainHandItem)) {
            playerInventory.player.mainHandItem
        } else {
            playerInventory.player.offhandItem
        }

    private var usingMainHand = playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === filterStack

    val container: ItemContainerContents?
        get() = filterStack.get(DataComponents.CONTAINER)

    val filter: Set<ItemFilterEntryListDataComponent.FilterEntry>?
        get() = container
            ?.getStackInSlot(0)
            ?.get(ModDataComponents.ITEM_FILTER_ENTRIES)
            ?.entries

    val filterContainer = object : SimpleContainer(9) {
        override fun getItems(): NonNullList<ItemStack> {
            val items = NonNullList.withSize(9, ItemStack.EMPTY)
            val filter: Set<ItemFilterEntryListDataComponent.FilterEntry> = this@DropFilterMenu.filter ?: return items

            for (index in 0 until 9) {
                val entry = filter.elementAtOrNull(index) ?: continue
                items[index] = entry.getDisplayStack()
            }

            return items
        }

        override fun getItem(index: Int): ItemStack {
            return getItems()[index]
        }

        override fun removeItem(index: Int, count: Int): ItemStack {
            val filter = this@DropFilterMenu.filter ?: return ItemStack.EMPTY
            val entry = filter.elementAtOrNull(index) ?: return ItemStack.EMPTY

            val newFilter = filter.toMutableSet()
            newFilter.remove(entry)

            filterStack.set(
                ModDataComponents.ITEM_FILTER_ENTRIES,
                ItemFilterEntryListDataComponent(newFilter)
            )

            return ItemStack.EMPTY
        }

        override fun addItem(stack: ItemStack): ItemStack {
            val filter = this@DropFilterMenu.filter ?: return stack

            val newFilterEntry = ItemFilterEntryListDataComponent.FilterEntry.SpecificItem(stack, requireSameComponents = false)

            val newFilter = filter.toMutableSet()
            newFilter.add(newFilterEntry)

            filterStack.set(
                ModDataComponents.ITEM_FILTER_ENTRIES,
                ItemFilterEntryListDataComponent(newFilter)
            )

            return ItemStack.EMPTY
        }
    }

    init {

        val filterX = 80
        val filterY = 18

        val filterSlot = object : Slot(this.filterContainer, 0, filterX, filterY) {

            override fun isFake(): Boolean {
                return true
            }

            override fun mayPickup(player: Player): Boolean {
                this.container.removeItem(this.index, 1)
                return false
            }

            override fun mayPlace(stack: ItemStack): Boolean {
                this@DropFilterMenu.filterContainer.addItem(stack.copyWithCount(1))

                return false
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
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        val itemInHand = player.getItemInHand(
            if (usingMainHand) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND
        )

        return itemInHand === filterStack
    }
}