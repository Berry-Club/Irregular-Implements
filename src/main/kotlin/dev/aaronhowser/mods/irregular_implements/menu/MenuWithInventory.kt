package dev.aaronhowser.mods.irregular_implements.menu

import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.Slot

abstract class MenuWithInventory(
	menuType: MenuType<*>?,
	containerId: Int,
	protected val playerInventory: Inventory,
) : AbstractContainerMenu(menuType, containerId) {

	protected open fun addPlayerInventorySlots(playerInventoryY: Int) {
		// Add the 27 slots of the player inventory
		for (row in 0 until 3) {
			val y = playerInventoryY + row * 18

			for (column in 0 until 9) {
				val slotIndex = column + row * 9 + 9
				val x = 8 + column * 18

				this.addSlot(Slot(playerInventory, slotIndex, x, y))
			}
		}

		val playerHotbarY = playerInventoryY + 58

		// Add the 9 slots of the player hotbar
		for (hotbarIndex in 0..8) {
			val x = 8 + hotbarIndex * 18

			this.addSlot(Slot(playerInventory, hotbarIndex, x, playerHotbarY))
		}
	}

	open fun addSlots() {}

}