package dev.aaronhowser.mods.irregular_implements.menu.ender_mailbox

import dev.aaronhowser.mods.irregular_implements.handler.ender_letter.EnderMailboxContainer
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack

class EnderMailboxMenu(
	containerId: Int,
	val playerInventory: Inventory,
	val enderMailboxContainer: EnderMailboxContainer
) : AbstractContainerMenu(ModMenuTypes.ENDER_MAILBOX.get(), containerId) {

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean = enderMailboxContainer.stillValid(player)
}