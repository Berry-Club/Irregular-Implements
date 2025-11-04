package dev.aaronhowser.mods.irregular_implements.handler

import dev.aaronhowser.mods.irregular_implements.client.ClientSpectreIllumination
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateSpectreIlluminationPacket
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.saveddata.SavedData
import net.neoforged.fml.loading.FMLEnvironment

class SpectreIlluminationHandler : SavedData() {

	private val illuminatedChunkLongs: MutableSet<Long> = mutableSetOf()

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		tag.putLongArray(CHUNK_LONGS, illuminatedChunkLongs.toLongArray())
		return tag
	}

	fun isChunkIlluminated(blockPos: BlockPos): Boolean {
		val chunkPosLong = ChunkPos.asLong(blockPos)
		return illuminatedChunkLongs.contains(chunkPosLong)
	}

	fun isChunkIlluminated(chunkPos: ChunkPos): Boolean {
		val chunkPosLong = chunkPos.toLong()
		return illuminatedChunkLongs.contains(chunkPosLong)
	}

	companion object {
		const val CHUNK_LONGS = "chunk_longs"

		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): SpectreIlluminationHandler {
			val data = SpectreIlluminationHandler()

			val chunkLongs = tag.getLongArray(CHUNK_LONGS)
			data.illuminatedChunkLongs.addAll(chunkLongs.toList())

			return data
		}

		fun get(level: ServerLevel): SpectreIlluminationHandler {
			return level.dataStorage.computeIfAbsent(
				Factory(::SpectreIlluminationHandler, ::load),
				"spectre_illumination_handler"
			)
		}

		fun watchChunk(player: ServerPlayer, chunkPos: ChunkPos) {
			val handler = get(player.serverLevel())

			if (handler.isChunkIlluminated(chunkPos)) {
				val packet = UpdateSpectreIlluminationPacket(chunkPos.toLong(), true)
				packet.messagePlayer(player)
			}
		}

		@JvmStatic
		fun isChunkIlluminated(blockAndTintGetter: BlockAndTintGetter, blockPos: BlockPos): Boolean {
			if (blockAndTintGetter.getBlockState(blockPos).isAir) return false

			val isClientSide: Boolean = if (blockAndTintGetter is Level) {
				blockAndTintGetter.isClientSide
			} else {
				!FMLEnvironment.dist.isDedicatedServer
			}

			return if (isClientSide) {
				ClientSpectreIllumination.isChunkIlluminated(blockPos)
			} else {
				val level = blockAndTintGetter as? ServerLevel ?: return false
				isChunkIlluminated(level, blockPos)
			}
		}

		fun isChunkIlluminated(level: ServerLevel, blockPos: BlockPos): Boolean {
			val handler = get(level)
			return handler.isChunkIlluminated(blockPos)
		}

		fun setChunkIlluminated(level: ServerLevel, blockPos: BlockPos, newValue: Boolean) {
			val handler = get(level)
			val chunkPos = ChunkPos(blockPos)
			val chunkPosLong = chunkPos.toLong()

			if (newValue) {
				handler.illuminatedChunkLongs.add(chunkPosLong)
			} else {
				handler.illuminatedChunkLongs.remove(chunkPosLong)
			}

			val packet = UpdateSpectreIlluminationPacket(chunkPosLong, newValue)
			packet.messageAllPlayersTrackingChunk(level, chunkPos)

			forceLightUpdates(level, chunkPos)

			level.getChunk(blockPos).isUnsaved = true
			handler.setDirty()
		}

		//FIXME: For some reason it doesn't work super well in chunks that are mostly empty (possibly only effects superflat levels?)
		//TODO: Study effect on lag, possibly only when the chunk loads the first time?
		fun forceLightUpdates(level: Level, chunkPos: ChunkPos) {
			if (!level.isLoaded(chunkPos.worldPosition)) return

			// +- 1 to also check edges
			val minX = chunkPos.minBlockX - 1
			val maxX = chunkPos.maxBlockX + 1
			val minZ = chunkPos.minBlockZ - 1
			val maxZ = chunkPos.maxBlockZ + 1
			val minY = level.minBuildHeight
			val maxY = level.maxBuildHeight

			val iterable = BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)

			for (pos in iterable) {
				level.chunkSource.lightEngine.checkBlock(pos.immutable())
			}
		}
	}


}