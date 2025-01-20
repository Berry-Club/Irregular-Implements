package dev.aaronhowser.mods.irregular_implements.menu.base

import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.world.Container
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class ItemFilterContainer(
    val filterStack: ItemStack,
    val lookupProvider: HolderLookup.Provider
) : Container {

    val filterComponent: ItemFilterDataComponent?
        get() = filterStack.get(ModDataComponents.ITEM_FILTER_ENTRIES)

    val filterEntries: NonNullList<FilterEntry>?
        get() = this.filterComponent?.entries

    override fun clearContent() {
        this.filterEntries?.clear()
    }

    override fun getContainerSize(): Int {
        return 9
    }

    override fun isEmpty(): Boolean {
        val filterEntries = this.filterEntries ?: return true

        return filterEntries.all { it is FilterEntry.Empty }
    }

    override fun getItem(slot: Int): ItemStack {
        return this.filterEntries
            ?.getOrNull(slot)
            ?.getDisplayStack(this.lookupProvider)
            ?: ItemStack.EMPTY
    }

    override fun removeItem(slot: Int, amount: Int): ItemStack {
        return removeItemNoUpdate(slot)
    }

    override fun removeItemNoUpdate(slot: Int): ItemStack {
        val filterEntries = this.filterEntries ?: return ItemStack.EMPTY
        val removedFilter = filterEntries.removeAt(slot)

        return removedFilter.getDisplayStack(this.lookupProvider)
    }

    override fun setItem(slot: Int, stack: ItemStack) {
        val filterEntries = this.filterEntries ?: return

        filterEntries[slot] = FilterEntry.Item(stack, false)
    }

    override fun setChanged() {
        // Do nothing
    }

    override fun stillValid(player: Player): Boolean {
        return player.getItemInHand(InteractionHand.MAIN_HAND) === filterStack
                || player.getItemInHand(InteractionHand.OFF_HAND) === filterStack
    }
}