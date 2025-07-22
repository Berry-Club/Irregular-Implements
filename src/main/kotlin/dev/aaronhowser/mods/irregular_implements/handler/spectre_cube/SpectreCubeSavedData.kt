package dev.aaronhowser.mods.irregular_implements.handler.spectre_cube

import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModDimensions
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.saveddata.SavedData
import java.util.*

class SpectreCubeSavedData : SavedData() {

	private val cubes: MutableMap<UUID, SpectreCube> = mutableMapOf()
	private var positionCounter: Int = 0

	var spectreLevel: Level? = null

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		val listTag = tag.getList(CUBES_NBT, Tag.TAG_COMPOUND.toInt())

		for ((uuid, cube) in cubes) {
			val cubeTag = CompoundTag()
			cubeTag.putUUID(UUID_NBT, uuid)
			cubeTag.put(CUBE_NBT, cube.toTag())
			listTag.add(cubeTag)
		}

		tag.put(CUBES_NBT, listTag)
		tag.putInt(POSITION_COUNTER_NBT, positionCounter)

		return tag
	}

	fun teleportPlayerToSpectreCube(player: ServerPlayer) {
		val spectreLevel = this@SpectreCubeSavedData.spectreLevel as? ServerLevel ?: return

		val pData = player.persistentData

		val tag = CompoundTag()
		tag.putDouble(FROM_X, player.x)
		tag.putDouble(FROM_Y, player.y)
		tag.putDouble(FROM_Z, player.z)
		tag.putString(FROM_DIMENSION, player.level().dimension().location().toString())

		pData.put(PLAYER_SPECTRE_INFO, tag)

		val uuid = player.uuid
		val cube = cubes[uuid] ?: generateSpectreCube(uuid)
		val spawnPos = cube.getSpawnPos()

		player.teleportTo(
			spectreLevel,
			spawnPos.x + 0.5,
			spawnPos.y + 1.0,
			spawnPos.z + 0.5,
			player.yRot,
			player.xRot
		)
	}

	private fun generateSpectreCube(uuid: UUID): SpectreCube {

	}

	fun getSpectreCubeFromBlockPos(level: Level, pos: BlockPos): SpectreCube? {
		if (level.dimension() != ModDimensions.SPECTRE_LEVEL_KEY) return null

		if (pos.z > 16 || pos.z < 0) return null

		val chunk = level.getChunkAt(pos)
		val position = chunk.pos.x

		for (cube in cubes.values) {
			if (cube.position / 16 != position) continue

			if (pos.y <= 0
				|| pos.y > cube.height + 1
				|| pos.x < position * 16
				|| pos.x >= position * 16 + 15
			) return null

			return cube
		}

		return null
	}

	fun verifyPosition(player: ServerPlayer) {
		if (player.isCreative) return

		val pos = player.blockPosition()
		val cube = getSpectreCubeFromBlockPos(player.serverLevel(), pos) ?: return
		val playerUuid = player.uuid

		if (cube.owner == playerUuid) return

		val playersCube = cubes[playerUuid]
		if (playersCube == null) {
			teleportPlayerBack(player)
			return
		}

		val spawn = playersCube.getSpawnPos()
		player.teleportTo(spawn.x + 0.5, spawn.y + 1.0, spawn.z + 0.5)
	}

	fun teleportPlayerBack(player: Player) {

	}

	companion object {
		const val CUBES_NBT = "cubes"
		const val CUBE_NBT = "cube"
		const val UUID_NBT = "uuid"
		const val POSITION_COUNTER_NBT = "position_counter"

		const val PLAYER_SPECTRE_INFO = "ii_spectre_cube_info"
		const val FROM_X = "from_x"
		const val FROM_Y = "from_y"
		const val FROM_Z = "from_z"
		const val FROM_DIMENSION = "from_dimension"

		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): SpectreCubeSavedData {
			val data = SpectreCubeSavedData()

			val listTag = tag.getList(CUBES_NBT, Tag.TAG_COMPOUND.toInt())
			for (i in listTag.indices) {
				val cube = SpectreCube.fromTag(data, listTag.getCompound(i))
				val uuid = cube.owner ?: continue
				data.cubes[uuid] = cube
			}

			data.positionCounter = tag.getInt(POSITION_COUNTER_NBT)

			return data
		}

		fun get(level: ServerLevel): SpectreCubeSavedData {
			if (level != level.server.overworld()) {
				return get(level.server.overworld())
			}

			val savedData = level.dataStorage.computeIfAbsent(
				Factory(::SpectreCubeSavedData, ::load),
				"spectre_cube"
			)

			savedData.spectreLevel = level.server.getLevel(ModDimensions.SPECTRE_LEVEL_KEY)

			return savedData
		}
	}

}