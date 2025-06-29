package dev.aaronhowser.mods.irregular_implements.handler.redstone_signal

import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level

data class SavedSignal(
	val blockPos: Long,
	val dimension: ResourceKey<Level>,
	val duration: Int,
	val strength: Int,
	val startTick: Long
) {

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
}