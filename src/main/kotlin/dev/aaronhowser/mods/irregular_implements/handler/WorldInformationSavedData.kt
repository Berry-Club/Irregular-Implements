package dev.aaronhowser.mods.irregular_implements.handler

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData

class WorldInformationSavedData : SavedData() {

	var enderDragonKilled: Boolean = false
		set(value) {
			field = value
			setDirty()
		}

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		tag.putBoolean(ENDER_DRAGON_KILLED, enderDragonKilled)

		return tag
	}

	companion object {
		private const val ENDER_DRAGON_KILLED = "ender_dragon_killed"

		private fun load(pTag: CompoundTag, provider: HolderLookup.Provider): WorldInformationSavedData {
			val worldInformation = WorldInformationSavedData()

			worldInformation.enderDragonKilled = pTag.getBoolean(ENDER_DRAGON_KILLED)

			return worldInformation
		}

		fun get(level: ServerLevel): WorldInformationSavedData {
			if (level != level.server.overworld()) {
				return get(level.server.overworld())
			}

			return level.dataStorage.computeIfAbsent(
				Factory(::WorldInformationSavedData, ::load),
				"world_information"
			)
		}

	}
}