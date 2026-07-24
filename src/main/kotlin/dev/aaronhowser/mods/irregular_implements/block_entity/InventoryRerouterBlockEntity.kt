package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.InventoryRerouterBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.IItemHandler
import java.util.*

class InventoryRerouterBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : SyncingBlockEntity(ModBlockEntityTypes.INVENTORY_REROUTER.get(), pos, blockState) {

	override val syncImmediately: Boolean = true

	private val configuredSides: MutableMap<Direction, Direction> =
		EnumMap(Direction::class.java)

	private val forwardingHandlers: MutableMap<Direction, IItemHandler> =
		EnumMap(Direction::class.java)

	init {
		for (direction in Direction.entries) {
			configuredSides[direction] = direction
			forwardingHandlers[direction] = ForwardingItemHandler(direction)
		}
	}

	fun cycleSide(exposedSide: Direction): Direction {
		val currentSide = configuredSides.getValue(exposedSide)
		val nextSide = Direction.entries[(currentSide.ordinal + 1) % Direction.entries.size]
		configuredSides[exposedSide] = nextSide

		setChanged()

		return nextSide
	}

	fun getConfiguredSide(exposedSide: Direction): Direction {
		return configuredSides.getValue(exposedSide)
	}

	private fun getTargetHandler(exposedSide: Direction): IItemHandler? {
		val level = level ?: return null
		val front = blockState.getValue(InventoryRerouterBlock.FACING)
		val targetDirection = front.opposite

		if (exposedSide == front) return null

		return level.getCapability(
			Capabilities.ItemHandler.BLOCK,
			blockPos.relative(targetDirection),
			getConfiguredSide(exposedSide)
		)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		for ((exposedSide, configuredSide) in configuredSides) {
			tag.putInt(exposedSide.name, configuredSide.ordinal)
		}
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		for (exposedSide in Direction.entries) {
			if (!tag.contains(exposedSide.name)) continue

			val ordinal = tag.getInt(exposedSide.name)
			if (ordinal in Direction.entries.indices) {
				configuredSides[exposedSide] = Direction.entries[ordinal]
			}
		}
	}

	private inner class ForwardingItemHandler(
		private val exposedSide: Direction
	) : IItemHandler {

		override fun getSlots(): Int = getTargetHandler(exposedSide)?.slots ?: 0

		override fun getStackInSlot(slot: Int): ItemStack {
			return getTargetHandler(exposedSide)?.getStackInSlot(slot) ?: ItemStack.EMPTY
		}

		override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
			return getTargetHandler(exposedSide)?.insertItem(slot, stack, simulate) ?: stack
		}

		override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
			return getTargetHandler(exposedSide)?.extractItem(slot, amount, simulate) ?: ItemStack.EMPTY
		}

		override fun getSlotLimit(slot: Int): Int {
			return getTargetHandler(exposedSide)?.getSlotLimit(slot) ?: 0
		}

		override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
			return getTargetHandler(exposedSide)?.isItemValid(slot, stack) ?: false
		}
	}

	companion object {
		fun getItemCapability(
			inventoryRerouter: InventoryRerouterBlockEntity,
			direction: Direction?
		): IItemHandler? {
			if (direction == null) return null

			val front = inventoryRerouter.blockState
				.getValue(InventoryRerouterBlock.FACING)
			if (direction == front) return null

			return inventoryRerouter.forwardingHandlers.getValue(direction)
		}
	}

}