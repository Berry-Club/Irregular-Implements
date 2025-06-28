package dev.aaronhowser.mods.irregular_implements.menu.global_chat_detector

import dev.aaronhowser.mods.irregular_implements.block.block_entity.GlobalChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithStrings
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack

class GlobalChatDetectorMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val globalChatDetectorContainer: Container,
	private val containerData: ContainerData,
	private val containerLevelAccess: ContainerLevelAccess
) : AbstractContainerMenu(ModMenuTypes.GLOBAL_CHAT_DETECTOR.get(), containerId), MenuWithButtons, MenuWithStrings {

	constructor(containerId: Int, playerInventory: Inventory) :
			this(
				containerId,
				playerInventory,
				SimpleContainer(9),
				SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE),
				ContainerLevelAccess.NULL
			)

	init {
		checkContainerSize(globalChatDetectorContainer, 9)
		globalChatDetectorContainer.startOpen(playerInventory.player)

		for (containerSlotIndex in 0..8) {
			val x = 8 + containerSlotIndex * 18
			val y = 40

			val slot = object : Slot(globalChatDetectorContainer, containerSlotIndex, x, y) {
				override fun mayPlace(stack: ItemStack): Boolean {
					return stack.`is`(ModItems.ID_CARD) && stack.has(ModDataComponents.PLAYER)
				}

			}

			this.addSlot(slot)
		}

		for (row in 0..2) {
			for (column in 0..8) {
				val inventorySlotIndex = column + row * 9 + 9

				val x = 8 + column * 18
				val y = 75 + row * 18

				this.addSlot(Slot(playerInventory, inventorySlotIndex, x, y))
			}
		}

		for (hotbarSlotIndex in 0..8) {
			val x = 8 + hotbarSlotIndex * 18
			val y = 133

			this.addSlot(Slot(playerInventory, hotbarSlotIndex, x, y))
		}

		this.addDataSlots(containerData)
	}

	companion object {
		const val TOGGLE_MESSAGE_PASS_BUTTON_ID = 0

		const val REGEX_STRING_ID = 0
	}

	var shouldMessageStop: Boolean
		get() = containerData.get(GlobalChatDetectorBlockEntity.STOPS_MESSAGE_INDEX) == 1
		set(value) = containerData.set(GlobalChatDetectorBlockEntity.STOPS_MESSAGE_INDEX, if (value) 1 else 0)

	override fun handleButtonPressed(buttonId: Int) {
		when (buttonId) {
			TOGGLE_MESSAGE_PASS_BUTTON_ID -> shouldMessageStop = !shouldMessageStop
		}
	}

	override fun receiveString(stringId: Int, string: String) {
		if (stringId == REGEX_STRING_ID) setRegex(string)
	}

	private var currentRegexString: String = ""
	fun setRegex(regexString: String): Boolean {
		if (regexString == this.currentRegexString) return false
		this.currentRegexString = regexString

		this.containerLevelAccess.execute { level, pos ->
			val blockEntity = level.getBlockEntity(pos) as? GlobalChatDetectorBlockEntity
			blockEntity?.regexString = regexString
		}

		return true
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		val slot = slots.getOrNull(index)

		if (slot == null || !slot.hasItem()) return ItemStack.EMPTY

		val stackThere = slot.item
		val copyStack = stackThere.copy()

		if (index < 9) {
			if (!this.moveItemStackTo(stackThere, 9, 45, true)) {
				return ItemStack.EMPTY
			}
		} else if (!this.moveItemStackTo(stackThere, 0, 9, false)) {
			return ItemStack.EMPTY
		}

		if (stackThere.isEmpty) {
			slot.setByPlayer(ItemStack.EMPTY)
		} else {
			slot.setChanged()
		}

		if (stackThere.count == copyStack.count) return ItemStack.EMPTY

		slot.onTake(player, stackThere)

		return copyStack
	}

	override fun stillValid(player: Player): Boolean {
		return stillValid(this.containerLevelAccess, player, ModBlocks.GLOBAL_CHAT_DETECTOR.get())
	}
}