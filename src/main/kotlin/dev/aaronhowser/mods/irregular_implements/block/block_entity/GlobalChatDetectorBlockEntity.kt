package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.GlobalChatDetectorBlock
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientChatDetector
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.event.ServerChatEvent

class GlobalChatDetectorBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.GLOBAL_CHAT_DETECTOR.get(), pPos, pBlockState), MenuProvider {

    companion object {

        private val globalDetectors: MutableSet<GlobalChatDetectorBlockEntity> = mutableSetOf()

        fun processMessage(event: ServerChatEvent) {
            if (event.isCanceled) return

            val message = event.message
            val sender = event.player

            val iterator = globalDetectors.iterator()

            while (iterator.hasNext()) {
                val detector = iterator.next()
                if (detector.isRemoved) {
                    iterator.remove()
                    continue
                }

                if (detector.checkMessage(sender, message)) event.isCanceled = true
            }
        }

        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: GlobalChatDetectorBlockEntity) {
            if (blockEntity.timeOn > 0) {
                blockEntity.timeOn--

                if (blockEntity.timeOn == 0) {
                    level.setBlockAndUpdate(
                        pos,
                        state.setValue(GlobalChatDetectorBlock.ENABLED, false)
                    )
                }
            }
        }

        const val STOPS_MESSAGE_NBT = "StopsMessage"
        const val MESSAGE_REGEX_NBT = "MessageRegex"
    }

    var regexString: String = ""
        set(value) {
            field = value
            setChanged()

            sendStringUpdate()
        }

    var stopsMessage = false
        set(value) {
            field = value
            setChanged()
        }

    private var timeOn = 0

    fun sendStringUpdate() {
        val level = this.level as? ServerLevel ?: return

        ModPacketHandler.messageNearbyPlayers(
            UpdateClientChatDetector(this.regexString),
            level,
            this.blockPos.center,
            16.0
        )
    }

    /**
     * @return true if the message should be stopped
     */
    fun checkMessage(player: Player, message: Component): Boolean {
        if (this.regexString.isEmpty()) return false

        val messageString = message.string
        val regex = this.regexString.toRegex()

        if (regex.containsMatchIn(messageString)) {
            pulse()
            if (this.stopsMessage) return true
        }

        return false
    }

    private fun pulse() {
        this.timeOn = 20 * 3    //TODO: Some way to configure this

        this.level?.setBlockAndUpdate(
            this.blockPos,
            this.blockState.setValue(GlobalChatDetectorBlock.ENABLED, true)
        )
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val regex = tag.getString(MESSAGE_REGEX_NBT)
        this.regexString = regex

        val stopsMessage = tag.getBoolean(STOPS_MESSAGE_NBT)
        this.stopsMessage = stopsMessage
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putString(MESSAGE_REGEX_NBT, regexString)
        tag.putBoolean(STOPS_MESSAGE_NBT, stopsMessage)
    }

    override fun onLoad() {
        super.onLoad()
        if (!this.level?.isClientSide.isTrue) globalDetectors.add(this)
    }

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu? {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): Component {
        return this.blockState.block.name
    }
}