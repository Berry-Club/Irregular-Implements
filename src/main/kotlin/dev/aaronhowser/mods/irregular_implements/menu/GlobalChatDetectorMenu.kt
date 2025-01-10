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
import net.minecraft.world.item.ItemStack

class GlobalChatDetectorMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val globalChatDetectorContainer: Container,
    private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.IRON_DROPPER.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                SimpleContainer(9),
                SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE)
            )

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean {
        return globalChatDetectorContainer.stillValid(player)
    }
}