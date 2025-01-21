package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.InventoryTesterSlot
import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class InventoryTesterMenu(
    containerId: Int,
    playerInventory: Inventory,
    private val inventoryTesterContainer: Container
) : AbstractContainerMenu(ModMenuTypes.INVENTORY_TESTER.get(), containerId), MenuWithButtons {

    constructor(containerId: Int, playerInventory: Inventory) : this(containerId, playerInventory, SimpleContainer(1))

    init {
        val slot = InventoryTesterSlot(inventoryTesterContainer, 64, 18)
        this.addSlot(slot)

//        val realSlot = Slot(inventoryTesterContainer, 0, 80, 18)
//        this.addSlot(realSlot)

        // Add the 27 slots of the player inventory
        for (row in 0..2) {
            for (column in 0..8) {
                val slotIndex = column + row * 9 + 9
                val x = 8 + column * 18
                val y = 54 + row * 18

                this.addSlot(Slot(playerInventory, slotIndex, x, y))
            }
        }

        // Add the 9 slots of the player hotbar
        for (hotbarIndex in 0..8) {
            val x = 8 + hotbarIndex * 18
            val y = 112

            this.addSlot(Slot(playerInventory, hotbarIndex, x, y))
        }
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return this.inventoryTesterContainer.stillValid(player)
    }

    override fun handleButtonPressed(buttonId: Int) {
        TODO("Not yet implemented")
    }

}