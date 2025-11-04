package dev.aaronhowser.mods.irregular_implements.client

import dev.aaronhowser.mods.irregular_implements.handler.SpectreIlluminationHandler
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.minecraft.world.level.ChunkPos

object ClientSpectreIllumination {

	private val illuminatedChunks: MutableSet<Long> = mutableSetOf()

	fun isChunkIlluminated(blockPos: BlockPos): Boolean {
		val chunkPosLong = ChunkPos.asLong(blockPos)
		return illuminatedChunks.contains(chunkPosLong)
	}

	fun setChunkIlluminated(chunkPos: ChunkPos, newValue: Boolean) {
		val chunkPosLong = chunkPos.toLong()
		if (newValue) {
			illuminatedChunks.add(chunkPosLong)
		} else {
			illuminatedChunks.remove(chunkPosLong)
		}

		val level = ClientUtil.localLevel
		if (level != null) {
			SpectreIlluminationHandler.forceLightUpdates(level, chunkPos)
		}
	}

}