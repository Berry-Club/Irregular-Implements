package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.Difficulty
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class SlimeCubeBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.SLIME_CUBE.get(), pos, blockState) {

	override fun onLoad() {
		super.onLoad()
		val level = this.level ?: return

		CUBES.computeIfAbsent(level) { mutableSetOf() }.add(this)
	}

	override fun setRemoved() {
		super.setRemoved()
		val level = this.level ?: return

		CUBES.computeIfAbsent(level) { mutableSetOf() }.remove(this)
	}

	override fun clearRemoved() {
		super.clearRemoved()
		val level = this.level ?: return

		CUBES.computeIfAbsent(level) { mutableSetOf() }.add(this)
	}

	companion object {
		private val CUBES: MutableMap<Level, MutableSet<SlimeCubeBlockEntity>> = mutableMapOf()

		fun chunkHasCube(level: Level, pos: BlockPos, powered: Boolean): Boolean {
			val chunkPos = ChunkPos(pos)

			val cubesInThatChunk = CUBES
				.getOrDefault(level, emptySet())
				.filter {
					ChunkPos(it.worldPosition) == chunkPos
				}

			if (cubesInThatChunk.isEmpty()) return false

			return cubesInThatChunk.any { it.level?.hasNeighborSignal(it.worldPosition) == powered }
		}

		@JvmStatic
		fun slimeCubeResult(level: LevelAccessor, pos: BlockPos): Boolean? {
			if (level !is Level || level.difficulty == Difficulty.PEACEFUL) return null

			if (chunkHasCube(level, pos, powered = true)) return false
			if (chunkHasCube(level, pos, powered = false)) return true

			return null
		}
	}
}