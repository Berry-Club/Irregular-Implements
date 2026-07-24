package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.aaron.container.ContainerContainer
import dev.aaronhowser.mods.aaron.container.ImprovedSimpleContainer
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.loadItems
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.saveItems
import dev.aaronhowser.mods.irregular_implements.menu.filtered_redirector_plate.FilteredRedirectorPlateMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.state.BlockState

class FilteredRedirectorPlateBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : SyncingBlockEntity(ModBlockEntityTypes.FILTERED_REDIRECTOR_PLATE.get(), pos, blockState),
	MenuProvider,
	ContainerContainer {

	override val syncImmediately: Boolean = true

	private val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

	fun matchesFilter(index: Int, entity: Entity): Boolean {
		val entityType = container.getItem(index).get(ModDataComponents.ENTITY_TYPE) ?: return false
		return entity.type == entityType
	}

	override fun getContainers(): List<Container> = listOf(container)

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)
		tag.saveItems(container, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)
		tag.loadItems(container, registries)
	}

	override fun getDisplayName(): Component = blockState.block.name

	override fun createMenu(
		containerId: Int,
		playerInventory: Inventory,
		player: Player
	): AbstractContainerMenu {
		return FilteredRedirectorPlateMenu(containerId, playerInventory, container)
	}

	companion object {
		const val CONTAINER_SIZE = 2
	}

}
