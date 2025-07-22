package dev.aaronhowser.mods.irregular_implements.handler.spectre_cube

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class SpectreCube(
	val handler: SpectreCubeSavedData
) {

	var owner: UUID? = null
	val guests: MutableList<UUID> = mutableListOf()

	var height = 2
	var position = 0

	private var spawnBlock: BlockPos = BlockPos(8, 0, 8)

	fun toTag(): CompoundTag {
		val tag = CompoundTag()

		val owner = this@SpectreCube.owner
		if (owner != null) tag.putUUID(OWNER_NBT, owner)

		tag.putInt(HEIGHT_NBT, height)
		tag.putInt(POSITION_NBT, position)

		val guestList = tag.getList(GUESTS_NBT, Tag.TAG_COMPOUND.toInt())
		for (guest in guests) {
			val guestTag = CompoundTag()
			guestTag.putUUID(UUID_NBT, guest)
			guestList.add(guestTag)
		}
		tag.put(GUESTS_NBT, guestList)

		tag.putLong(SPAWN_BLOCK_NBT, spawnBlock.asLong())

		return tag
	}

	fun generate(level: Level) {
		if (level !is ServerLevel) return

		val cornerOne = BlockPos(position * 16, 0, 0)
		val cornerTwo = cornerOne.offset(15, height + 1, 15)

		generateCube(level, cornerOne, cornerTwo, ModBlocks.SPECTRE_BLOCK.get().defaultBlockState())
		generateCube(
			level,
			cornerOne.offset(7, 0, 7),
			cornerOne.offset(8, 0, 8),
			ModBlocks.SPECTRE_CORE.get().defaultBlockState()
		)
	}

	fun increaseHeight(amountToAdd: Int): Int {
		val level = handler.spectreLevel ?: return 0

		val maxToAdd = level.maxBuildHeight - (height + 1)
		val newHeight = (height + amountToAdd).coerceAtMost(height + maxToAdd)

		if (newHeight == height) return 0

		val delta = newHeight - height
		changeHeight(level, newHeight)
		return delta
	}

	private fun changeHeight(level: Level, newHeight: Int) {
		val corner = BlockPos(position * 16, 0, 0)
		generateCube(
			level,
			corner,
			corner.offset(15, height + 1, 15),
			Blocks.AIR.defaultBlockState()
		)
	}

	companion object {
		const val OWNER_NBT = "owner"
		const val GUESTS_NBT = "guests"
		const val UUID_NBT = "uuid"
		const val HEIGHT_NBT = "height"
		const val POSITION_NBT = "position"
		const val SPAWN_BLOCK_NBT = "spawn_block"

		private fun generateCube(
			level: Level,
			cornerOne: BlockPos,
			cornerTwo: BlockPos,
			state: BlockState
		) {
			val minX = minOf(cornerOne.x, cornerTwo.x)
			val minY = minOf(cornerOne.y, cornerTwo.y)
			val minZ = minOf(cornerOne.z, cornerTwo.z)
			val maxX = maxOf(cornerOne.x, cornerTwo.x)
			val maxY = maxOf(cornerOne.y, cornerTwo.y)
			val maxZ = maxOf(cornerOne.z, cornerTwo.z)

			for (x in minX..maxX) for (y in minY..maxY) for (z in minZ..maxZ) {
				if (x in setOf(minX, maxX) || y in setOf(minY, maxY) || z in setOf(minZ, maxZ)) {
					level.setBlockAndUpdate(
						BlockPos(x, y, z),
						state
					)
				}
			}
		}

		fun fromTag(handler: SpectreCubeSavedData, tag: CompoundTag): SpectreCube {
			val cube = SpectreCube(handler)
			cube.owner = tag.getUuidOrNull(OWNER_NBT)
			cube.height = tag.getInt(HEIGHT_NBT)
			cube.position = tag.getInt(POSITION_NBT)
			cube.spawnBlock = BlockPos.of(tag.getLong(SPAWN_BLOCK_NBT))

			val guestList = tag.getList(GUESTS_NBT, Tag.TAG_COMPOUND.toInt())
			for (i in guestList.indices) {
				val guestTag = guestList.getCompound(i)
				val guestUuid = guestTag.getUuidOrNull(UUID_NBT)
				if (guestUuid != null) {
					cube.guests.add(guestUuid)
				}
			}

			return cube
		}

	}

}