package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.OnlineDetectorBlock
import dev.aaronhowser.mods.irregular_implements.menu.online_detector.OnlineDetectorMenu
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientScreenString
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
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
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class OnlineDetectorBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntityTypes.ONLINE_DETECTOR.get(), pPos, pBlockState), MenuProvider {

	var username: String = ""
		set(value) {
			field = value
			setChanged()

			if (this.level is ServerLevel) {
				checkPlayerOnline(
					this.level as ServerLevel,
					this.blockPos,
					this.blockState,
					this
				)
			}

			sendStringUpdate()
		}

	fun sendStringUpdate() {
		val level = this.level as? ServerLevel ?: return

		val packet = UpdateClientScreenString(OnlineDetectorMenu.USERNAME_STRING_ID, this.username)
		packet.messageNearbyPlayers(level, this.blockPos.center, 16.0)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putString(USERNAME_NBT, this.username)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		this.username = tag.getString(USERNAME_NBT)
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return OnlineDetectorMenu(containerId, ContainerLevelAccess.create(level!!, blockPos))
	}

	override fun getDisplayName(): Component {
		return this.blockState.block.name
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		const val USERNAME_NBT = "Username"

		fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: OnlineDetectorBlockEntity) {
			if (level !is ServerLevel || level.gameTime % 20 != 0L) return

			checkPlayerOnline(level, pos, state, blockEntity)
		}

		private fun checkPlayerOnline(level: ServerLevel, pos: BlockPos, state: BlockState, blockEntity: OnlineDetectorBlockEntity) {
			val username = blockEntity.username
			val playerOnline = level.server.playerList.getPlayerByName(username) != null

			if (playerOnline != state.getValue(OnlineDetectorBlock.ENABLED)) {
				level.setBlockAndUpdate(
					pos,
					state.setValue(OnlineDetectorBlock.ENABLED, playerOnline)
				)
			}
		}
	}

}