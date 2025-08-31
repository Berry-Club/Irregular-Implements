package dev.aaronhowser.mods.irregular_implements.menu.auto_placer

import dev.aaronhowser.mods.irregular_implements.block.block_entity.AutoPlacerBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack

class AutoPlacerMenu(
	containerId: Int,
	playerInventory: Inventory,
	val autoPlacerContainer: Container
) : MenuWithInventory(ModMenuTypes.AUTO_PLACER.get(), containerId, playerInventory) {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(AutoPlacerBlockEntity.CONTAINER_SIZE),
			)

	init {
		addSlots()
		addPlayerInventorySlots(51)
	}

	override fun addSlots() {
		val slot = object : Slot(autoPlacerContainer, 0, 80, 18) {
			override fun mayPlace(stack: ItemStack): Boolean {
				return stack.item is BlockItem
			}
		}

		addSlot(slot)
	}

	//TODO
	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return autoPlacerContainer.stillValid(player)
	}

}