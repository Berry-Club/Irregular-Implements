package dev.aaronhowser.mods.irregular_implements.menu.inventory_tester

import dev.aaronhowser.mods.aaron.menu.MenuWithButtons
import dev.aaronhowser.mods.aaron.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.block_entity.InventoryTesterBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class InventoryTesterMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val container: Container,
	private val containerData: ContainerData
) : MenuWithInventory(ModMenuTypes.INVENTORY_TESTER.get(), containerId, playerInventory), MenuWithButtons {

	constructor(containerId: Int, playerInventory: Inventory) : this(
		containerId,
		playerInventory,
		SimpleContainer(1),
		SimpleContainerData(InventoryTesterBlockEntity.CONTAINER_DATA_SIZE)
	)

	init {
		checkContainerDataCount(containerData, InventoryTesterBlockEntity.CONTAINER_DATA_SIZE)
		checkContainerSize(container, InventoryTesterBlockEntity.CONTAINER_SIZE)

		addSlots(54)

		addDataSlots(containerData)
	}

	override fun addContainerSlots() {
		val slot = InventoryTesterSlot(container, 64, 18)
		addSlot(slot)
	}

	override fun quickMoveStack(player: Player, clickedSlotIndex: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return container.stillValid(player)
	}

	var isInverted: Boolean
		get() = containerData.get(0) != 0
		private set(value) = containerData.set(0, if (value) 1 else 0)

	override fun handleButtonPressed(buttonId: Int, isShiftDown: Boolean) {
		when (buttonId) {
			TOGGLE_INVERSION_BUTTON_ID -> isInverted = !isInverted
		}
	}

	companion object {
		const val TOGGLE_INVERSION_BUTTON_ID = 0
	}

}