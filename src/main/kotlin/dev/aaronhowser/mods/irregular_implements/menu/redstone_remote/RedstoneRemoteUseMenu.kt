package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.aaron.menu.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.handler.redstone_signal.RedstoneHandlerSavedData
import dev.aaronhowser.mods.irregular_implements.menu.HeldItemContainerMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

//TODO: Quick move stack
class RedstoneRemoteUseMenu(
	containerId: Int,
	playerInventory: Inventory
) : HeldItemContainerMenu(
	ModItems.REDSTONE_REMOTE,
	ModMenuTypes.REDSTONE_REMOTE_USE.get(),
	containerId,
	playerInventory
), MenuWithButtons {

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun handleButtonPressed(buttonId: Int) {
		val level = playerInventory.player.level() as? ServerLevel ?: return

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
