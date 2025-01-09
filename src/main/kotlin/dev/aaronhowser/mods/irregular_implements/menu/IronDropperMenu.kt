package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.DispenserMenu
import net.minecraft.world.inventory.SimpleContainerData

class IronDropperMenu(
    containerId: Int,
    playerInventory: Inventory, container: Container, private val containerData: ContainerData
) : DispenserMenu(containerId, playerInventory, container) {

    constructor(containerId: Int, playerInventory: Inventory) : this(containerId, playerInventory, SimpleContainer(9), SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE))

}