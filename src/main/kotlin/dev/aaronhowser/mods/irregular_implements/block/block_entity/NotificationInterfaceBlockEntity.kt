package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
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

    private var ownerUuid: UUID = UUID.randomUUID()
        set(value) {
            field = value
            setChanged()
        }

    private var toastTitle: String = ""
        set(value) {
            field = value
            setChanged()
        }

    private var toastDescription: String = ""
        set(value) {
            field = value
            setChanged()
        }

    private val container = SimpleContainer(1)

    private var icon: ItemStack
        get() = container.getItem(0)
        set(value) {
            container.setItem(0, value)
            setChanged()
        }

    fun notify() {
        val level = this.level as? ServerLevel ?: return
        val owner = level.server.playerList.getPlayer(ownerUuid) ?: return
    }

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu? {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): Component {
        return this.blockState.block.name
    }
}