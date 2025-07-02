package dev.aaronhowser.mods.irregular_implements.menu.ender_energy_distributor

import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderEnergyDistributorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class EnderEnergyDistributorMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val container: Container,
) : AbstractContainerMenu(ModMenuTypes.ENDER_ENERGY_DISTRIBUTOR.get(), containerId) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(EnderEnergyDistributorBlockEntity.INVENTORY_SIZE)
			)

	init {
		checkContainerSize(container, 1)
		container.startOpen(playerInventory.player)

		for (row in 0..2) {
			for (column in 0..8) {
				val inventorySlotIndex = column + row * 9 + 9

				val x = 8 + column * 18
				val y = 49 + row * 18

				this.addSlot(Slot(playerInventory, inventorySlotIndex, x, y))
			}
		}

		for (hotbarSlotIndex in 0..8) {
			val x = 8 + hotbarSlotIndex * 18
			val y = 107

			this.addSlot(Slot(playerInventory, hotbarSlotIndex, x, y))
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean = container.stillValid(player)
}