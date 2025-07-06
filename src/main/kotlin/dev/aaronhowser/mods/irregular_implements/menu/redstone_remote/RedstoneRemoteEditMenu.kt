package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.HeldItemContainerMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.SlotItemHandler

class RedstoneRemoteEditMenu(
	containerId: Int,
	playerInventory: Inventory
) : HeldItemContainerMenu(
	ModItems.REDSTONE_REMOTE,
	ModMenuTypes.REDSTONE_REMOTE_EDIT.get(),
	containerId, playerInventory
) {

	private val itemHandler: IItemHandler? = getHeldItemStack().getCapability(Capabilities.ItemHandler.ITEM)

	init {
		addSlots()
		addPlayerInventorySlots(68)
	}

	override fun addSlots() {
		if (itemHandler == null) return

		for (pairIndex in 0 until RedstoneRemoteDataComponent.HORIZONTAL_SLOT_COUNT) {
			val x = 8 + pairIndex * 18

			val filterY = 18
			val filterSlot = SlotItemHandler(itemHandler, pairIndex, x, filterY)
			this.addSlot(filterSlot)

			val displayY = 36
			val displaySlot = SlotItemHandler(itemHandler, pairIndex + 9, x, displayY)
			this.addSlot(displaySlot)
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return player.getItemInHand(hand).`is`(ModItems.REDSTONE_REMOTE)
	}

}