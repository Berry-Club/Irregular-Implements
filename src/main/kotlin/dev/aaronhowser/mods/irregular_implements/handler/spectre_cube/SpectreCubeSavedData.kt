package dev.aaronhowser.mods.irregular_implements.handler.spectre_cube

import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModDimensions
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
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

	companion object {
		const val CUBES_NBT = "cubes"
		const val CUBE_NBT = "cube"
		const val UUID_NBT = "uuid"
		const val POSITION_COUNTER_NBT = "position_counter"

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