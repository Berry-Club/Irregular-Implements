package dev.aaronhowser.mods.irregular_implements.menu.block_replacer

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class BlockReplacerMenu(
    menuType: MenuType<*>,
    containerId: Int
) : AbstractContainerMenu(menuType, containerId) {

    constructor(id: Int, inventory: Inventory, extraData: RegistryFriendlyByteBuf) : this(
        ModMenuTypes.BLOCK_REPLACER.get(),
        id
    )

    val topLeftInventoryX = 8
    val topLeftInventoryY = 90

    fun addPlayerInventory(playerInventory: Inventory) {
        for (row in 0 until 3) {
            for (column in 0 until 9) {
                addSlot(
                    Slot(
                        playerInventory,
                        column + row * 9 + 9,
                        topLeftInventoryX + column * 18,
                        topLeftInventoryY + row * 18
                    )
                )
            }
        }
    }

    fun addPlayerHotbar(playerInventory: Inventory) {
        for (column in 0 until 9) {
            addSlot(
                Slot(
                    playerInventory,
                    column,
                    topLeftInventoryX + column * 18,
                    topLeftInventoryY + 4 + 3 * 18
                )
            )
        }
    }

    companion object {
        // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
        // must assign a slot number to each of the slots used by the GUI.
        // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
        // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
        //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
        //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
        //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
        private const val HOTBAR_SLOT_COUNT = 9
        private const val PLAYER_INVENTORY_ROW_COUNT = 3
        private const val PLAYER_INVENTORY_COLUMN_COUNT = 9
        private const val PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT
        private const val VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT
        private const val VANILLA_FIRST_SLOT_INDEX = 0
        private const val TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT
    }

    private val amountSlots = 9

    override fun quickMoveStack(playerIn: Player, index: Int): ItemStack {
        val sourceSlot = slots.getOrNull(index)
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY //EMPTY_ITEM
        val sourceStack = sourceSlot.item
        val copyOfSourceStack = sourceStack.copy()

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(
                    sourceStack,
                    TE_INVENTORY_FIRST_SLOT_INDEX,
                    TE_INVENTORY_FIRST_SLOT_INDEX + amountSlots,
                    false
                )
            ) {
                return ItemStack.EMPTY // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + amountSlots) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(
                    sourceStack,
                    VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT,
                    false
                )
            ) {
                return ItemStack.EMPTY
            }
        } else {
            IrregularImplements.LOGGER.error("Invalid slotIndex: $index")
            return ItemStack.EMPTY
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.count == 0) {
            sourceSlot.set(ItemStack.EMPTY)
        } else {
            sourceSlot.setChanged()
        }
        sourceSlot.onTake(playerIn, sourceStack)
        return copyOfSourceStack
    }

    override fun stillValid(player: Player): Boolean {
        TODO("Not yet implemented")
    }
}