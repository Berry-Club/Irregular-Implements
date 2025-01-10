package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class ChatDetectorMenu(
    containerId: Int,
    private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.CHAT_DETECTOR.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE)
            )

    init {
        this.addDataSlots(containerData)
    }

    companion object {
        const val TOGGLE_MESSAGE_PASS_BUTTON_ID = 0
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return true
    }

}