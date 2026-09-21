package dev.aaronhowser.mods.irregular_implements.menu.block_detector

import dev.aaronhowser.mods.aaron.menu.MenuWithInventory
import dev.aaronhowser.mods.aaron.menu.components.ContainerSlot
import dev.aaronhowser.mods.irregular_implements.block_entity.BlockDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player

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
		addSlots(51)
	}

	override fun addContainerSlots() {
		val slot = ContainerSlot(blockDetectorContainer, 0, 80, 18)
		addSlot(slot)
	}

	override fun stillValid(player: Player): Boolean {
		return blockDetectorContainer.stillValid(player)
	}
}