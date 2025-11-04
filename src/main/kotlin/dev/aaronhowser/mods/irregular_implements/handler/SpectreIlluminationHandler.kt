package dev.aaronhowser.mods.irregular_implements.handler

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData

class SpectreIlluminationHandler : SavedData() {

	private val illuminatedChunkLongs: MutableSet<Long> = mutableSetOf()

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		tag.putLongArray(CHUNK_LONGS, illuminatedChunkLongs.toLongArray())
		return tag
	}

	companion object {
		const val CHUNK_LONGS = "chunk_longs"

		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): SpectreIlluminationHandler {
			val data = SpectreIlluminationHandler()

			val chunkLongs = tag.getLongArray(CHUNK_LONGS)
			data.illuminatedChunkLongs.addAll(chunkLongs.toList())

			return data
		}

		fun get(level: ServerLevel): SpectreIlluminationHandler {
			return level.dataStorage.computeIfAbsent(
				Factory(::SpectreIlluminationHandler, ::load),
				"spectre_illumination_handler"
			)
		}
	}


}