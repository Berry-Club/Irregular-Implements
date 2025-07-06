package dev.aaronhowser.mods.irregular_implements.menu.imbuing_station

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ImbuingStationBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class ImbuingStationMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val imbuingStationContainer: Container
) : MenuWithInventory(ModMenuTypes.IMBUING_STATION.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(ImbuingStationBlockEntity.CONTAINER_SIZE)
			)

	init {
		checkContainerSize(imbuingStationContainer, 4)

		addSlots()
		addPlayerInventorySlots(126)
	}

	override fun addSlots() {
		val topSlot = Slot(
			imbuingStationContainer,
			ImbuingStationBlockEntity.TOP_SLOT_INDEX,
			80,
			9
		)
		this.addSlot(topSlot)

		val leftSlot = Slot(
			imbuingStationContainer,
			ImbuingStationBlockEntity.LEFT_SLOT_INDEX,
			35,
			54
		)
		this.addSlot(leftSlot)

		val middleSlot = Slot(
			imbuingStationContainer,
			ImbuingStationBlockEntity.CENTER_SLOT_INDEX,
			80,
			54
		)
		this.addSlot(middleSlot)

		val bottomSlot = Slot(
			imbuingStationContainer,
			ImbuingStationBlockEntity.BOTTOM_SLOT_INDEX,
			80,
			99
		)
		this.addSlot(bottomSlot)

		val outputSlot = Slot(
			imbuingStationContainer,
			ImbuingStationBlockEntity.OUTPUT_SLOT_INDEX,
			125,
			54
		)
		this.addSlot(outputSlot)
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return imbuingStationContainer.stillValid(player)
	}
}