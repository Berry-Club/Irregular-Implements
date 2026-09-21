package dev.aaronhowser.mods.irregular_implements.menu.void_stone

import dev.aaronhowser.mods.aaron.menu.HeldItemMenu
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class VoidStoneMenu(
	containerId: Int,
	playerInventory: Inventory,
	usedHand: InteractionHand
) : HeldItemMenu(
	ModMenuTypes.VOID_STONE.get(),
	containerId,
	playerInventory,
	usedHand
) {

	constructor(containerId: Int, playerInventory: Inventory, data: RegistryFriendlyByteBuf) :
			this(containerId, playerInventory, data.readEnum(InteractionHand::class.java))

	override fun isValidHeldItem(heldItem: ItemStack): Boolean = heldItem.isItem(ModItems.VOID_STONE)

	private val temporaryContainer = SimpleContainer(1)

	init {
		addSlots(51)
	}

	override fun addContainerSlots() {
		val voidSlotX = 80
		val voidSlotY = 18

		val voidSlot = VoidSlot(voidSlotX, voidSlotY)

		addSlot(voidSlot)
	}

	override fun quickMoveStack(player: Player, slotIndex: Int): ItemStack {
		if (slotIndex !in 1..36) return ItemStack.EMPTY

		val slot = slots.getOrNull(slotIndex)

		if (slot != null && slot.hasItem()) {
			val stackInSlot = slot.item

			if (stackInSlot != playerInventory.getSelected()) {
				moveItemStackTo(stackInSlot, 0, 1, false)
			}
		}

		return ItemStack.EMPTY
	}

	private inner class VoidSlot(x: Int, y: Int) : Slot(temporaryContainer, 0, x, y) {

		override fun set(stack: ItemStack) {
			// Do nothing (voids the item)
		}

	}

}