package dev.aaronhowser.mods.irregular_implements.handler.floo

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import java.util.*

class FlooFireplace(
	val masterUuid: UUID,
	val name: String,
	val masterBlockPos: BlockPos
) {

	fun toTag(): CompoundTag {
		val tag = CompoundTag()
		tag.putUUID(NBT_MASTER_UUID, masterUuid)
		tag.putString(NBT_NAME, name)
		tag.putLong(NBT_BLOCK_POS, masterBlockPos.asLong())

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

	fun collectBrickLocations(level: ServerLevel, fireplace: FlooFireplace): List<BlockPos> {
		val toCheck = mutableSetOf(fireplace.masterBlockPos)
		val checked = mutableSetOf<BlockPos>()
		val found = mutableSetOf<BlockPos>()

		while (toCheck.isNotEmpty()) {
			if (checked.size >= 1000) return found.toList()

			val pos = toCheck.first()
			val state = level.getBlockState(pos)

			checked.add(pos)

			if (state.`is`(ModBlocks.FLOO_BRICK)) {
				found.add(pos)

				for (dir in Direction.entries) {
					val offset = pos.relative(dir)
					if (offset in checked) continue
					toCheck.add(offset)
				}
			}
		}

		return found.toList()
	}

	companion object {
		const val NBT_MASTER_UUID = "MasterUUID"
		const val NBT_NAME = "Name"
		const val NBT_BLOCK_POS = "MasterBlockPos"

		fun fromTag(tag: CompoundTag): FlooFireplace {
			val uuid = tag.getUUID(NBT_MASTER_UUID)
			val name = tag.getString(NBT_NAME)
			val blockPos = BlockPos.of(tag.getLong(NBT_BLOCK_POS))

			return FlooFireplace(uuid, name, blockPos)
		}

	}

}