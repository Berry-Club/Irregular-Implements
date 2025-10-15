package dev.aaronhowser.mods.irregular_implements.menu.advanced_redstone_torch

import dev.aaronhowser.mods.irregular_implements.menu.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack

class AdvancedRedstoneTorchMenu(
	containerId: Int,
	private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.BLOCK_DESTABILIZER.get(), containerId), MenuWithButtons {

	override fun quickMoveStack(player: Player, index: Int): ItemStack = ItemStack.EMPTY
	override fun stillValid(player: Player): Boolean = true

	override fun handleButtonPressed(buttonId: Int) {

	}

	companion object {
		const val DECREASE_GREEN_POWER_BUTTON_ID = 0
		const val INCREASE_GREEN_POWER_BUTTON_ID = 1
		const val DECREASE_RED_POWER_BUTTON_ID = 2
		const val INCREASE_RED_POWER_BUTTON_ID = 3
	}
}