package dev.aaronhowser.mods.irregular_implements.menu.advanced_redstone_interface

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneInterfaceAdvancedBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class AdvancedRedstoneInterfaceMenu(
	containerId: Int,
	playerInventory: Inventory,
	val interfaceContainer: Container
) : MenuWithInventory(ModMenuTypes.ADVANCED_REDSTONE_INTERFACE.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(RedstoneInterfaceAdvancedBlockEntity.CONTAINER_SIZE)
			)

	init {
		addSlots()
		addPlayerInventorySlots(51)
	}

	override fun addSlots() {
		val y = 18

		for (i in 0 until RedstoneInterfaceAdvancedBlockEntity.CONTAINER_SIZE) {
			val x = 8 + i * 18
			val slot = FilteredSlot(interfaceContainer, i, x, y) { it.has(ModDataComponents.GLOBAL_POS) }
			this.addSlot(slot)
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return interfaceContainer.stillValid(player)
	}
}