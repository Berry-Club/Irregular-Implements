package dev.aaronhowser.mods.irregular_implements.savedata

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData

class WorldInformationSavedData : SavedData() {

	companion object {
		private const val ENDER_DRAGON_KILLED = "ender_dragon_killed"

		private fun load(pTag: CompoundTag, provider: HolderLookup.Provider): WorldInformationSavedData {
			val worldInformation = WorldInformationSavedData()

			worldInformation.enderDragonKilled = pTag.getBoolean(ENDER_DRAGON_KILLED)

			return worldInformation
		}

		private fun get(level: ServerLevel): WorldInformationSavedData {
			require(level == level.server.overworld()) { "RedstoneSignalSavedData can only be accessed on the overworld" }

			return level.dataStorage.computeIfAbsent(
				Factory(::WorldInformationSavedData, ::load),
				"redstone_handler"
			)
		}

		val ServerLevel.worldInformationSavedData: WorldInformationSavedData
			inline get() = this.server.worldInformationSavedData

		val MinecraftServer.worldInformationSavedData: WorldInformationSavedData
			get() = get(this.overworld())

	}

	var enderDragonKilled: Boolean = false
		set(value) {
			field = value
			setDirty()
		}

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		tag.putBoolean(ENDER_DRAGON_KILLED, enderDragonKilled)

		return tag
	}
}