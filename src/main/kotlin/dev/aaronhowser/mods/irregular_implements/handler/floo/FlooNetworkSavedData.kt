package dev.aaronhowser.mods.irregular_implements.handler.floo

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import org.jline.utils.Levenshtein
import java.util.*

class FlooNetworkSavedData : SavedData() {

	val fireplaces: MutableList<FlooFireplace> = mutableListOf()

	fun addFireplace(fireplace: FlooFireplace) {
		fireplaces.add(fireplace)
		setDirty()
	}

	fun addFireplace(masterUuid: UUID, name: String, blockPos: BlockPos) {
		addFireplace(FlooFireplace(masterUuid, name, blockPos))
	}

	fun createFireplace(
		masterUuid: UUID,
		name: String?,
		blockPos: BlockPos,
		connectedBricks: List<BlockPos>
	): Boolean {
		if (name.isNullOrBlank()) return false
		if (fireplaces.any { connectedBricks.contains(it.masterBlockPos) || it.name.lowercase() == name.lowercase() }) return false

		addFireplace(masterUuid, name, blockPos)

		return true
	}

	fun findFireplace(name: String): FlooFireplace? {
		return fireplaces.minByOrNull { Levenshtein.distance(it.name.lowercase(), name.lowercase()) }
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

		fun get(level: ServerLevel): FlooNetworkSavedData {
			return level.dataStorage.computeIfAbsent(
				Factory(::FlooNetworkSavedData, ::load),
				"floo_network"
			)
		}

	}

}