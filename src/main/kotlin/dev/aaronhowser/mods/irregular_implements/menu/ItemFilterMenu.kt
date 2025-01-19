package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.NonNullList
import net.minecraft.world.InteractionHand
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class ItemFilterMenu(
    containerId: Int,
    playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.ITEM_FILTER.get(), containerId), MenuWithButtons {

    private val filterStack: ItemStack =
        if (playerInventory.player.mainHandItem.`is`(ModItems.ITEM_FILTER.get())) {
            playerInventory.player.mainHandItem
        } else {
            playerInventory.player.offhandItem
        }

    private var usingMainHand = playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === filterStack

    private val filterComponent: ItemFilterDataComponent?
        get() = filterStack.get(ModDataComponents.ITEM_FILTER_ENTRIES)

    val filter: Set<ItemFilterDataComponent.FilterEntry>?
        get() = filterComponent?.entries

    var isBlacklist: Boolean
        get() = filterComponent?.isBlacklist.isTrue
        private set(value) {
            if (value == this.isBlacklist) return

            val filterComponent = this.filterComponent ?: return

            filterStack.set(
                ModDataComponents.ITEM_FILTER_ENTRIES,
                filterComponent.copy(isBlacklist = value)
            )
        }

    val filterContainer = object : SimpleContainer(9) {
        override fun getItems(): NonNullList<ItemStack> {
            val items = NonNullList.withSize(9, ItemStack.EMPTY)
            val filter: Set<ItemFilterDataComponent.FilterEntry> = this@ItemFilterMenu.filter ?: return items

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
            val filter = this@ItemFilterMenu.filter ?: return ItemStack.EMPTY
            val entry = filter.elementAtOrNull(index) ?: return ItemStack.EMPTY

            val newFilter = filter.toMutableSet()
            newFilter.remove(entry)

            filterStack.set(
                ModDataComponents.ITEM_FILTER_ENTRIES,
                ItemFilterDataComponent(newFilter, this@ItemFilterMenu.filterComponent!!.isBlacklist)
            )

            return ItemStack.EMPTY
        }

        override fun addItem(addedStack: ItemStack): ItemStack {
            val component = this@ItemFilterMenu.filterComponent ?: return addedStack
            if (!component.canAddFilter(addedStack)) return addedStack

            val filter = component.entries

            val newFilterEntry = ItemFilterDataComponent.FilterEntry.SpecificItem(addedStack, requireSameComponents = false)

            val newFilter = filter.toMutableSet()
            newFilter.add(newFilterEntry)

            filterStack.set(
                ModDataComponents.ITEM_FILTER_ENTRIES,
                ItemFilterDataComponent(newFilter, this@ItemFilterMenu.filterComponent!!.isBlacklist)
            )

            return ItemStack.EMPTY
        }

    }

    init {

        for (index in 0 until 9) {
            val x = 8 + index * 18
            val y = 26

            val slot = object : Slot(this.filterContainer, index, x, y) {

                override fun isFake(): Boolean {
                    return true
                }

                override fun mayPickup(player: Player): Boolean {
                    this.container.removeItem(this.index, 1)
                    return false
                }

                override fun mayPlace(stack: ItemStack): Boolean {
                    this@ItemFilterMenu.filterContainer.addItem(stack.copyWithCount(1))

                    return false
                }

            }

            this.addSlot(slot)
        }

        // Add the 27 slots of the player inventory
        for (row in 0..2) {
            for (column in 0..8) {
                val slotIndex = column + row * 9 + 9
                val x = 8 + column * 18
                val y = 59 + row * 18

                this.addSlot(Slot(playerInventory, slotIndex, x, y))
            }
        }

        // Add the 9 slots of the player hotbar
        for (hotbarIndex in 0..8) {
            val x = 8 + hotbarIndex * 18
            val y = 117

            this.addSlot(Slot(playerInventory, hotbarIndex, x, y))
        }

    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return player.getItemInHand(
            if (usingMainHand) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND
        ).`is`(ModItems.ITEM_FILTER)
    }

    override fun handleButtonPressed(buttonId: Int) {

        when (buttonId) {
            TOGGLE_BLACKLIST_BUTTON_ID -> this.isBlacklist = !this.isBlacklist
        }

    }

    companion object {

        const val TOGGLE_BLACKLIST_BUTTON_ID = 0

    }

}