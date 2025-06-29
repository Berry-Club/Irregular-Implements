package dev.aaronhowser.mods.irregular_implements.handler.floo

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.saveddata.SavedData

class FlooNetworkSavedData : SavedData() {



	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		return tag
	}
}