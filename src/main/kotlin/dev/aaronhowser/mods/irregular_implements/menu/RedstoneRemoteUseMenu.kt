package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack

class RedstoneRemoteUseMenu(
	containerId: Int,
	private val playerInventory: Inventory
) : AbstractContainerMenu(ModMenuTypes.REDSTONE_REMOTE_USE.get(), containerId), MenuWithButtons {

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		TODO("Not yet implemented")
	}

	override fun stillValid(player: Player): Boolean {
		TODO("Not yet implemented")
	}

	override fun handleButtonPressed(buttonId: Int) {
		TODO("Not yet implemented")
	}
}