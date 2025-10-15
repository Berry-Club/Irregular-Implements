package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.SlimeCubeCarrier
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import net.minecraft.core.BlockPos
import net.minecraft.world.Difficulty
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.Optional

class SlimeCubeBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.SLIME_CUBE.get(), pos, blockState) {

	override fun onLoad() {
		super.onLoad()
		val level = this.level
		if (level is SlimeCubeCarrier) {
			level.getSlimePositions().add(this.worldPosition.asLong())
		}
	}

	override fun setRemoved() {
		super.setRemoved()
		val level = this.level
		if (level is SlimeCubeCarrier) {
			level.getSlimePositions().remove(this.worldPosition.asLong())
		}
	}

	override fun clearRemoved() {
		super.clearRemoved()
		val level = this.level
		if (level is SlimeCubeCarrier) {
			level.getSlimePositions().add(this.worldPosition.asLong())
		}
	}

	companion object {
		fun SlimeCubeCarrier.getSlimePositions(): LongOpenHashSet = this.`irregular_implements$getSlimeCubePositions`()

		fun chunkHasCube(level: Level, pos: BlockPos, powered: Boolean): Boolean {
			if (level !is SlimeCubeCarrier) return false

			val chunkPos = ChunkPos(pos)

			val cubesInThatChunk = level.getSlimePositions()
				.map(BlockPos::of)
				.filter { ChunkPos(it) == chunkPos }

			if (cubesInThatChunk.isEmpty()) return false

			return cubesInThatChunk.any { level.hasNeighborSignal(it) == powered }
		}

		@JvmStatic
		fun slimeCubeResult(level: LevelAccessor, pos: BlockPos): Optional<Boolean> {
			if (level !is Level || level.difficulty == Difficulty.PEACEFUL) return Optional.empty()

			if (chunkHasCube(level, pos, powered = true)) return Optional.of(false)
			if (chunkHasCube(level, pos, powered = false)) return Optional.of(true)

			return Optional.empty()
		}
	}
}