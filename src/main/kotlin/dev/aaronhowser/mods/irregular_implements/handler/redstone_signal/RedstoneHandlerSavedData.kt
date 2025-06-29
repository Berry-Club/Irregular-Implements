package dev.aaronhowser.mods.irregular_implements.handler.redstone_signal

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

	private val signals: MutableSet<SavedSignal> = mutableSetOf()

	private fun addSignal(level: ServerLevel, blockPos: BlockPos, duration: Int, strength: Int) {
		signals.removeIf { it.blockPos == blockPos.asLong() && it.dimension == level.dimension() }

		val signal = SavedSignal(blockPos.asLong(), level.dimension(), duration, strength, level.gameTime)

		signals.add(signal)
		updatePosition(level, blockPos)

		setDirty()
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
				setDirty()
			}
		}
	}

	private fun updatePosition(level: ServerLevel, blockPos: BlockPos) {
		val targetState = level.getBlockState(blockPos)

		targetState.handleNeighborChanged(level, blockPos, Blocks.AIR, blockPos, false)
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
				Factory(::RedstoneHandlerSavedData, Companion::load),
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

}