package dev.aaronhowser.mods.irregular_implements.menu.auto_placer

import dev.aaronhowser.mods.aaron.menu.MenuWithInventory
import dev.aaronhowser.mods.aaron.menu.components.ContainerSlot
import dev.aaronhowser.mods.irregular_implements.block_entity.AutoPlacerBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player

class AutoPlacerMenu(
	containerId: Int,
	playerInventory: Inventory,
	val autoPlacerContainer: Container
) : MenuWithInventory(ModMenuTypes.AUTO_PLACER.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(AutoPlacerBlockEntity.CONTAINER_SIZE),
			)

	init {
		addSlots(51 + 16 + 16 + 1)
	}

	override fun addContainerSlots() {
		val slot = ContainerSlot(autoPlacerContainer, 0, 80, 35)
		addSlot(slot)
	}

	override fun stillValid(player: Player): Boolean {
		return autoPlacerContainer.stillValid(player)
	}

}