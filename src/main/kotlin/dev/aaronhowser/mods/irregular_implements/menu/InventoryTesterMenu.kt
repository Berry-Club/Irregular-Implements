package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack

class InventoryTesterMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val containerLevelAccess: ContainerLevelAccess
) : AbstractContainerMenu(ModMenuTypes.INVENTORY_TESTER.get(), containerId), MenuWithButtons {

    constructor(containerId: Int, playerInventory: Inventory) : this(containerId, playerInventory, ContainerLevelAccess.NULL)

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(this.containerLevelAccess, player, ModBlocks.INVENTORY_TESTER.get())
    }

    override fun handleButtonPressed(buttonId: Int) {
        TODO("Not yet implemented")
    }

}