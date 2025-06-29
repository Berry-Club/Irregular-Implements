package dev.aaronhowser.mods.irregular_implements.handler.floo

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import java.util.*

class FlooFireplace(
	val masterUuid: UUID,
	val name: String,
	val blockPos: BlockPos
) {

	fun toTag(): CompoundTag {
		val tag = CompoundTag()
		tag.putUUID("masterUuid", masterUuid)
		tag.putString("name", name)
		tag.putLong("blockPos", blockPos.asLong())

		return tag
	}

	fun teleportFrom(player: ServerPlayer, destination: String): Boolean {

		if (destination == name) {
			player.sendSystemMessage(Component.literal("You are already at '$name'"))
			return false
		}

		val data = FlooNetworkSavedData.get(player.serverLevel())
		val fireplace = data.findFireplace(destination)

		if (fireplace == null) {
			player.sendSystemMessage(Component.literal("Could not find fireplace named '$destination'"))
			return false
		}

		return true
	}

	companion object {
		const val NBT_MASTER_UUID = "MasterUUID"
		const val NBT_NAME = "Name"
		const val NBT_BLOCK_POS = "BlockPos"

		fun fromTag(tag: CompoundTag): FlooFireplace {
			val uuid = tag.getUUID(NBT_MASTER_UUID)
			val name = tag.getString(NBT_NAME)
			val blockPos = BlockPos.of(tag.getLong(NBT_BLOCK_POS))

			return FlooFireplace(uuid, name, blockPos)
		}

	}

}