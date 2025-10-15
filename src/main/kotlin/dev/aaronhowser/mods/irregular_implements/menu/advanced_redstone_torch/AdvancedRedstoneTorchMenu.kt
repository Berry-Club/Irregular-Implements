package dev.aaronhowser.mods.irregular_implements.menu.advanced_redstone_torch

import dev.aaronhowser.mods.irregular_implements.block.block_entity.AdvancedRedstoneTorchBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class AdvancedRedstoneTorchMenu(
	containerId: Int,
	private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.BLOCK_DESTABILIZER.get(), containerId), MenuWithButtons {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				SimpleContainerData(AdvancedRedstoneTorchBlockEntity.CONTAINER_DATA_SIZE)
			)

	override fun quickMoveStack(player: Player, index: Int): ItemStack = ItemStack.EMPTY
	override fun stillValid(player: Player): Boolean = true

	var strengthGreen: Int
		get() = containerData.get(AdvancedRedstoneTorchBlockEntity.STRENGTH_GREEN_INDEX)
		set(value) = containerData.set(AdvancedRedstoneTorchBlockEntity.STRENGTH_GREEN_INDEX, value)

	var strengthRed: Int
		get() = containerData.get(AdvancedRedstoneTorchBlockEntity.STRENGTH_RED_INDEX)
		set(value) = containerData.set(AdvancedRedstoneTorchBlockEntity.STRENGTH_RED_INDEX, value)

	override fun handleButtonPressed(buttonId: Int) {
		when (buttonId) {
			DECREASE_GREEN_POWER_BUTTON_ID -> {
				val newStrength = if (strengthGreen == 0) 15 else (strengthGreen - 1)
				strengthGreen = newStrength
			}

			INCREASE_GREEN_POWER_BUTTON_ID -> {
				val newStrength = (strengthGreen + 1) % 16
				strengthGreen = newStrength
			}

			DECREASE_RED_POWER_BUTTON_ID -> {
				val newStrength = if (strengthRed == 0) 15 else (strengthRed - 1)
				strengthRed = newStrength
			}

			INCREASE_RED_POWER_BUTTON_ID -> {
				val newStrength = (strengthRed + 1) % 16
				strengthRed = newStrength
			}
		}
	}

	companion object {
		const val DECREASE_GREEN_POWER_BUTTON_ID = 0
		const val INCREASE_GREEN_POWER_BUTTON_ID = 1
		const val DECREASE_RED_POWER_BUTTON_ID = 2
		const val INCREASE_RED_POWER_BUTTON_ID = 3
	}
}