package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.PeaceCandleCarrier
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent

class PeaceCandleBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.PEACE_CANDLE.get(), pPos, pBlockState) {

	companion object {
		fun PeaceCandleCarrier.getPeaceCandleChunks(): LongOpenHashSet = this.`irregular_implements$getPeaceCandleChunks`()

		fun chunkIsPreventingMonsterSpawns(level: LevelReader, blockPos: BlockPos): Boolean {
			if (!level.isAreaLoaded(blockPos, 1)) return false
			if (level !is PeaceCandleCarrier) return false

			val chunkPos = level.getChunk(blockPos).pos.toLong()
			return level.getPeaceCandleChunks().contains(chunkPos)
		}

		fun onSpawnPlacementCheck(event: MobSpawnEvent.SpawnPlacementCheck) {
			if (event.spawnType != MobSpawnType.NATURAL
				|| event.result == MobSpawnEvent.SpawnPlacementCheck.Result.FAIL
				|| event.entityType.category.isFriendly
			) return

			if (chunkIsPreventingMonsterSpawns(event.level, event.pos)) {
				event.result = MobSpawnEvent.SpawnPlacementCheck.Result.FAIL
			}
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: PeaceCandleBlockEntity
		) {
			if (level !is PeaceCandleCarrier) return

			val chunkPos = ChunkPos(blockPos.x.shr(4), blockPos.z.shr(4))

			val checkRadius = ServerConfig.CONFIG.peaceCandleChunkRadius.get()
			val chunkX = chunkPos.x
			val chunkZ = chunkPos.z

			for (x in (chunkX - checkRadius)..(chunkX + checkRadius)) {
				for (z in (chunkZ - checkRadius)..(chunkZ + checkRadius)) {
					level.getPeaceCandleChunks().add(ChunkPos.asLong(x, z))
				}
			}
		}
	}

}