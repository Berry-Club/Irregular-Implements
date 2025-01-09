package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class IronDropperMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val dispenserContainer: Container,
    private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.IRON_DROPPER.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) : this(containerId, playerInventory, SimpleContainer(9), SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE))

    init {
        checkContainerSize(dispenserContainer, 9)
        dispenserContainer.startOpen(playerInventory.player)

        // Add the 9 slots of the dispenser
        for (i in 0..2) {
            for (j in 0..2) {
                this.addSlot(Slot(dispenserContainer, j + i * 3, 62 + j * 18, 17 + i * 18))
            }
        }

        // Add the 27 slots of the player inventory
        for (k in 0..2) {
            for (i1 in 0..8) {
                this.addSlot(Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18))
            }
        }

        // Add the 9 slots of the player hotbar
        for (l in 0..8) {
            this.addSlot(Slot(playerInventory, l, 8 + l * 18, 142))
        }
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val slot = slots.getOrNull(index)

        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY

        val stackThere = slot.item
        val copyStack = stackThere.copy()

        if (index < 9) {
            if (!this.moveItemStackTo(stackThere, 9, 45, true)) {
                return ItemStack.EMPTY
            }
        } else if (!this.moveItemStackTo(stackThere, 0, 9, false)) {
            return ItemStack.EMPTY
        }

        if (stackThere.isEmpty) {
            slot.setByPlayer(ItemStack.EMPTY)
        } else {
            slot.setChanged()
        }

        if (stackThere.count == copyStack.count) return ItemStack.EMPTY

        slot.onTake(player, stackThere)

        return copyStack
    }

    override fun stillValid(player: Player): Boolean {
        return dispenserContainer.stillValid(player)
    }

}