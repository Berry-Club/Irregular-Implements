package dev.aaronhowser.mods.irregular_implements.util

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
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

        private fun get(level: ServerLevel): RedstoneHandlerSavedData {
            require(level == level.server.overworld()) { "RedstoneSignalSavedData can only be accessed on the overworld" }

            return level.dataStorage.computeIfAbsent(
                Factory(::RedstoneHandlerSavedData, ::load),
                "redstone_handler"
            )
        }

        @JvmStatic
        val ServerLevel.redstoneHandlerSavedData: RedstoneHandlerSavedData
            inline get() = this.server.redstoneHandlerSavedData

        val MinecraftServer.redstoneHandlerSavedData: RedstoneHandlerSavedData
            get() = get(this.overworld())

        fun tick(level: Level) {
            if (level !is ServerLevel) return
            level.redstoneHandlerSavedData.tick(level.server)
        }

        fun addSignal(level: ServerLevel, blockPos: BlockPos, duration: Int, strength: Int) {
            level.redstoneHandlerSavedData.addSignal(level, blockPos, duration, strength)
        }
    }

    private val signals: MutableSet<SavedSignal> = mutableSetOf()

    private fun addSignal(level: ServerLevel, blockPos: BlockPos, duration: Int, strength: Int) {
        val signal = SavedSignal(blockPos.asLong(), level.dimension(), duration, strength, level.gameTime)

        signals.add(signal)
        updatePosition(level, blockPos)
    }

    fun tick(server: MinecraftServer) {
        val iterator = signals.iterator()

        while (iterator.hasNext()) {
            val signal = iterator.next()
            val level = server.getLevel(signal.dimension)

            if (level == null) {
                iterator.remove()
                continue
            }

            updatePosition(level, signal.blockPos)

            if (signal.isExpired(level.gameTime)) {
                iterator.remove()
            }
        }
    }

    private fun updatePosition(level: ServerLevel, blockPos: BlockPos) {
        val targetState = level.getBlockState(blockPos)

        targetState.handleNeighborChanged(level, blockPos, Blocks.AIR, blockPos, false)  //TODO: Apparently dangerous?
        level.updateNeighborsAt(blockPos, targetState.block)
    }

    private fun updatePosition(level: ServerLevel, blockPos: Long) = updatePosition(level, BlockPos.of(blockPos))

    fun getStrongPower(level: ServerLevel, blockPos: BlockPos, facing: Direction): Int {
        val pos = blockPos.relative(facing.opposite)
        val dimension = level.dimension()

        for (signal in signals) {
            if (signal.isExpired(level.gameTime)) continue
            if (signal.blockPos == pos.asLong() && signal.dimension == dimension) {
                return signal.strength
            }
        }

        return 0
    }

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