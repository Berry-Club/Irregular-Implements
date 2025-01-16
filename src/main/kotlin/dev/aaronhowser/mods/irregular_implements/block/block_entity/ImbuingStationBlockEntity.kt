package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.menu.ImbuingStationMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class ImbuingStationBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.IMBUING_STATION.get(), pPos, pBlockState), MenuProvider {

    companion object {
        const val CONTAINER_SIZE = 5

        const val TOP_SLOT_INDEX = 0
        const val LEFT_SLOT_INDEX = 1
        const val RIGHT_SLOT_INDEX = 2
        const val BOTTOM_SLOT_INDEX = 3
        const val CENTER_SLOT_INDEX = 4
        const val OUTPUT_SLOT_INDEX = 5
    }

    // Container stuff

    private val container = SimpleContainer(CONTAINER_SIZE)

    // Menu stuff

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
        return ImbuingStationMenu(containerId, playerInventory, this.container)
    }

    override fun getDisplayName(): Component {
        return this.blockState.block.name
    }
}