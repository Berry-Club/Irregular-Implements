package dev.aaronhowser.mods.irregular_implements.handler

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import net.neoforged.neoforge.energy.IEnergyStorage
import java.util.*

class SpectreCoilSavedData : SavedData() {

	private val coilEntries: MutableMap<UUID, Int> = mutableMapOf()
	private val energyInjectors: MutableMap<UUID, IEnergyStorage> = mutableMapOf()

	fun getEnergyInjector(ownerUuid: UUID): IEnergyStorage {
		val existing = energyInjectors[ownerUuid]
		if (existing != null) return existing

		val new = object : IEnergyStorage {
			override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
				val currentEnergy = coilEntries.getOrDefault(ownerUuid, 0)
				val newEnergy = minOf(
					this.maxEnergyStored,
					currentEnergy + toReceive
				)

				if (!simulate) {
					coilEntries[ownerUuid] = newEnergy
				}

				this@SpectreCoilSavedData.setDirty()

				return newEnergy - currentEnergy
			}

			override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
				return 0
			}

			override fun getEnergyStored(): Int {
				return coilEntries.getOrDefault(ownerUuid, 0)
			}

			override fun getMaxEnergyStored(): Int {
				return MAX_ENERGY
			}

			override fun canExtract(): Boolean {
				return false
			}

			override fun canReceive(): Boolean {
				return true
			}
		}

		energyInjectors[ownerUuid] = new

		return new
	}

	fun getCoil(ownerUuid: UUID): IEnergyStorage {
		return object : IEnergyStorage {
			override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
				return 0
			}

			override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
				val currentEntity = coilEntries.getOrDefault(ownerUuid, 0)
				val newEnergy = maxOf(
					0,
					currentEntity - toExtract
				)

				if (!simulate) {
					coilEntries[ownerUuid] = newEnergy
				}

				this@SpectreCoilSavedData.setDirty()

				return currentEntity - newEnergy
			}

			override fun getEnergyStored(): Int {
				return coilEntries.getOrDefault(ownerUuid, 0)
			}

			override fun getMaxEnergyStored(): Int {
				return MAX_ENERGY
			}

			override fun canExtract(): Boolean {
				return true
			}

			override fun canReceive(): Boolean {
				return false
			}
		}
	}

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		val listTag = tag.getList(COIL_ENTRIES_NBT, Tag.TAG_COMPOUND.toInt())

		for ((uuid, energy) in this.coilEntries) {
			val entryTag = CompoundTag()
			entryTag.putString(UUID_NBT, uuid.toString())
			entryTag.putInt(ENERGY_NBT, energy)

			listTag.add(entryTag)
		}

		return tag
	}

	companion object {
		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): SpectreCoilSavedData {
			val spectreCoilSavedData = SpectreCoilSavedData()

			val listTag = tag.getList(COIL_ENTRIES_NBT, Tag.TAG_COMPOUND.toInt())

			for (i in 0 until listTag.count()) {
				val entryTag = listTag.getCompound(i)

				val uuid = UUID.fromString(entryTag.getString(UUID_NBT))
				val energy = entryTag.getInt(ENERGY_NBT)

				spectreCoilSavedData.coilEntries[uuid] = energy
			}

			return spectreCoilSavedData
		}

		fun get(level: ServerLevel): SpectreCoilSavedData {
			if (level != level.server.overworld()) {
				return get(level.server.overworld())
			}

			return level.dataStorage.computeIfAbsent(
				Factory(::SpectreCoilSavedData, ::load),
				"spectre_coil"
			)
		}

		const val COIL_ENTRIES_NBT = "coil_entries"
		const val UUID_NBT = "uuid"
		const val ENERGY_NBT = "energy"

		val MAX_ENERGY: Int
			get() = ServerConfig.SPECTRE_BUFFER_CAPACITY.get()
	}

}