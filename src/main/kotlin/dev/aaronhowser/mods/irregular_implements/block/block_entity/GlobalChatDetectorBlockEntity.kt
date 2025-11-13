package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaron.AaronExtensions.isTrue
import dev.aaronhowser.mods.irregular_implements.block.GlobalChatDetectorBlock
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.ImprovedSimpleContainer
import dev.aaronhowser.mods.irregular_implements.menu.global_chat_detector.GlobalChatDetectorMenu
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientScreenString
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.event.ServerChatEvent

class GlobalChatDetectorBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntityTypes.GLOBAL_CHAT_DETECTOR.get(), pPos, pBlockState), MenuProvider {

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

		val packet = UpdateClientScreenString(GlobalChatDetectorMenu.REGEX_STRING_ID, this.regexString)
		packet.messageNearbyPlayers(level, this.blockPos.center, 16.0)
	}

	/**
	 * @return true if the message should be stopped
	 */
	fun processMessage(player: Player, message: Component): Boolean {
		if (this.regexString.isEmpty()) return false

		val messageString = message.string
		val regex = this.regexString.toRegex()

		if (regex.containsMatchIn(messageString)) {
			pulse()
			if (this.stopsMessage) {
				for (item in this.container.items) {
					if (!item.`is`(ModItems.PLAYER_FILTER)) continue

					val playerUuid = item.get(ModDataComponents.PLAYER)?.uuid ?: continue
					if (playerUuid == player.uuid) return true
				}
			}
		}

		return false
	}

	private fun pulse() {
		this.timeOn = 20 * 3

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

		ContainerHelper.loadAllItems(tag, this.container.items, registries)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putString(MESSAGE_REGEX_NBT, regexString)
		tag.putBoolean(STOPS_MESSAGE_NBT, stopsMessage)

		ContainerHelper.saveAllItems(tag, this.container.items, registries)
	}

	override fun onLoad() {
		super.onLoad()
		if (!this.level?.isClientSide.isTrue()) globalDetectors.add(this)
	}

	// Menu stuff

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return GlobalChatDetectorMenu(
			containerId,
			playerInventory,
			this.container,
			this.containerData,
			ContainerLevelAccess.create(this.level!!, this.blockPos)
		)
	}

	override fun getDisplayName(): Component {
		return this.blockState.block.name
	}

	val container = ImprovedSimpleContainer(this, 9)

	private val containerData = object : ContainerData {
		override fun set(index: Int, value: Int) {
			when (index) {
				STOPS_MESSAGE_INDEX -> this@GlobalChatDetectorBlockEntity.stopsMessage = value == 1
				else -> error("Unknown index: $index")
			}
		}

		override fun get(index: Int): Int {
			return when (index) {
				STOPS_MESSAGE_INDEX -> if (this@GlobalChatDetectorBlockEntity.stopsMessage) 1 else 0
				else -> error("Unknown index: $index")
			}
		}

		override fun getCount(): Int = CONTAINER_DATA_SIZE
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

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

				if (detector.processMessage(sender, message)) event.isCanceled = true
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

		const val CONTAINER_DATA_SIZE = 1
		const val STOPS_MESSAGE_INDEX = 0
	}

}