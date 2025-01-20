package dev.aaronhowser.mods.irregular_implements.menu.base

import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack
import java.util.function.Supplier

open class FilterSlot(
    private val filterStack: Supplier<ItemStack>,
    private val lookupProvider: HolderLookup.Provider,
    slotIndex: Int,
    x: Int,
    y: Int
) : NonInteractiveResultSlot(SimpleContainer(9), slotIndex, x, y) {

    private val stackComponent: ItemFilterDataComponent?
        get() = this.filterStack.get().get(ModDataComponents.ITEM_FILTER_ENTRIES)

    private val stackFilter: NonNullList<FilterEntry>?
        get() = stackComponent?.entries

    private val entryInThisSlot: FilterEntry?
        get() = stackFilter?.getOrNull(this.index)

    override fun getItem(): ItemStack {
        return entryInThisSlot?.getDisplayStack(lookupProvider) ?: ItemStack.EMPTY
    }

    // Treating this as basically a button that removes this slot's entry from the filter component
    override fun mayPickup(player: Player): Boolean {
        val stackFilter = stackFilter ?: return false

        val newFilter = stackFilter.toMutableList()
        newFilter[this.index] = FilterEntry.Empty

        filterStack.get().set(
            ModDataComponents.ITEM_FILTER_ENTRIES,
            ItemFilterDataComponent(
                *newFilter.toTypedArray(),
                isBlacklist = stackComponent?.isBlacklist ?: false
            )
        )

        return false
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

}