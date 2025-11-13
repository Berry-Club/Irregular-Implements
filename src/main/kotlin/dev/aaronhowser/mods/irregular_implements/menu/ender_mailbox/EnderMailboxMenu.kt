package dev.aaronhowser.mods.irregular_implements.menu.ender_mailbox

import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class EnderMailboxMenu(
	containerId: Int,
	playerInventory: Inventory,
	val enderMailboxContainer: Container
) : MenuWithInventory(ModMenuTypes.ENDER_MAILBOX.get(), containerId, playerInventory) {

	constructor(
		containerId: Int,
		playerInventory: Inventory
	) : this(
		containerId,
		playerInventory,
		SimpleContainer(9)
	)

	init {
		addSlots()
		addPlayerInventorySlots(51)
	}

	override fun addSlots() {
		val y = 18
		for (i in 0 until 9) {
			val x = 8 + i * 18

			val slot = FilteredSlot(enderMailboxContainer, i, x, y) { false }
			addSlot(slot)
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean = enderMailboxContainer.stillValid(player)
}