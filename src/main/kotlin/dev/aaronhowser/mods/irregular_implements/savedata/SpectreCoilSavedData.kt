package dev.aaronhowser.mods.irregular_implements.savedata

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import net.neoforged.neoforge.energy.IEnergyStorage
import java.util.*
import javax.swing.text.html.parser.Entity

class SpectreCoilSavedData : SavedData() {

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

        private fun get(level: ServerLevel): SpectreCoilSavedData {
            require(level == level.server.overworld()) { "SpectreCoilSavedData can only be accessed on the overworld" }

            return level.dataStorage.computeIfAbsent(
                Factory(::SpectreCoilSavedData, Companion::load),
                "spectre_coil"
            )
        }

        val ServerLevel.spectreCoilSavedData: SpectreCoilSavedData
            inline get() = this.server.spectreCoilSavedData

        val MinecraftServer.spectreCoilSavedData: SpectreCoilSavedData
            get() = get(this.overworld())

        const val COIL_ENTRIES_NBT = "coil_entries"
        const val UUID_NBT = "uuid"
        const val ENERGY_NBT = "energy"

        const val MAX_ENERGY = 1_000_000
    }

    private val coilEntries: MutableMap<UUID, Int> = mutableMapOf()

    fun getStorage(ownerUuid: UUID): IEnergyStorage {
        return object : IEnergyStorage {
            override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
                val currentEnergy = coilEntries.getOrDefault(ownerUuid, 0)
                val newEnergy = currentEnergy + toReceive

                if (!simulate) {
                    coilEntries[ownerUuid] = newEnergy
                }

                return newEnergy - currentEnergy
            }

            override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
                val currentEntity = coilEntries.getOrDefault(ownerUuid, 0)
                val newEnergy = maxOf(currentEntity - toExtract, 0)

                if (!simulate) {
                    coilEntries[ownerUuid] = newEnergy
                }

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
                return true
            }

        }
    }

    fun getStorage(ownerEntity: Entity): IEnergyStorage {
        return getStorage(ownerEntity)
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
}