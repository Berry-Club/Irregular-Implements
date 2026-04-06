package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.aaron.container.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.filtered_platform.FilteredPlatformMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.state.BlockState

class FilteredPlatformBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : SyncingBlockEntity(ModBlockEntityTypes.FILTERED_PLATFORM.get(), pPos, pBlockState), MenuProvider {

	override val syncImmediately: Boolean = true

	fun entityPassesFilter(entity: Entity?): Boolean {
		if (entity !is ItemEntity) return false

		val filter = this.container
			.getItem(0)
			.get(ModDataComponents.ITEM_FILTER)
			?: return false

		return filter.test(entity.item)
	}

	val container = ImprovedSimpleContainer(this, 1)

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		ContainerHelper.saveAllItems(tag, this.container.items, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		ContainerHelper.loadAllItems(tag, this.container.items, registries)
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return FilteredPlatformMenu(containerId, playerInventory, this.container)
	}

	override fun getDisplayName(): Component {
		return this.blockState.block.name
	}

}