package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.ChatDetectorBlock
import dev.aaronhowser.mods.irregular_implements.menu.ChatDetectorMenu
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientChatDetector
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.event.ServerChatEvent
import java.util.*

class ChatDetectorBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.CHAT_DETECTOR.get(), pPos, pBlockState), MenuProvider {

    companion object {
        private val detectors: MutableSet<ChatDetectorBlockEntity> = mutableSetOf()

        fun processMessage(event: ServerChatEvent) {
            if (event.isCanceled) return

            val message = event.message
            val sender = event.player

            val iterator = detectors.iterator()

            while (iterator.hasNext()) {
                val detector = iterator.next()
                if (detector.isRemoved) {
                    iterator.remove()
                    continue
                }

                if (detector.checkMessage(sender, message)) event.isCanceled = true
            }
        }

        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: ChatDetectorBlockEntity) {
            if (blockEntity.timeOn > 0) {
                blockEntity.timeOn--

                if (blockEntity.timeOn == 0) {
                    level.setBlockAndUpdate(
                        pos,
                        state.setValue(ChatDetectorBlock.ENABLED, false)
                    )
                }
            }
        }

        const val OWNER_UUID_NBT = "OwnerUuid"
        const val STOPS_MESSAGE_NBT = "StopsMessage"
        const val MESSAGE_REGEX_NBT = "MessageRegex"

        const val CONTAINER_DATA_SIZE = 1
        const val STOPS_MESSAGE_INDEX = 0
    }

    // Defaults to a random one but gets immediately set either by loading from NBT or when it's placed
    var ownerUuid: UUID = UUID.randomUUID()

    var regexString: String = ""
        set(value) {
            field = value
            setChanged()

            sendStringUpdate()
        }

    fun sendStringUpdate() {
        val level = this.level as? ServerLevel ?: return

        ModPacketHandler.messageNearbyPlayers(
            UpdateClientChatDetector(this.regexString),
            level,
            this.blockPos.center,
            16.0
        )
    }

    var stopsMessage = false
        set(value) {
            field = value
            setChanged()
        }
    private var timeOn = 0


    /**
     * @return true if the message should be stopped
     */
    fun checkMessage(player: Player, message: Component): Boolean {
        if (this.regexString.isEmpty() || player.uuid != this.ownerUuid) return false

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
            this.blockState.setValue(ChatDetectorBlock.ENABLED, true)
        )
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val uuid = tag.getUuidOrNull(OWNER_UUID_NBT)
        if (uuid != null) this.ownerUuid = uuid

        val regex = tag.getString(MESSAGE_REGEX_NBT)
        this.regexString = regex

        val stopsMessage = tag.getBoolean(STOPS_MESSAGE_NBT)
        this.stopsMessage = stopsMessage
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putUUID(OWNER_UUID_NBT, ownerUuid)
        tag.putString(MESSAGE_REGEX_NBT, regexString)
        tag.putBoolean(STOPS_MESSAGE_NBT, stopsMessage)
    }

    override fun onLoad() {
        super.onLoad()
        if (!this.level?.isClientSide.isTrue) detectors.add(this)
    }

    // Menu stuff

    private val containerData = object : SimpleContainerData(1) {
        override fun set(index: Int, value: Int) {
            when (index) {
                STOPS_MESSAGE_INDEX -> this@ChatDetectorBlockEntity.stopsMessage = value == 1
                else -> error("Unknown index: $index")
            }
        }

        override fun get(index: Int): Int {
            return when (index) {
                STOPS_MESSAGE_INDEX -> if (this@ChatDetectorBlockEntity.stopsMessage) 1 else 0
                else -> error("Unknown index: $index")
            }
        }
    }

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
        return ChatDetectorMenu(containerId, this.containerData, ContainerLevelAccess.create(this.level!!, this.blockPos))
    }

    override fun getDisplayName(): Component {
        return this.blockState.block.name
    }

    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)


}