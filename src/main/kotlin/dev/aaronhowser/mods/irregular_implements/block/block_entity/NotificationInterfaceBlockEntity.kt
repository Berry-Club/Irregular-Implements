package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.menu.NotificationInterfaceMenu
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.SendClientToast
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientScreenString
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleContainer
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
) : BlockEntity(ModBlockEntities.NOTIFICATION_INTERFACE.get(), pPos, pBlockState), MenuProvider {

    companion object {
        const val TOAST_TITLE_NBT = "ToastTitle"
        const val TOAST_DESCRIPTION_NBT = "ToastDescription"
        const val OWNER_UUID_NBT = "OwnerUUID"
    }

    var ownerUuid: UUID = UUID.randomUUID()
        set(value) {
            field = value
            setChanged()
        }

    var toastTitle: String = "Title"
        set(value) {
            field = value
            setChanged()
            sendStringUpdate()
        }

    var toastDescription: String = "Description"
        set(value) {
            field = value
            setChanged()
            sendStringUpdate()
        }

    fun sendStringUpdate() {
        val level = this.level as? ServerLevel ?: return

        ModPacketHandler.messageNearbyPlayers(
            UpdateClientScreenString(NotificationInterfaceMenu.TITLE_STRING_ID, this.toastTitle),
            level,
            this.blockPos.center,
            16.0
        )

        ModPacketHandler.messageNearbyPlayers(
            UpdateClientScreenString(NotificationInterfaceMenu.DESCRIPTION_STRING_ID, this.toastDescription),
            level,
            this.blockPos.center,
            16.0
        )
    }

    private val container = SimpleContainer(1)

    private var icon: ItemStack
        get() = container.getItem(0)
        set(value) {
            container.setItem(0, value)
            setChanged()
        }

    fun notifyOwner() {
        val level = this.level as? ServerLevel ?: return
        val owner = level.server.playerList.getPlayer(this.ownerUuid) ?: return

        ModPacketHandler.messagePlayer(
            owner,
            SendClientToast(this.toastTitle, this.toastDescription, this.icon)
        )
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putUUID(OWNER_UUID_NBT, this.ownerUuid)
        tag.putString(TOAST_TITLE_NBT, this.toastTitle)
        tag.putString(TOAST_DESCRIPTION_NBT, this.toastDescription)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val uuid = tag.getUuidOrNull(OWNER_UUID_NBT)
        if (uuid != null) {
            this.ownerUuid = uuid
        }

        this.toastTitle = tag.getString(TOAST_TITLE_NBT)
        this.toastDescription = tag.getString(TOAST_DESCRIPTION_NBT)
    }

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
        return NotificationInterfaceMenu(
            containerId,
            playerInventory,
            this.container,
            ContainerLevelAccess.create(this.level!!, this.blockPos)
        )
    }

    override fun getDisplayName(): Component {
        return this.blockState.block.name
    }
}