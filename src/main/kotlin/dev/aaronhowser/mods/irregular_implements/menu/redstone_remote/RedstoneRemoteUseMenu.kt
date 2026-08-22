package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.aaron.menu.HeldItemMenuWithoutInventory
import dev.aaronhowser.mods.aaron.menu.MenuWithButtons
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.handler.redstone_signal.RedstoneHandlerSavedData
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class RedstoneRemoteUseMenu(
	containerId: Int,
	playerInventory: Inventory,
	usedHand: InteractionHand
) : HeldItemMenuWithoutInventory(
	ModMenuTypes.REDSTONE_REMOTE_USE.get(),
	containerId,
	usedHand
), MenuWithButtons {

	constructor(containerId: Int, playerInventory: Inventory, data: RegistryFriendlyByteBuf) :
			this(containerId, playerInventory, data.readEnum(InteractionHand::class.java))

	private val player = playerInventory.player

	fun getHeldItemStack(): ItemStack = player.getItemInHand(usedHand)

	override fun isValidHeldItem(heldItem: ItemStack): Boolean = heldItem.isItem(ModItems.REDSTONE_REMOTE)

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun handleButtonPressed(buttonId: Int) {
		val level = player.level() as? ServerLevel ?: return

		val remoteDataComponent = getHeldItemStack().get(ModDataComponents.REDSTONE_REMOTE) ?: return
		val locationFilterStack = remoteDataComponent.getLocation(buttonId)
		val location = locationFilterStack.get(ModDataComponents.GLOBAL_POS) ?: return

		RedstoneHandlerSavedData.addSignal(
			level = level,
			blockPos = location.pos,
			duration = 20,
			strength = 15
		)
	}
}