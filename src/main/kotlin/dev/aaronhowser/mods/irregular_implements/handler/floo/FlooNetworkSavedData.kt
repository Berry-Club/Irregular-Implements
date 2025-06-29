package dev.aaronhowser.mods.irregular_implements.handler.floo

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import java.util.*

class FlooNetworkSavedData : SavedData() {

	val fireplaces: MutableList<FlooFireplace> = mutableListOf()

	fun addFireplace(fireplace: FlooFireplace) {
		fireplaces.add(fireplace)
		setDirty()
	}

	fun addFireplace(masterUuid: UUID, name: String?, blockPos: BlockPos) {
		addFireplace(FlooFireplace(masterUuid, name, blockPos))
	}

	fun createFireplace(
		masterUuid: UUID,
		name: String?,
		blockPos: BlockPos,
		connectedBricks: List<BlockPos>
	): Boolean {
		for (fp in fireplaces) {
			if (connectedBricks.contains(fp.blockPos)
				|| fp.name != null && fp.name.lowercase() == name?.lowercase()
			) return false
		}

		addFireplace(masterUuid, name, blockPos)

		return true
	}

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		val fireplaceList = tag.getList(NBT_FIREPLACES, Tag.TAG_LIST.toInt())

		for (fireplace in fireplaces) {
			fireplaceList.add(fireplace.toTag())
		}

		tag.put(NBT_FIREPLACES, fireplaceList)

		return tag
	}

	companion object {
		const val NBT_FIREPLACES = "Fireplaces"

		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): FlooNetworkSavedData {
			val data = FlooNetworkSavedData()

			val fireplaceList = tag.getList(NBT_FIREPLACES, Tag.TAG_COMPOUND.toInt())
			for (i in fireplaceList.indices) {
				val fireplaceTag = fireplaceList.getCompound(i)
				data.fireplaces.add(FlooFireplace.fromTag(fireplaceTag))
			}

			return data
		}

		private fun get(level: ServerLevel): FlooNetworkSavedData {
			return level.dataStorage.computeIfAbsent(
				Factory(::FlooNetworkSavedData, ::load),
				"floo_network"
			)
		}

	}

}