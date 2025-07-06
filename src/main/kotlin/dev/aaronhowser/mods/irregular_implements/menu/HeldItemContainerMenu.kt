package dev.aaronhowser.mods.irregular_implements.menu

import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredItem

abstract class HeldItemContainerMenu(
	protected val item: Item,
	menuType: MenuType<*>?,
	containerId: Int,
	protected val playerInventory: Inventory,
) : AbstractContainerMenu(menuType, containerId) {

	constructor(
		deferredItem: DeferredItem<out Item>,
		menuType: MenuType<*>?,
		containerId: Int,
		playerInventory: Inventory
	) : this(deferredItem.get(), menuType, containerId, playerInventory)

	protected open fun addPlayerInventorySlots(playerInventoryY: Int) {
		// Add the 27 slots of the player inventory
		for (row in 0..2) {
			val y = playerInventoryY + row * 18

			for (column in 0..8) {
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

	abstract fun addSlots()

	open fun getHeldItemStack(): ItemStack {
		return if (playerInventory.player.mainHandItem.`is`(item)) {
			playerInventory.player.mainHandItem
		} else {
			playerInventory.player.offhandItem
		}
	}

	protected val hand: InteractionHand =
		if (playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === getHeldItemStack())
			InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND

	override fun stillValid(player: Player): Boolean {
		return player.getItemInHand(hand).`is`(item)
	}

}