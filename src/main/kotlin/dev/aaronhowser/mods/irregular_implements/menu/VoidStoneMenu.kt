package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack

class VoidStoneMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val container: Container,
) : AbstractContainerMenu(ModMenuTypes.VOID_STONE.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                SimpleContainer(1),
            )

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY  // Deletes the item?
    }

    override fun stillValid(player: Player): Boolean {
        return playerInventory.getSelected().`is`(ModItems.VOID_STONE)
    }
}