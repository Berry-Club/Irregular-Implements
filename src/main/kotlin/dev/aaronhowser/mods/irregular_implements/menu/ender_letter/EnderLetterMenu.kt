package dev.aaronhowser.mods.irregular_implements.menu.ender_letter

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.IItemHandler

class EnderLetterMenu(
	containerId: Int,
	private val playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.ENDER_LETTER.get(), containerId) {

	private fun getEnderLetterStack(): ItemStack {
		return if (playerInventory.player.mainHandItem.`is`(ModItems.ENDER_LETTER.get())) {
			playerInventory.player.mainHandItem
		} else {
			playerInventory.player.offhandItem
		}
	}

	private var usingMainHand: Boolean =
		playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === getEnderLetterStack()

	private val itemHandler: IItemHandler? = getEnderLetterStack().getCapability(Capabilities.ItemHandler.ITEM)

	init {
		if (itemHandler != null) {

		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		val hand = if (usingMainHand) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND
		return player.getItemInHand(hand).`is`(ModItems.REDSTONE_REMOTE)
	}
}