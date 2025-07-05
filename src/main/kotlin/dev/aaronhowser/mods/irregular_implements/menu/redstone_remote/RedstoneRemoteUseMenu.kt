package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.handler.redstone_signal.RedstoneHandlerSavedData
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack

//TODO: Quick move stack
//TODO: Weirdness when putting items in sometimes
class RedstoneRemoteUseMenu(
	containerId: Int,
	private val playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.REDSTONE_REMOTE_USE.get(), containerId), MenuWithButtons {

	// Uses a getter because when it mutates it only does so on server, and doesn't mutate the one on the client's copy of the menu
	fun getRedstoneRemoteStack(): ItemStack {
		return if (playerInventory.player.mainHandItem.`is`(ModItems.REDSTONE_REMOTE.get())) {
			playerInventory.player.mainHandItem
		} else {
			playerInventory.player.offhandItem
		}
	}

	private val hand: InteractionHand =
		if (playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND) === getRedstoneRemoteStack())
			InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return player.getItemInHand(hand).`is`(ModItems.REDSTONE_REMOTE)
	}

	override fun handleButtonPressed(buttonId: Int) {
		val level = playerInventory.player.level() as? ServerLevel ?: return

		val remoteDataComponent = getRedstoneRemoteStack().get(ModDataComponents.REDSTONE_REMOTE) ?: return
		val locationFilterStack = remoteDataComponent.getLocation(buttonId)
		val location = locationFilterStack.get(ModDataComponents.LOCATION) ?: return

		RedstoneHandlerSavedData.addSignal(
			level = level,
			blockPos = location.blockPos,
			duration = 20,
			strength = 15
		)
	}
}
