package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState

class ImbuingStationBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BaseContainerBlockEntity(ModBlockEntities.IMBUING_STATION.get(), pPos, pBlockState) {

    // Container stuff

    private var items: NonNullList<ItemStack> = NonNullList.withSize(containerSize, ItemStack.EMPTY)

    override fun getContainerSize(): Int {
        return 4
    }

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    override fun setItems(items: NonNullList<ItemStack>) {
        this.items = items
    }

    // Menu stuff

    override fun createMenu(containerId: Int, inventory: Inventory): AbstractContainerMenu {
        TODO("Not yet implemented")
    }

    override fun getDefaultName(): Component {
        return this.blockState.block.name
    }
}