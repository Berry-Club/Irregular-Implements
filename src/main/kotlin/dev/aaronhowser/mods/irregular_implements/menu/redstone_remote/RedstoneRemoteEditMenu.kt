package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class RedstoneRemoteEditMenu(
	containerId: Int,
	private val playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.REDSTONE_REMOTE_EDIT.get(), containerId) {

	// Uses a getter because when it mutates it only does so on server, and doesn't mutate the one on the client's copy of the menu
	private val redstoneRemoteStack: ItemStack
		get() =
			if (playerInventory.player.mainHandItem.`is`(ModItems.REDSTONE_REMOTE.get())) {
				playerInventory.player.mainHandItem
			} else {
				playerInventory.player.offhandItem
			}

	private var usingMainHand: Boolean = playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === redstoneRemoteStack

	init {

		for (pairIndex in 0 until 9) {
			val x = 8 + pairIndex * 18

			val filterY = 18
			val filterSlot = RedstoneRemoteFilterSlot(::redstoneRemoteStack, pairIndex, x, filterY)
			this.addSlot(filterSlot)

			val displayY = 36
			val displaySlot = RedstoneRemoteDisplaySlot(::redstoneRemoteStack, pairIndex, x, displayY)
			this.addSlot(displaySlot)
		}

		// Add the 27 slots of the player inventory
		for (row in 0..2) {
			for (column in 0..8) {
				val slotIndex = column + row * 9 + 9
				val x = 8 + column * 18
				val y = 68 + row * 18

				this.addSlot(Slot(playerInventory, slotIndex, x, y))
			}
		}

		// Add the 9 slots of the player hotbar
		for (hotbarIndex in 0..8) {
			val x = 8 + hotbarIndex * 18
			val y = 126

			this.addSlot(Slot(playerInventory, hotbarIndex, x, y))
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		val hand = if (usingMainHand) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND
		return player.getItemInHand(hand).`is`(ModItems.REDSTONE_REMOTE)
	}

}