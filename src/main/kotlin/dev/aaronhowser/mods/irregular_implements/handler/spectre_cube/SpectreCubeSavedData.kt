package dev.aaronhowser.mods.irregular_implements.handler.spectre_cube

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import java.util.*

class SpectreCubeSavedData : SavedData() {

	private val cubes: MutableMap<UUID, SpectreCube> = mutableMapOf()
	private var positionCounter: Int = 0

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		val listTag = tag.getList(CUBES_NBT, Tag.TAG_COMPOUND.toInt())

		for ((uuid, cube) in cubes) {
			val cubeTag = CompoundTag()
			cube.save(cubeTag)
			cubeTag.putUUID("uuid", uuid)
			listTag.add(cubeTag)
		}

		tag.put(CUBES_NBT, listTag)
		tag.putInt(POSITION_COUNTER_NBT, positionCounter)

		return tag
	}

	companion object {
		const val CUBES_NBT = "cubes"
		const val POSITION_COUNTER_NBT = "position_counter"

		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): SpectreCubeSavedData {
			val data = SpectreCubeSavedData()

			val listTag = tag.getList(CUBES_NBT, Tag.TAG_COMPOUND.toInt())

			val positionCounter = tag.getInt(POSITION_COUNTER_NBT)
			data.positionCounter = positionCounter

			return data
		}

		fun get(level: ServerLevel): SpectreCubeSavedData {
			if (level != level.server.overworld()) {
				return get(level.server.overworld())
			}

			return level.dataStorage.computeIfAbsent(
				Factory(::SpectreCubeSavedData, ::load),
				"spectre_cube"
			)
		}
	}

}