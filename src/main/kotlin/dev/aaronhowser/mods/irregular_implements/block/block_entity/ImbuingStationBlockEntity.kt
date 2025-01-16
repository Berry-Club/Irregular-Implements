package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.menu.ImbuingStationMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.ContainerHelper
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
        const val BOTTOM_SLOT_INDEX = 2
        const val CENTER_SLOT_INDEX = 3
        const val OUTPUT_SLOT_INDEX = 4
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        ContainerHelper.saveAllItems(tag, this.container.items, registries)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        ContainerHelper.loadAllItems(tag, this.container.items, registries)
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