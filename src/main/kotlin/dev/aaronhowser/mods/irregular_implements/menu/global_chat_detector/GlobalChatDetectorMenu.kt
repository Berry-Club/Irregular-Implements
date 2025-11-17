package dev.aaronhowser.mods.irregular_implements.menu.global_chat_detector

import dev.aaronhowser.mods.aaron.menu.components.FilteredSlot
import dev.aaronhowser.mods.irregular_implements.block.block_entity.GlobalChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.aaron.menu.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithInventory
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithStrings
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class GlobalChatDetectorMenu(
	containerId: Int,
	playerInventory: Inventory,
	private val globalChatDetectorContainer: Container,
	private val containerData: ContainerData,
	private val containerLevelAccess: ContainerLevelAccess
) : MenuWithInventory(ModMenuTypes.GLOBAL_CHAT_DETECTOR.get(), containerId, playerInventory), MenuWithButtons, MenuWithStrings {

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

		addSlots()
		addPlayerInventorySlots(75)

		this.addDataSlots(containerData)
	}

	override fun addSlots() {
		for (containerSlotIndex in 0..8) {
			val x = 8 + containerSlotIndex * 18
			val y = 40

			//TODO: Add a Player Filter outline to the slot background
			val slot = FilteredSlot(globalChatDetectorContainer, containerSlotIndex, x, y) { stack ->
				stack.`is`(ModItems.PLAYER_FILTER) && stack.has(ModDataComponents.PLAYER)
			}
			this.addSlot(slot)
		}
	}

	var shouldMessageStop: Boolean
		get() = containerData.get(GlobalChatDetectorBlockEntity.STOPS_MESSAGE_INDEX) == 1
		set(value) = containerData.set(GlobalChatDetectorBlockEntity.STOPS_MESSAGE_INDEX, if (value) 1 else 0)

	override fun handleButtonPressed(buttonId: Int) {
		when (buttonId) {
			TOGGLE_MESSAGE_PASS_BUTTON_ID -> shouldMessageStop = !shouldMessageStop
		}
	}

	override fun receiveString(stringId: Int, stringReceived: String) {
		if (stringId == REGEX_STRING_ID) setRegex(stringReceived)
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

	companion object {
		const val TOGGLE_MESSAGE_PASS_BUTTON_ID = 0

		const val REGEX_STRING_ID = 0
	}
}