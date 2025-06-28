package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack

class RedstoneRemoteEditMenu(
	containerId: Int,
	private val playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.REDSTONE_REMOTE_EDIT.get(), containerId) {

	private val holderLookup = this.playerInventory.player.level().registryAccess()

	// Uses a getter because when it mutates it only does so on server, and doesn't mutate the one on the client's copy of the menu
	private val redstoneRemoteStack: ItemStack
		get() =
			if (playerInventory.player.mainHandItem.`is`(ModItems.REDSTONE_REMOTE.get())) {
				playerInventory.player.mainHandItem
			} else {
				playerInventory.player.offhandItem
			}

	private var usingMainHand: Boolean = playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === redstoneRemoteStack

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		val hand = if (usingMainHand) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND
		return player.getItemInHand(hand).`is`(ModItems.REDSTONE_REMOTE)
	}

}