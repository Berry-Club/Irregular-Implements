package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.InventoryTesterBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.InventoryTesterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.ItemHandlerHelper

class InventoryTesterBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.INVENTORY_TESTER.get(), pPos, pBlockState), MenuProvider {

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

	private var counter: Int = 0
	fun tick() {
		val level = level ?: return

		if (level.isClientSide) return

		// Only check every 5 ticks
		if (++this.counter != 5) return
		this.counter = 0

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

	val containerData = object : SimpleContainerData(CONTAINER_DATA_SIZE) {
		override fun get(index: Int): Int {
			return if (invertSignal) 1 else 0
		}

		override fun set(index: Int, value: Int) {
			invertSignal = value != 0
		}
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return InventoryTesterMenu(containerId, playerInventory, this.container, this.containerData)
	}

	override fun getDisplayName(): Component {
		return this.blockState.block.name
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}