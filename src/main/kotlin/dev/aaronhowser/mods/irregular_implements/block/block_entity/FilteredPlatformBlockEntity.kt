package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.menu.FilteredPlatformMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FilteredPlatformBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.FILTERED_PLATFORM.get(), pPos, pBlockState), MenuProvider {

    fun entityPassesFilter(entity: Entity): Boolean {
        if (entity !is ItemEntity) return false

        val filter = this.container
            .getItem(0)
            .get(ModDataComponents.ITEM_FILTER_ENTRIES)
            ?: return false

        return filter.test(entity.item)
    }

    val container = SimpleContainer(1)

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
        return FilteredPlatformMenu(containerId, playerInventory, this.container)
    }

    override fun getDisplayName(): Component {
        return this.blockState.block.name
    }
}