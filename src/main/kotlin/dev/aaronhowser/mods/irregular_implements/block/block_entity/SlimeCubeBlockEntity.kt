package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.Difficulty
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import org.antlr.v4.runtime.misc.MultiMap

class SlimeCubeBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.SLIME_CUBE.get(), pos, blockState) {

	override fun onLoad() {
		super.onLoad()
		val level = this.level ?: return

		CUBES.computeIfAbsent(level) { mutableListOf() }.add(this)
	}

	override fun setRemoved() {
		super.setRemoved()
		val level = this.level ?: return

		CUBES.computeIfAbsent(level) { mutableListOf() }.remove(this)
	}

	override fun clearRemoved() {
		super.clearRemoved()
		val level = this.level ?: return

		CUBES.computeIfAbsent(level) { mutableListOf() }.add(this)
	}

	companion object {
		private val CUBES: MultiMap<Level, SlimeCubeBlockEntity> = MultiMap()

		fun chunkHasCube(level: Level, pos: BlockPos, powered: Boolean): Boolean {
			val chunkPos = level.getChunk(pos).pos

			val cubesInThatChunk = CUBES
				.getOrDefault(level, emptyList())
				.filter { it.level?.getChunk(it.worldPosition)?.pos == chunkPos }

			if (cubesInThatChunk.isEmpty()) return false

			return cubesInThatChunk.any { it.level?.hasNeighborSignal(it.worldPosition) == powered }
		}

		@JvmStatic
		fun slimeCubeResult(level: LevelAccessor, pos: BlockPos): Boolean? {
			if (level !is Level || level.difficulty == Difficulty.PEACEFUL) return null

			if (chunkHasCube(level, pos, powered = true)) return false
			if (chunkHasCube(level, pos, powered = false)) return true

			return false
		}
	}
}