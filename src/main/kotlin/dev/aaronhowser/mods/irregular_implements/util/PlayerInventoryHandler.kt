package dev.aaronhowser.mods.irregular_implements.util

import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler

// Inspired largely by PneumaticCraft's PlayerInvHandler, used by its Aerial Interface
sealed class PlayerInventoryHandler : IItemHandler {

    protected var cached: IItemHandler? = null

    fun invalidate() {
        this.cached = null
    }

    fun getCachedHandler(player: Player, function: (Inventory) -> IItemHandler): IItemHandler {
        if (this.cached == null) this.cached = function.invoke(player.inventory)
        return this.cached!!
    }

    abstract fun getWrapper(player: Player): IItemHandler

    override fun getSlots(): Int {
        return getWrapper(player).slots
    }

    override fun getSlotLimit(slot: Int): Int {
        return getWrapper(player).getSlotLimit(slot)
    }

    override fun getStackInSlot(slot: Int): ItemStack {
        return getWrapper(player).getStackInSlot(slot)
    }

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        return getWrapper(player).insertItem(slot, stack, simulate)
    }

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
        return getWrapper(player).extractItem(slot, amount, simulate)
    }

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        return getWrapper(player).isItemValid(slot, stack)
    }

    class MainInventoryHandler(player: Player) : PlayerInventoryHandler(player) {
        override fun getWrapper(player: Player): IItemHandler {
            return getCachedHandler(player, ::MainInventoryHandler)
        }
    }


}