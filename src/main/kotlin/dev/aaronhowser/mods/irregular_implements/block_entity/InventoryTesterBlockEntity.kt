package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.InventoryTesterBlock
import dev.aaronhowser.mods.irregular_implements.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.inventory_tester.InventoryTesterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.ItemHandlerHelper

class InventoryTesterBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : SyncingBlockEntity(ModBlockEntityTypes.INVENTORY_TESTER.get(), pPos, pBlockState), MenuProvider {

	val container = ImprovedSimpleContainer(this, CONTAINER_SIZE)

	private var invertSignal: Boolean = false
		set(value) {
			field = value
			setChanged()
		}

	var isEmittingRedstone: Boolean = false
		private set(value) {
			field = value
			setChanged()
			level?.updateNeighborsAt(this.blockPos, this.blockState.block)
		}

	fun tick() {
		val level = level as? ServerLevel ?: return
		if (level.gameTime % 20 != 0L) return

		val item = this.container.getItem(0)
		if (item.isEmpty) {
			if (this.isEmittingRedstone) this.isEmittingRedstone = false
			return
		}

		val facing = this.blockState.getValue(InventoryTesterBlock.FACING)
		val onBlock = this.blockPos.relative(facing)
		val itemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, onBlock, facing.opposite) ?: return

		val canInsert = ItemHandlerHelper.insertItemStacked(itemHandler, item, true).isEmpty

		val redstone = if (this.invertSignal) !canInsert else canInsert

		if (redstone != this.isEmittingRedstone) {
			this.isEmittingRedstone = redstone
		}
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		ContainerHelper.saveAllItems(tag, this.container.items, registries)
		tag.putBoolean(INVERT_SIGNAL_NBT, this.invertSignal)
		tag.putBoolean(IS_EMITTING_REDSTONE_NBT, this.isEmittingRedstone)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		ContainerHelper.loadAllItems(tag, this.container.items, registries)
		this.invertSignal = tag.getBoolean(INVERT_SIGNAL_NBT)
		this.isEmittingRedstone = tag.getBoolean(IS_EMITTING_REDSTONE_NBT)
	}

	// Menu stuff

	private val containerData: ContainerData =
		object : ContainerData {
			override fun get(index: Int): Int {
				return if (invertSignal) 1 else 0
			}

			override fun set(index: Int, value: Int) {
				invertSignal = value != 0
			}

			override fun getCount(): Int = CONTAINER_DATA_SIZE
		}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return InventoryTesterMenu(containerId, playerInventory, this.container, this.containerData)
	}

	override fun getDisplayName(): Component {
		return this.blockState.block.name
	}

	companion object {
		const val INVERT_SIGNAL_NBT = "InvertSignal"
		const val IS_EMITTING_REDSTONE_NBT = "IsEmittingRedstone"

		const val CONTAINER_SIZE = 1
		const val CONTAINER_DATA_SIZE = 1

		fun tick(
			level: Level,
			pos: BlockPos,
			state: BlockState,
			blockEntity: InventoryTesterBlockEntity
		) {
			blockEntity.tick()
		}
	}


}