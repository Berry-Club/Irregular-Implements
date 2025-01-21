package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.InventoryTesterSlot
import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class InventoryTesterMenu(
    containerId: Int,
    playerInventory: Inventory,
    private val containerLevelAccess: ContainerLevelAccess
) : AbstractContainerMenu(ModMenuTypes.INVENTORY_TESTER.get(), containerId), MenuWithButtons {

    constructor(containerId: Int, playerInventory: Inventory) : this(containerId, playerInventory, ContainerLevelAccess.NULL)

    init {
        val slot = InventoryTesterSlot(this.containerLevelAccess, 64, 18)
        this.addSlot(slot)

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
        return stillValid(this.containerLevelAccess, player, ModBlocks.INVENTORY_TESTER.get())
    }

    override fun handleButtonPressed(buttonId: Int) {
        TODO("Not yet implemented")
    }

}