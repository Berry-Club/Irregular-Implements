package dev.aaronhowser.mods.irregular_implements.menu.notification_interface

import dev.aaronhowser.mods.irregular_implements.block.block_entity.NotificationInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.GhostSlot
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithStrings
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack

class NotificationInterfaceMenu(
	containerId: Int,
	notificationInterfaceContainer: Container,
	private val containerLevelAccess: ContainerLevelAccess
) : AbstractContainerMenu(ModMenuTypes.NOTIFICATION_INTERFACE.get(), containerId), MenuWithStrings {

	constructor(containerId: Int, playerInventory: Inventory) : this(
		containerId,
		SimpleContainer(1),
		ContainerLevelAccess.NULL
	)

	init {
		checkContainerSize(notificationInterfaceContainer, 1)

		this.addSlot(
			GhostSlot(
				notificationInterfaceContainer,
				0,
				8,
				31
			)
		)
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return stillValid(containerLevelAccess, player, ModBlocks.NOTIFICATION_INTERFACE.get())
	}

	override fun receiveString(stringId: Int, stringReceived: String) {
		when (stringId) {
			TITLE_STRING_ID -> setTitle(stringReceived)
			DESCRIPTION_STRING_ID -> setDescription(stringReceived)
		}
	}

	private var title: String = ""
	fun setTitle(newTitle: String): Boolean {
		if (newTitle == this.title) return false
		this.title = newTitle

		this.containerLevelAccess.execute { level, pos ->
			val blockEntity = level.getBlockEntity(pos) as? NotificationInterfaceBlockEntity
			blockEntity?.toastTitle = newTitle
		}

		return true
	}

	private var description: String = ""
	fun setDescription(newDescription: String): Boolean {
		if (newDescription == this.description) return false
		this.description = newDescription

		this.containerLevelAccess.execute { level, pos ->
			val blockEntity = level.getBlockEntity(pos) as? NotificationInterfaceBlockEntity
			blockEntity?.toastDescription = newDescription
		}

		return true
	}

	companion object {
		const val TITLE_STRING_ID = 0
		const val DESCRIPTION_STRING_ID = 1
	}

}