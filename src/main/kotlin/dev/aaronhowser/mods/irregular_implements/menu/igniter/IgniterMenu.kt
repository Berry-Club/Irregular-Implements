package dev.aaronhowser.mods.irregular_implements.menu.igniter

import dev.aaronhowser.mods.irregular_implements.block.block_entity.IgniterBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class IgniterMenu(
	containerId: Int,
	private val containerData: ContainerData
) : AbstractContainerMenu(ModMenuTypes.IGNITER.get(), containerId), MenuWithButtons {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				SimpleContainerData(IgniterBlockEntity.CONTAINER_DATA_SIZE)
			)

	init {
		this.addDataSlots(this.containerData)
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return true
	}

	var mode: IgniterBlockEntity.Mode
		get() = IgniterBlockEntity.Mode.entries[containerData.get(IgniterBlockEntity.MODE_INDEX)]
		set(value) = containerData.set(IgniterBlockEntity.MODE_INDEX, value.ordinal)

	override fun handleButtonPressed(buttonId: Int) {
		if (buttonId != CYCLE_MODE_BUTTON_ID) return

		val nextMode = when (this.mode) {
			IgniterBlockEntity.Mode.KEEP_IGNITED -> IgniterBlockEntity.Mode.IGNITE
			IgniterBlockEntity.Mode.IGNITE -> IgniterBlockEntity.Mode.TOGGLE
			IgniterBlockEntity.Mode.TOGGLE -> IgniterBlockEntity.Mode.KEEP_IGNITED
		}

		this.mode = nextMode
	}

	companion object {
		const val CYCLE_MODE_BUTTON_ID = 0
	}
}