package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class AdvancedItemCollectorMenu(
    containerId: Int,
    playerInventory: Inventory,
    private val container: Container,
    private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.ADVANCED_ITEM_COLLECTOR.get(), containerId), MenuWithButtons {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                SimpleContainer(1),
                SimpleContainerData(1)
            )

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean {
        return container.stillValid(player)
    }

    override fun handleButtonPressed(buttonId: Int) {
        TODO("Not yet implemented")
    }
}