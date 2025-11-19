package dev.aaronhowser.mods.irregular_implements.handler

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import net.neoforged.neoforge.energy.IEnergyStorage
import java.util.*

class SpectreEnergyHandler : SavedData() {

	private val playerStoredEnergy: MutableMap<UUID, Int> = mutableMapOf()

	private val cachedEnergyInjectors: MutableMap<UUID, IEnergyStorage> = mutableMapOf()
	private val cachedCoils: MutableMap<UUID, IEnergyStorage> = mutableMapOf()

	fun getEnergyInjector(ownerUuid: UUID): IEnergyStorage {
		val existing = cachedEnergyInjectors[ownerUuid]
		if (existing != null) return existing

		val new = object : IEnergyStorage {
			override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
				val currentEnergy = playerStoredEnergy.getOrDefault(ownerUuid, 0)
				val max = getMaxEnergyStored()

				val newEnergy = (currentEnergy.toLong() + toReceive.toLong()).coerceIn(0L, max.toLong()).toInt()
				val actualReceived = newEnergy - currentEnergy

				if (!simulate) {
					playerStoredEnergy[ownerUuid] = newEnergy
				}

				this@SpectreEnergyHandler.setDirty()
				return actualReceived
			}

			override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
				return 0
			}

			override fun getEnergyStored(): Int {
				return playerStoredEnergy.getOrDefault(ownerUuid, 0)
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

		cachedEnergyInjectors[ownerUuid] = new

		return new
	}

	fun getCoil(ownerUuid: UUID): IEnergyStorage {
		val existing = cachedCoils[ownerUuid]
		if (existing != null) return existing

		val new = object : IEnergyStorage {
			override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
				return 0
			}

			override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
				val currentEntity = playerStoredEnergy.getOrDefault(ownerUuid, 0)
				val newEnergy = maxOf(
					0,
					currentEntity - toExtract
				)

				if (!simulate) {
					playerStoredEnergy[ownerUuid] = newEnergy
				}

				this@SpectreEnergyHandler.setDirty()

				return currentEntity - newEnergy
			}

			override fun getEnergyStored(): Int {
				return playerStoredEnergy.getOrDefault(ownerUuid, 0)
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

		cachedCoils[ownerUuid] = new
		return new
	}

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		val listTag = tag.getList(PLAYER_ENERGIES_NBT, Tag.TAG_COMPOUND.toInt())

		for ((uuid, energy) in this.playerStoredEnergy) {
			val entryTag = CompoundTag()
			entryTag.putString(UUID_NBT, uuid.toString())
			entryTag.putInt(ENERGY_NBT, energy)

			listTag.add(entryTag)
		}

		return tag
	}

	companion object {
		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): SpectreEnergyHandler {
			val spectreEnergyHandler = SpectreEnergyHandler()

			val newListTag = tag.getList(PLAYER_ENERGIES_NBT, Tag.TAG_COMPOUND.toInt())
			val oldListTag = tag.getList(PLAYER_ENERGIES_NBT_OLD, Tag.TAG_COMPOUND.toInt())

			val listTag = newListTag.ifEmpty { oldListTag }

			for (i in 0 until listTag.count()) {
				val entryTag = listTag.getCompound(i)

				val uuid = UUID.fromString(entryTag.getString(UUID_NBT))
				val energy = entryTag.getInt(ENERGY_NBT)

				spectreEnergyHandler.playerStoredEnergy[uuid] = energy
			}

			return spectreEnergyHandler
		}

		fun get(level: ServerLevel): SpectreEnergyHandler {
			if (level != level.server.overworld()) {
				return get(level.server.overworld())
			}

			val storage = level.dataStorage
			val factory = Factory(::SpectreEnergyHandler, ::load)

			val existing = storage.get(factory, SAVED_DATA_NAME) ?: storage.get(factory, OLD_SAVED_DATA_NAME)

			return existing ?: storage.computeIfAbsent(
				Factory(::SpectreEnergyHandler, ::load),
				SAVED_DATA_NAME
			)
		}

		const val OLD_SAVED_DATA_NAME = "spectre_coil"
		const val SAVED_DATA_NAME = "ii_spectre_energy"

		const val PLAYER_ENERGIES_NBT = "player_energies"
		const val PLAYER_ENERGIES_NBT_OLD = "coil_entries"

		const val UUID_NBT = "uuid"
		const val ENERGY_NBT = "energy"

		val MAX_ENERGY: Int
			get() = ServerConfig.CONFIG.spectreBufferCapacity.get()
	}

}