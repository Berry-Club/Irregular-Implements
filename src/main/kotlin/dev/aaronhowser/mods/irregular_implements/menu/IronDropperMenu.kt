package dev.aaronhowser.mods.irregular_implements.menu

import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.DispenserMenu

class IronDropperMenu(
    containerId: Int,
    playerInventory: Inventory,
    container: Container
) : DispenserMenu(containerId, playerInventory, container) {

    constructor(containerId: Int, playerInventory: Inventory) : this(containerId, playerInventory, SimpleContainer(9))

}