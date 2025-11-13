package dev.aaronhowser.mods.irregular_implements.menu.block_destabilizer

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDestabilizerBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class BlockDestabilizerMenu(
	containerId: Int,
	private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.BLOCK_DESTABILIZER.get(), containerId), MenuWithButtons {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE)
			)

	init {
		this.addDataSlots(containerData)
	}

	val isLazy: Boolean
		get() = containerData.get(BlockDestabilizerBlockEntity.LAZY_INDEX) == 1

	private fun toggleLazy() {
		containerData.set(BlockDestabilizerBlockEntity.LAZY_INDEX, 0)   // Toggles lazy no matter what value is passed
	}

	private fun showLazyShape() {
		containerData.set(BlockDestabilizerBlockEntity.SHOW_LAZY_INDEX, 1)
	}

	private fun resetLazyShape() {
		containerData.set(BlockDestabilizerBlockEntity.RESET_LAZY_INDEX, 0)
	}

	// Only called on server
	override fun handleButtonPressed(buttonId: Int) {
		when (buttonId) {
			TOGGLE_LAZY_BUTTON_ID -> toggleLazy()
			SHOW_LAZY_SHAPE_BUTTON_ID -> showLazyShape()
			RESET_LAZY_SHAPE_BUTTON_ID -> resetLazyShape()
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return true
	}

	companion object {
		const val TOGGLE_LAZY_BUTTON_ID = 0
		const val SHOW_LAZY_SHAPE_BUTTON_ID = 1
		const val RESET_LAZY_SHAPE_BUTTON_ID = 2
	}

}