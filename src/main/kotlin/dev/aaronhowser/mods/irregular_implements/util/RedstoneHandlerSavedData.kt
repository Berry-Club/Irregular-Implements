package dev.aaronhowser.mods.irregular_implements.util

import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.saveddata.SavedData

class RedstoneHandlerSavedData : SavedData() {

    companion object {
        private const val TAG_SAVED_SIGNALS = "saved_signals"

        private fun load(pTag: CompoundTag, provider: HolderLookup.Provider): RedstoneHandlerSavedData {
            val redstoneHandlerSavedData = RedstoneHandlerSavedData()

            val savedSignals = pTag.getList(TAG_SAVED_SIGNALS, Tag.TAG_COMPOUND.toInt())

            for (i in savedSignals.indices) {
                val signalTag = savedSignals.getCompound(i)
                val signal = SavedSignal.fromTag(signalTag)

                redstoneHandlerSavedData.signals.add(signal)
            }

            return redstoneHandlerSavedData
        }

        fun get(level: ServerLevel): RedstoneHandlerSavedData {
            require(level == level.server.overworld()) { "RedstoneSignalSavedData can only be accessed on the overworld" }

            return level.dataStorage.computeIfAbsent(
                Factory(::RedstoneHandlerSavedData, ::load),
                "redstone_handler"
            )
        }
    }

    private val signals: MutableSet<SavedSignal> = mutableSetOf()

    override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
        val signalsTag = tag.getList(TAG_SAVED_SIGNALS, Tag.TAG_COMPOUND.toInt())

        for (signal in signals) {
            signalsTag.add(signal.toTag())
        }

        tag.put(TAG_SAVED_SIGNALS, signalsTag)

        return tag
    }

    data class SavedSignal(
        val blockPos: Long,
        val dimension: ResourceKey<Level>,
        val duration: Int,
        val strength: Int,
        val startTick: Long
    ) {
        companion object {
            const val TAG_BLOCK_POS = "block_pos"
            const val TAG_DIMENSION = "dimension"
            const val TAG_DURATION = "duration"
            const val TAG_STRENGTH = "strength"
            const val TAG_START_TICK = "start_tick"

            fun fromTag(tag: CompoundTag): SavedSignal {
                val blockPos = tag.getLong(TAG_BLOCK_POS)
                val dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(tag.getString(TAG_DIMENSION)))
                val duration = tag.getInt(TAG_DURATION)
                val strength = tag.getInt(TAG_STRENGTH)
                val startTick = tag.getLong(TAG_START_TICK)

                return SavedSignal(blockPos, dimension, duration, strength, startTick)
            }
        }

        fun toTag(): CompoundTag {
            val tag = CompoundTag()

            tag.putLong(TAG_BLOCK_POS, blockPos)
            tag.putString(TAG_DIMENSION, dimension.location().toString())
            tag.putInt(TAG_DURATION, duration)
            tag.putInt(TAG_STRENGTH, strength)
            tag.putLong(TAG_START_TICK, startTick)

            return tag
        }

        fun isExpired(currentTick: Long): Boolean {
            return currentTick - startTick >= duration
        }
    }

}