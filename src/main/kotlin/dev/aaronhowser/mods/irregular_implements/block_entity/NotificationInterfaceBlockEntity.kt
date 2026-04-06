package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.container.ContainerContainer
import dev.aaronhowser.mods.aaron.container.ImprovedSimpleContainer
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.getUuidOrNull
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.loadItems
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.saveItems
import dev.aaronhowser.mods.aaron.packet.s2c.UpdateClientScreenString
import dev.aaronhowser.mods.irregular_implements.menu.notification_interface.NotificationInterfaceMenu
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.SendClientToast
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class NotificationInterfaceBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntityTypes.NOTIFICATION_INTERFACE.get(), pPos, pBlockState), MenuProvider, ContainerContainer {

	val container = ImprovedSimpleContainer(this, 1)
	val icon: ItemStack
		get() = container.getItem(0)

	override fun getContainers(): List<Container> {
		return listOf(container)
	}

	var ownerUuid: UUID = UUID.randomUUID()
		set(value) {
			field = value
			setChanged()
		}

	var toastTitle: String = ""
		set(value) {
			field = value
			setChanged()
			sendStringUpdate()
		}

	var toastDescription: String = ""
		set(value) {
			field = value
			setChanged()
			sendStringUpdate()
		}

	fun sendStringUpdate() {
		val level = level as? ServerLevel ?: return

		val titlePacket = UpdateClientScreenString(NotificationInterfaceMenu.TITLE_STRING_ID, toastTitle)
		titlePacket.messageNearbyPlayers(level, blockPos.center, 16.0)

		val descriptionPacket = UpdateClientScreenString(NotificationInterfaceMenu.DESCRIPTION_STRING_ID, toastDescription)
		descriptionPacket.messageNearbyPlayers(level, blockPos.center, 16.0)
	}

	fun notifyOwner() {
		val level = level as? ServerLevel ?: return
		val owner = level.server.playerList.getPlayer(ownerUuid) ?: return

		val packet = SendClientToast(toastTitle, toastDescription, icon)
		packet.messagePlayer(owner)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putUUID(OWNER_UUID_NBT, ownerUuid)
		tag.putString(TOAST_TITLE_NBT, toastTitle)
		tag.putString(TOAST_DESCRIPTION_NBT, toastDescription)

		if (!container.isEmpty) {
			tag.saveItems(container, registries)
		}
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		val uuid = tag.getUuidOrNull(OWNER_UUID_NBT)
		if (uuid != null) {
			ownerUuid = uuid
		}

		toastTitle = tag.getString(TOAST_TITLE_NBT)
		toastDescription = tag.getString(TOAST_DESCRIPTION_NBT)

		tag.loadItems(container, registries)
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return NotificationInterfaceMenu(
			containerId,
			playerInventory,
			container,
			ContainerLevelAccess.create(level!!, blockPos)
		)
	}

	override fun getDisplayName(): Component {
		return blockState.block.name
	}

	companion object {
		const val TOAST_TITLE_NBT = "ToastTitle"
		const val TOAST_DESCRIPTION_NBT = "ToastDescription"
		const val OWNER_UUID_NBT = "OwnerUUID"
	}

}