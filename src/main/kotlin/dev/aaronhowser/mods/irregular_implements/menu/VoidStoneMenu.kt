package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class VoidStoneMenu(
    containerId: Int,
    private val playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.VOID_STONE.get(), containerId) {

    private val temporaryContainer = SimpleContainer(1)

    init {
        val voidSlotX = 80
        val voidSlotY = 18

        //TODO: Figure out how to make it only void the item in the slot when you place another item into it
        val voidSlot = object : Slot(temporaryContainer, 0, voidSlotX, voidSlotY) {
            override fun set(stack: ItemStack) {
                // Do nothing (voids the item)
            }
        }

        this.addSlot(voidSlot)

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
        if (index !in 1..36) return ItemStack.EMPTY

        val slot = slots.getOrNull(index)

        if (slot != null && slot.hasItem()) {
            val stackInSlot = slot.item

            if (stackInSlot != this.playerInventory.getSelected()) {
                this.moveItemStackTo(stackInSlot, 0, 1, false)
            }
        }

        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return playerInventory.getSelected().`is`(ModItems.VOID_STONE)
    }
}