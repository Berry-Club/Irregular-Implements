package dev.aaronhowser.mods.irregular_implements.menu.block_detector

import dev.aaronhowser.mods.aaron.menu.components.FilteredSlot
import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack

class BlockDetectorMenu(
	containerId: Int,
	playerInventory: Inventory,
	val blockDetectorContainer: Container
) : MenuWithInventory(ModMenuTypes.BLOCK_DETECTOR.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(BlockDetectorBlockEntity.CONTAINER_SIZE),
			)

	init {
		addSlots()
		addPlayerInventorySlots(51)
	}

	override fun addSlots() {
		val slot = FilteredSlot(blockDetectorContainer, 0, 80, 18) { it.item is BlockItem }
		addSlot(slot)
	}

	//TODO
	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return blockDetectorContainer.stillValid(player)
	}
}