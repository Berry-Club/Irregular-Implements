package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ImbuingStationBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class ImbuingStationMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val imbuingStationContainer: Container
) : AbstractContainerMenu(ModMenuTypes.IMBUING_STATION.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                SimpleContainer(ImbuingStationBlockEntity.CONTAINER_SIZE)
            )

    init {
        checkContainerSize(imbuingStationContainer, 4)

        val topSlot = Slot(
            imbuingStationContainer,
            ImbuingStationBlockEntity.TOP_SLOT_INDEX,
            80,
            9
        )
        this.addSlot(topSlot)

        val leftSlot = Slot(
            imbuingStationContainer,
            ImbuingStationBlockEntity.LEFT_SLOT_INDEX,
            35,
            54
        )
        this.addSlot(leftSlot)

        val middleSlot = Slot(
            imbuingStationContainer,
            ImbuingStationBlockEntity.CENTER_SLOT_INDEX,
            80,
            54
        )
        this.addSlot(middleSlot)

        val bottomSlot = Slot(
            imbuingStationContainer,
            ImbuingStationBlockEntity.BOTTOM_SLOT_INDEX,
            80,
            99
        )
        this.addSlot(bottomSlot)

        val outputSlot = Slot(
            imbuingStationContainer,
            ImbuingStationBlockEntity.OUTPUT_SLOT_INDEX,
            125,
            54
        )
        this.addSlot(outputSlot)

        // Add the 27 slots of the player inventory
        for (row in 0..2) {
            for (column in 0..8) {
                val slotIndex = column + row * 9 + 9
                val x = 8 + column * 18
                val y = 126 + row * 18

                this.addSlot(Slot(playerInventory, slotIndex, x, y))
            }
        }

        // Add the 9 slots of the player hotbar
        for (hotbarIndex in 0..8) {
            val x = 8 + hotbarIndex * 18
            val y = 184

            this.addSlot(Slot(playerInventory, hotbarIndex, x, y))
        }

    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return imbuingStationContainer.stillValid(player)
    }
}