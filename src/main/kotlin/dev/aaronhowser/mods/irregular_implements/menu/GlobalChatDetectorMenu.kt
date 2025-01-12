package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IronDropperBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack

class GlobalChatDetectorMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val globalChatDetectorContainer: Container,
    private val containerData: ContainerData,
    private val containerLevelAccess: ContainerLevelAccess
) : AbstractContainerMenu(ModMenuTypes.GLOBAL_CHAT_DETECTOR.get(), containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(
                containerId,
                playerInventory,
                SimpleContainer(9),
                SimpleContainerData(IronDropperBlockEntity.CONTAINER_DATA_SIZE),
                ContainerLevelAccess.NULL
            )

    init {
        this.addDataSlots(containerData)
    }

    companion object {
        const val TOGGLE_MESSAGE_PASS_BUTTON_ID = 0
    }

    var shouldMessageStop: Boolean
        get() = containerData.get(ChatDetectorBlockEntity.STOPS_MESSAGE_INDEX) == 1
        set(value) = containerData.set(ChatDetectorBlockEntity.STOPS_MESSAGE_INDEX, if (value) 1 else 0)

    fun handleButtonPressed(buttonId: Int) {
        when (buttonId) {
            ChatDetectorMenu.TOGGLE_MESSAGE_PASS_BUTTON_ID -> shouldMessageStop = !shouldMessageStop
        }
    }

    private var currentRegexString: String = ""
    fun setRegex(regexString: String): Boolean {
        if (regexString == this.currentRegexString) return false
        this.currentRegexString = regexString

        this.containerLevelAccess.execute { level, pos ->
            val blockEntity = level.getBlockEntity(pos) as? ChatDetectorBlockEntity
            blockEntity?.regexString = regexString
        }

        return true
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return globalChatDetectorContainer.stillValid(player)
    }
}