package dev.aaronhowser.mods.irregular_implements.handler.floo

import dev.aaronhowser.mods.aaron.AaronExtensions.status
import dev.aaronhowser.mods.irregular_implements.block.block_entity.FlooBrickBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.BurningFlooFireplacePacket
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import java.util.*

class FlooFireplace(
	val masterUuid: UUID,
	val name: String?,
	val masterBlockPos: BlockPos
) {

	fun toTag(): CompoundTag {
		val tag = CompoundTag()
		tag.putUUID(NBT_MASTER_UUID, masterUuid)
		if (name != null) tag.putString(NBT_NAME, name)
		tag.putLong(NBT_BLOCK_POS, masterBlockPos.asLong())

		return tag
	}

	fun getBlockEntity(level: Level): FlooBrickBlockEntity? {
		return level.getBlockEntity(masterBlockPos) as? FlooBrickBlockEntity?
	}

	fun teleportFromThis(player: ServerPlayer, target: String): Boolean {
		val level = player.serverLevel()

		val data = FlooNetworkSavedData.get(level)
		val fireplace = data.findFireplace(target)

		if (fireplace == null) {
			player.status(ModMessageLang.FIREPLACE_NOT_FOUND.toComponent(target))
			return false
		} else if (fireplace == this) {
			val name = name ?: target
			player.status(ModMessageLang.FIREPLACE_ALREADY_AT.toComponent(name))
			return false
		}

		val success = fireplace.teleportToThis(player)

		if (success) {
			val myBe = this.getBlockEntity(level)
			if (myBe != null) {
				val bricks = myBe.children + myBe.blockPos
				val packet = BurningFlooFireplacePacket(bricks)
				packet.messageNearbyPlayers(level, this.masterBlockPos.center, 64.0)
			}
		}

		return success
	}

	fun teleportToThis(player: ServerPlayer): Boolean {
		val level = player.serverLevel()
		val be = level.getBlockEntity(this.masterBlockPos) as? FlooBrickBlockEntity

		if (be == null) {
			player.status(
				ModMessageLang.FIREPLACE_NO_LONGER_VALID.toComponent(
					masterBlockPos.x, masterBlockPos.y, masterBlockPos.z
				)
			)

			val network = FlooNetworkSavedData.get(level)
			network.removeFireplace(this.masterUuid)

			return false
		}

		val destination = this.masterBlockPos.above().bottomCenter

		player.teleportTo(
			level,
			destination.x, destination.y, destination.z,
			be.facing.toYRot(), player.xRot
		)

		if (name != null) {
			player.status(ModMessageLang.FIREPLACE_TELEPORTED.toComponent(name))
		}

		val bricks = be.children + be.blockPos
		val packet = BurningFlooFireplacePacket(bricks)
		packet.messageNearbyPlayers(level, this.masterBlockPos.center, 64.0)

		return true
	}

	companion object {
		const val NBT_MASTER_UUID = "MasterUUID"
		const val NBT_NAME = "Name"
		const val NBT_BLOCK_POS = "MasterBlockPos"

		fun fromTag(tag: CompoundTag): FlooFireplace {
			val uuid = tag.getUUID(NBT_MASTER_UUID)
			val name = tag.getString(NBT_NAME).ifBlank { null }
			val blockPos = BlockPos.of(tag.getLong(NBT_BLOCK_POS))

			return FlooFireplace(uuid, name, blockPos)
		}

	}

}