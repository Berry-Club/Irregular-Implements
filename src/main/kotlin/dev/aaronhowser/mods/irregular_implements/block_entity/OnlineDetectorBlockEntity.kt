package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.aaron.packet.s2c.UpdateClientScreenString
import dev.aaronhowser.mods.irregular_implements.block.OnlineDetectorBlock
import dev.aaronhowser.mods.irregular_implements.menu.online_detector.OnlineDetectorMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class OnlineDetectorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : SyncingBlockEntity(ModBlockEntityTypes.ONLINE_DETECTOR.get(), pos, blockState), MenuProvider {

	var username: String = ""
		set(value) {
			field = value
			setChanged()

			if (level is ServerLevel) {
				checkPlayerOnline(
					level as ServerLevel,
					blockPos,
					blockState,
					this
				)
			}

			sendStringUpdate()
		}

	fun sendStringUpdate() {
		val level = level as? ServerLevel ?: return

		val packet = UpdateClientScreenString(OnlineDetectorMenu.USERNAME_STRING_ID, username)
		packet.messageNearbyPlayers(level, blockPos.center, 16.0)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putString(USERNAME_NBT, username)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		username = tag.getString(USERNAME_NBT)
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return OnlineDetectorMenu(containerId, ContainerLevelAccess.create(level!!, blockPos))
	}

	override fun getDisplayName(): Component {
		return blockState.block.name
	}

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