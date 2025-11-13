package dev.aaronhowser.mods.irregular_implements.menu.block_teleporter

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockTeleporterBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class BlockTeleporterMenu(
	containerId: Int,
	playerInventory: Inventory,
	val blockTeleporterContainer: Container
) : MenuWithInventory(ModMenuTypes.BLOCK_TELEPORTER.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(BlockTeleporterBlockEntity.CONTAINER_SIZE),
			)

	init {
		addSlots()
		addPlayerInventorySlots(51)
	}

	override fun addSlots() {
		//TODO: Add a Location Filter outline to the slot background
		val slot = FilteredSlot(blockTeleporterContainer, 0, 80, 18) { it.has(ModDataComponents.GLOBAL_POS) }
		addSlot(slot)
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return true
	}
}