package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class FilteredPlatformMenu(
    containerId: Int,
    playerInventory: Inventory,
    private val platformContainer: Container
) : AbstractContainerMenu(ModMenuTypes.FILTERED_PLATFORM.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                SimpleContainer(1)
            )

    init {
        checkContainerSize(platformContainer, 1)

        platformContainer.startOpen(playerInventory.player)

        for (row in 0..2) {
            for (column in 0..8) {
                val inventorySlotIndex = column + row * 9 + 9

                val x = 8 + column * 18
                val y = 47 + row * 18

                this.addSlot(Slot(playerInventory, inventorySlotIndex, x, y))
            }
        }

        for (hotbarSlotIndex in 0..8) {
            val x = 8 + hotbarSlotIndex * 18
            val y = 105

            this.addSlot(Slot(playerInventory, hotbarSlotIndex, x, y))
        }

    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean {
        return platformContainer.stillValid(player)
    }

}