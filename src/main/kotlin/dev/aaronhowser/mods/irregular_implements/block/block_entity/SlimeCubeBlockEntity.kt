package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.SlimeCubeCarrier
import dev.aaronhowser.mods.irregular_implements.block.SlimeCubeBlock
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import net.minecraft.core.BlockPos
import net.minecraft.world.Difficulty
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class SlimeCubeBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.SLIME_CUBE.get(), pos, blockState) {

	override fun onLoad() {
		super.onLoad()
		val level = this.level
		if (level is SlimeCubeCarrier) {
			level.getSlimeCubeBlockPositions().add(this.worldPosition.asLong())
		}
	}

	override fun setRemoved() {
		super.setRemoved()
		val level = this.level
		if (level is SlimeCubeCarrier) {
			level.getSlimeCubeBlockPositions().remove(this.worldPosition.asLong())
		}
	}

	override fun clearRemoved() {
		super.clearRemoved()
		val level = this.level
		if (level is SlimeCubeCarrier) {
			level.getSlimeCubeBlockPositions().add(this.worldPosition.asLong())
		}
	}

	companion object {
		fun SlimeCubeCarrier.getSlimeCubeBlockPositions(): LongOpenHashSet = this.`irregular_implements$getSlimeCubeBlockPositions`()

		fun chunkHasCube(level: Level, pos: BlockPos, powered: Boolean): Boolean {
			if (level !is SlimeCubeCarrier) return false

			val checkingSpawnAtChunk = ChunkPos(pos)
			val radius = ServerConfig.CONFIG.slimeCubeChunkRadius.get()

			return level.getSlimeCubeBlockPositions()
				.asSequence()
				.map(BlockPos::of)
				.filter { ChunkPos(it).getChessboardDistance(checkingSpawnAtChunk) <= radius }
				.any { level.getBlockState(it).getValue(SlimeCubeBlock.POWERED) == powered }
		}

		@JvmStatic
		fun slimeCubeResult(level: LevelAccessor, pos: BlockPos): Optional<Boolean> {
			if (level is Level && level.difficulty != Difficulty.PEACEFUL) {
				if (chunkHasCube(level, pos, powered = true)) return Optional.of(false)
				if (chunkHasCube(level, pos, powered = false)) return Optional.of(true)
			}

			return Optional.empty()
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: SlimeCubeBlockEntity
		) {
			if (level !is SlimeCubeCarrier) return
			level.getSlimeCubeBlockPositions().add(blockPos.asLong())
		}
	}
}