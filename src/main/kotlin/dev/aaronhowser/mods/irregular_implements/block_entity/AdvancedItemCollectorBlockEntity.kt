package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.container.ContainerContainer
import dev.aaronhowser.mods.aaron.container.ImprovedSimpleContainer
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.loadItems
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.saveItems
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.advanced_item_collector.AdvancedItemCollectorMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class AdvancedItemCollectorBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : ItemCollectorBlockEntity(ModBlockEntityTypes.ADVANCED_ITEM_COLLECTOR.get(), pPos, pBlockState), MenuProvider, ContainerContainer {

	var xRadius: Int = 5
		set(value) {
			field = value.coerceIn(0, 10)
			setChanged()
		}

	var yRadius: Int = 5
		set(value) {
			field = value.coerceIn(0, 10)
			setChanged()
		}

	var zRadius: Int = 5
		set(value) {
			field = value.coerceIn(0, 10)
			setChanged()
		}

	private val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

	override fun getContainers(): List<Container> {
		return listOf(container)
	}
	
	override fun getFilter(): ItemFilterDataComponent? {
		return container.getItem(0).get(ModDataComponents.ITEM_FILTER)
	}

	override fun getCollectionArea(): AABB {
		return AABB.ofSize(
			blockPos.center,
			2 * xRadius.toDouble(),
			2 * yRadius.toDouble(),
			2 * zRadius.toDouble()
		)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putInt(X_RADIUS_NBT, xRadius)
		tag.putInt(Y_RADIUS_NBT, yRadius)
		tag.putInt(Z_RADIUS_NBT, zRadius)

		tag.saveItems(container, registries)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		xRadius = tag.getInt(X_RADIUS_NBT)
		yRadius = tag.getInt(Y_RADIUS_NBT)
		zRadius = tag.getInt(Z_RADIUS_NBT)

		tag.loadItems(container, registries)
	}

	// Menu stuff

	private val containerData = object : ContainerData {
		override fun set(index: Int, value: Int) {
			when (index) {
				X_RADIUS_INDEX -> this@AdvancedItemCollectorBlockEntity.xRadius = value

				Y_RADIUS_INDEX -> this@AdvancedItemCollectorBlockEntity.yRadius = value

				Z_RADIUS_INDEX -> this@AdvancedItemCollectorBlockEntity.zRadius = value
			}
		}

		override fun get(index: Int): Int {
			return when (index) {
				X_RADIUS_INDEX -> this@AdvancedItemCollectorBlockEntity.xRadius

				Y_RADIUS_INDEX -> this@AdvancedItemCollectorBlockEntity.yRadius

				Z_RADIUS_INDEX -> this@AdvancedItemCollectorBlockEntity.zRadius

				else -> error("Invalid index: $index")
			}
		}

		override fun getCount(): Int = CONTAINER_DATA_SIZE
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return AdvancedItemCollectorMenu(containerId, playerInventory, container, containerData)
	}

	override fun getDisplayName(): Component = blockState.block.name

	companion object {
		const val X_RADIUS_NBT = "XRadius"
		const val Y_RADIUS_NBT = "YRadius"
		const val Z_RADIUS_NBT = "ZRadius"

		const val CONTAINER_SIZE = 1

		const val CONTAINER_DATA_SIZE = 3
		const val X_RADIUS_INDEX = 0
		const val Y_RADIUS_INDEX = 1
		const val Z_RADIUS_INDEX = 2
	}

}