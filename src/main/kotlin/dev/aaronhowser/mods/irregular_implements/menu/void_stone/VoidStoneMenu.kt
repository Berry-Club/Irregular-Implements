package dev.aaronhowser.mods.irregular_implements.menu.void_stone

import dev.aaronhowser.mods.irregular_implements.menu.HeldItemContainerMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class VoidStoneMenu(
	containerId: Int,
	playerInventory: Inventory
) : HeldItemContainerMenu(
	ModItems.VOID_STONE,
	ModMenuTypes.VOID_STONE.get(),
	containerId,
	playerInventory
) {

	private val temporaryContainer = SimpleContainer(1)

	init {
		addSlots()
		addPlayerInventorySlots(51)
	}

	override fun addSlots() {
		val voidSlotX = 80
		val voidSlotY = 18

		//TODO: Figure out how to make it only void the item in the slot when you place another item into it
		val voidSlot = object : Slot(temporaryContainer, 0, voidSlotX, voidSlotY) {
			override fun set(stack: ItemStack) {
				// Do nothing (voids the item)
			}
		}

		this.addSlot(voidSlot)
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		if (index !in 1..36) return ItemStack.EMPTY

		val slot = slots.getOrNull(index)

		if (slot != null && slot.hasItem()) {
			val stackInSlot = slot.item

			if (stackInSlot != this.playerInventory.getSelected()) {
				this.moveItemStackTo(stackInSlot, 0, 1, false)
			}
		}

		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return playerInventory.getSelected().`is`(ModItems.VOID_STONE)
	}
}