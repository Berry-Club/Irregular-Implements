package dev.aaronhowser.mods.irregular_implements.handler

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import net.neoforged.neoforge.energy.IEnergyStorage
import java.util.*

class SpectreCoilHandler : SavedData() {

	private val coilEntries: MutableMap<UUID, Int> = mutableMapOf()
	private val energyInjectors: MutableMap<UUID, IEnergyStorage> = mutableMapOf()
	private val coils: MutableMap<UUID, IEnergyStorage> = mutableMapOf()

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

				this@SpectreCoilHandler.setDirty()

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
		val existing = coils[ownerUuid]
		if (existing != null) return existing

		val new = object : IEnergyStorage {
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

				this@SpectreCoilHandler.setDirty()

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

		coils[ownerUuid] = new
		return new
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
		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): SpectreCoilHandler {
			val spectreCoilHandler = SpectreCoilHandler()

			val listTag = tag.getList(COIL_ENTRIES_NBT, Tag.TAG_COMPOUND.toInt())

			for (i in 0 until listTag.count()) {
				val entryTag = listTag.getCompound(i)

				val uuid = UUID.fromString(entryTag.getString(UUID_NBT))
				val energy = entryTag.getInt(ENERGY_NBT)

				spectreCoilHandler.coilEntries[uuid] = energy
			}

			return spectreCoilHandler
		}

		fun get(level: ServerLevel): SpectreCoilHandler {
			if (level != level.server.overworld()) {
				return get(level.server.overworld())
			}

			return level.dataStorage.computeIfAbsent(
				Factory(::SpectreCoilHandler, ::load),
				"spectre_coil"
			)
		}

		const val COIL_ENTRIES_NBT = "coil_entries"
		const val UUID_NBT = "uuid"
		const val ENERGY_NBT = "energy"

		val MAX_ENERGY: Int
			get() = ServerConfig.CONFIG.spectreBufferCapacity.get()
	}

}