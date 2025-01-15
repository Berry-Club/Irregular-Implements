package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.OnlineDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithStrings
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class OnlineDetectorMenu(
    containerId: Int,
    private val containerData: ContainerData,
    private val containerLevelAccess: ContainerLevelAccess
) : AbstractContainerMenu(ModMenuTypes.ONLINE_DETECTOR.get(), containerId), MenuWithStrings {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                SimpleContainerData(ChatDetectorBlockEntity.CONTAINER_DATA_SIZE),
                ContainerLevelAccess.NULL
            )

    companion object {
        const val USERNAME_STRING_ID = 0
    }

    override fun receiveString(stringId: Int, string: String) {
        if (stringId == USERNAME_STRING_ID) setUsername(string)
    }

    private var currentUsername: String = ""
    fun setUsername(username: String): Boolean {
        if (this.currentUsername == username) return false
        this.currentUsername = username

        this.containerLevelAccess.execute { level, pos ->
            val blockEntity = level.getBlockEntity(pos) as? OnlineDetectorBlockEntity
            blockEntity?.username = username
        }

        return true
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(containerLevelAccess, player, ModBlocks.ONLINE_DETECTOR.get())
    }
}