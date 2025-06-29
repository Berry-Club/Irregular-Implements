package dev.aaronhowser.mods.irregular_implements.handler.floo

import dev.aaronhowser.mods.irregular_implements.block.block_entity.FlooBrickBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
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

	fun getBlockEntity(level: Level): FlooBrickBlockEntity? {
		return level.getBlockEntity(masterBlockPos) as? FlooBrickBlockEntity?
	}

	fun teleportFrom(player: ServerPlayer, target: String): Boolean {
		if (target == name) {
			player.sendSystemMessage(Component.literal("You are already at '$name'"))
			return false
		}

		val level = player.serverLevel()

		val data = FlooNetworkSavedData.get(level)
		val fireplace = data.findFireplace(target)

		if (fireplace == null) {
			player.sendSystemMessage(Component.literal("Could not find fireplace named '$target'"))
			return false
		}

		val be = level.getBlockEntity(fireplace.masterBlockPos)
		if (be !is FlooBrickBlockEntity) {
			player.sendSystemMessage(Component.literal("The fireplace at '$target' is not properly constructed"))
			return false
		}

		val destination = fireplace.getDestination(level)
		if (destination == null) {
			player.sendSystemMessage(Component.literal("The fireplace at '$target' is not properly constructed"))
			return false
		}

		player.teleportTo(
			level,
			destination.x, destination.y, destination.z,
			be.facing.toYRot(), 0f
		)

		return true
	}

	fun getDestination(level: ServerLevel): Vec3? {
		val be = getBlockEntity(level) ?: return null
		val locations = be.children + masterBlockPos

		val centers = locations.map(BlockPos::center)
		val avgX = centers.sumOf { it.x } / centers.size
		val avgZ = centers.sumOf { it.z } / centers.size

		val centerPos = Vec3(avgX, centers.first.y, avgZ)

		val stateThere = level.getBlockState(BlockPos.containing(centerPos))
		if (!stateThere.`is`(ModBlocks.FLOO_BRICK)) return locations.first().above().bottomCenter

		return centerPos.add(0.0, 0.5, 0.0)
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