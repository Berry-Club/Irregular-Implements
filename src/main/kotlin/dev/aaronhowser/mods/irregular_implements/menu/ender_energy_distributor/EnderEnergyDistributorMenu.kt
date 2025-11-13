package dev.aaronhowser.mods.irregular_implements.menu.ender_energy_distributor

import dev.aaronhowser.mods.aaron.menu.components.FilteredSlot
import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderEnergyDistributorBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class EnderEnergyDistributorMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val container: Container,
) : MenuWithInventory(ModMenuTypes.ENDER_ENERGY_DISTRIBUTOR.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(EnderEnergyDistributorBlockEntity.INVENTORY_SIZE)
			)

	init {
		checkContainerSize(container, EnderEnergyDistributorBlockEntity.INVENTORY_SIZE)
		container.startOpen(playerInventory.player)

		addSlots()
		addPlayerInventorySlots(49)
	}

	override fun addSlots() {
		for (i in 0 until EnderEnergyDistributorBlockEntity.INVENTORY_SIZE) {
			val x = 17 + (i % 9) * 18

			//TODO: Add an Item Filter outline to the slot background
			val slot = FilteredSlot(container, i, x, 18) { it.has(ModDataComponents.GLOBAL_POS) }
			this.addSlot(slot)
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		val slot = slots.getOrNull(index)
		if (slot == null || !slot.hasItem()) return ItemStack.EMPTY

		val stackThere = slot.item
		val copyStack = stackThere.copy()

		val blockMaxInvIndex = EnderEnergyDistributorBlockEntity.INVENTORY_SIZE
		val playerInvMaxIndex = blockMaxInvIndex + 9 * 4

		if (index < blockMaxInvIndex) {
			if (!this.moveItemStackTo(stackThere, blockMaxInvIndex, playerInvMaxIndex, true)) {
				return ItemStack.EMPTY
			}
		} else if (!this.moveItemStackTo(stackThere, 0, blockMaxInvIndex, false)) {
			return ItemStack.EMPTY
		}

		if (stackThere.isEmpty) {
			slot.setByPlayer(ItemStack.EMPTY)
		} else {
			slot.setChanged()
		}

		if (stackThere.count == copyStack.count) return ItemStack.EMPTY

		slot.onTake(player, stackThere)

		return copyStack
	}

	override fun stillValid(player: Player): Boolean = container.stillValid(player)
}