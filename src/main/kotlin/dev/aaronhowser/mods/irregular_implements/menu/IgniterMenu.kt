package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IgniterBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class IgniterMenu(
    containerId: Int,
    private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.IGNITER.get(), containerId), MenuWithButtons {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                SimpleContainerData(IgniterBlockEntity.CONTAINER_DATA_SIZE)
            )

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return true
    }

    override fun handleButtonPressed(buttonId: Int) {
        TODO("Not yet implemented")
    }
}