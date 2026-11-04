package dev.aaronhowser.mods.irregular_implements.menu.entity_detector

import dev.aaronhowser.mods.aaron.menu.MenuWithButtons
import dev.aaronhowser.mods.aaron.menu.MenuWithInventory
import dev.aaronhowser.mods.aaron.menu.components.ContainerSlot
import dev.aaronhowser.mods.irregular_implements.block_entity.EntityDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class EntityDetectorMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val container: Container,
	private val containerData: ContainerData
) : MenuWithInventory(ModMenuTypes.ENTITY_DETECTOR.get(), containerId, playerInventory), MenuWithButtons {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(EntityDetectorBlockEntity.CONTAINER_SIZE),
				SimpleContainerData(EntityDetectorBlockEntity.CONTAINER_DATA_SIZE)
			)

	init {
		checkContainerSize(container, EntityDetectorBlockEntity.CONTAINER_SIZE)
		container.startOpen(playerInventory.player)

		addSlots(153)

		checkContainerDataCount(containerData, EntityDetectorBlockEntity.CONTAINER_DATA_SIZE)
		addDataSlots(containerData)
	}

	override fun addContainerSlots() {
		val slot = ContainerSlot(container, 0, 80, 122)
		addSlot(slot)
	}

	override fun quickMoveStack(player: Player, clickedSlotIndex: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return container.stillValid(player)
	}

	var xRadius: Int
		get() = containerData.get(EntityDetectorBlockEntity.X_RADIUS_INDEX)
		private set(value) = containerData.set(EntityDetectorBlockEntity.X_RADIUS_INDEX, value)

	var yRadius: Int
		get() = containerData.get(EntityDetectorBlockEntity.Y_RADIUS_INDEX)
		private set(value) = containerData.set(EntityDetectorBlockEntity.Y_RADIUS_INDEX, value)

	var zRadius: Int
		get() = containerData.get(EntityDetectorBlockEntity.Z_RADIUS_INDEX)
		private set(value) = containerData.set(EntityDetectorBlockEntity.Z_RADIUS_INDEX, value)

	var isInverted: Boolean
		get() = containerData.get(EntityDetectorBlockEntity.INVERTED_INDEX) != 0
		private set(value) = containerData.set(EntityDetectorBlockEntity.INVERTED_INDEX, if (value) 1 else 0)

	var filterOrdinal: Int
		get() = containerData.get(EntityDetectorBlockEntity.FILTER_ORDINAL_INDEX)
		private set(value) = containerData.set(EntityDetectorBlockEntity.FILTER_ORDINAL_INDEX, value)

	fun getFilter(): EntityDetectorBlockEntity.Filter {
		val ordinal = filterOrdinal.coerceIn(0, EntityDetectorBlockEntity.Filter.entries.size - 1)
		return EntityDetectorBlockEntity.Filter.entries[ordinal]
	}

	override fun handleButtonPressed(buttonId: Int, isShiftDown: Boolean) {
		when (buttonId) {
			LOWER_X_BUTTON_ID -> xRadius--
			RAISE_X_BUTTON_ID -> xRadius++

			LOWER_Y_BUTTON_ID -> yRadius--
			RAISE_Y_BUTTON_ID -> yRadius++

			LOWER_Z_BUTTON_ID -> zRadius--
			RAISE_Z_BUTTON_ID -> zRadius++

			TOGGLE_INVERTED_BUTTON_ID -> isInverted = !isInverted

			CYCLE_FILTER_BUTTON_ID -> {
				val nextOrdinal = (filterOrdinal + 1) % EntityDetectorBlockEntity.Filter.entries.size
				filterOrdinal = nextOrdinal
			}
		}
	}

	companion object {
		const val LOWER_X_BUTTON_ID = 0
		const val RAISE_X_BUTTON_ID = 1
		const val LOWER_Y_BUTTON_ID = 2
		const val RAISE_Y_BUTTON_ID = 3
		const val LOWER_Z_BUTTON_ID = 4
		const val RAISE_Z_BUTTON_ID = 5
		const val TOGGLE_INVERTED_BUTTON_ID = 6
		const val CYCLE_FILTER_BUTTON_ID = 7
	}

}