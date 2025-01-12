package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
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
) : AbstractContainerMenu(ModMenuTypes.IRON_DROPPER.get(), containerId), MenuWithButtons {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                SimpleContainer(9),
                SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE)
            )

    init {
        checkContainerSize(dispenserContainer, 9)
        dispenserContainer.startOpen(playerInventory.player)

        // Add the 9 slots of the dispenser
        for (row in 0..2) {
            for (column in 0..2) {
                val slotIndex = column + row * 3
                val x = 62 + column * 18
                val y = 17 + row * 18

                this.addSlot(Slot(dispenserContainer, slotIndex, x, y))
            }
        }

        // Add the 27 slots of the player inventory
        for (row in 0..2) {
            for (column in 0..8) {
                val slotIndex = column + row * 9 + 9
                val x = 8 + column * 18
                val y = 84 + row * 18

                this.addSlot(Slot(playerInventory, slotIndex, x, y))
            }
        }

        // Add the 9 slots of the player hotbar
        for (hotbarIndex in 0..8) {
            val x = 8 + hotbarIndex * 18
            val y = 142

            this.addSlot(Slot(playerInventory, hotbarIndex, x, y))
        }

        this.addDataSlots(containerData)
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

    var shouldShootStraight: Boolean
        get() = containerData.get(IronDropperBlockEntity.SHOOT_STRAIGHT_INDEX) != 0
        private set(value) = containerData.set(IronDropperBlockEntity.SHOOT_STRAIGHT_INDEX, if (value) 1 else 0)

    var shouldHaveEffects: IronDropperBlockEntity.EffectsMode
        get() = IronDropperBlockEntity.EffectsMode.entries[containerData.get(IronDropperBlockEntity.EFFECTS_MODE_INDEX)]
        private set(value) = containerData.set(IronDropperBlockEntity.EFFECTS_MODE_INDEX, value.ordinal)

    var pickupDelay: IronDropperBlockEntity.PickupDelay
        get() = IronDropperBlockEntity.PickupDelay.entries[containerData.get(IronDropperBlockEntity.PICKUP_DELAY_INDEX)]
        private set(value) = containerData.set(IronDropperBlockEntity.PICKUP_DELAY_INDEX, value.ordinal)

    var redstoneMode: IronDropperBlockEntity.RedstoneMode
        get() = IronDropperBlockEntity.RedstoneMode.entries[containerData.get(IronDropperBlockEntity.REDSTONE_MODE_INDEX)]
        private set(value) = containerData.set(IronDropperBlockEntity.REDSTONE_MODE_INDEX, value.ordinal)

    // Only called from the server
    override fun handleButtonPressed(buttonId: Int) {
        when (buttonId) {
            SHOOT_MODE_BUTTON_ID -> this.shouldShootStraight = !this.shouldShootStraight
            EFFECTS_BUTTON_ID -> this.shouldHaveEffects = this.shouldHaveEffects.next()
            DELAY_BUTTON_ID -> this.pickupDelay = this.pickupDelay.next()
            REDSTONE_BUTTON_ID -> this.redstoneMode = this.redstoneMode.next()
        }
    }

    companion object {
        const val SHOOT_MODE_BUTTON_ID = 0
        const val EFFECTS_BUTTON_ID = 1
        const val DELAY_BUTTON_ID = 2
        const val REDSTONE_BUTTON_ID = 3
    }

}