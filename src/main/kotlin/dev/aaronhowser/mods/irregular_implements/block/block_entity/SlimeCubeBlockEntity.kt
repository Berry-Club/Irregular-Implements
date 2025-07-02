package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.Difficulty
import net.minecraft.world.entity.MobSpawnType
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
		CUBES.add(this)
	}

	override fun setRemoved() {
		super.setRemoved()
		CUBES.remove(this)
	}

	override fun clearRemoved() {
		super.clearRemoved()
		CUBES.add(this)
	}

	companion object {
		private val CUBES: MutableSet<SlimeCubeBlockEntity> = mutableSetOf()

		fun chunkHasCube(level: Level, pos: BlockPos, powered: Boolean): Boolean {
			val chunkPos = level.getChunk(pos).pos

			val cubesInThatChunk = CUBES.filter { it.level?.getChunk(it.worldPosition)?.pos == chunkPos }
			if (cubesInThatChunk.isEmpty()) return false

			return cubesInThatChunk.any { it.level?.hasNeighborSignal(it.worldPosition) == powered }
		}

		@JvmStatic
		fun slimeCubeResult(level: LevelAccessor, pos: BlockPos): Boolean? {
			if (level !is Level || level.difficulty == Difficulty.PEACEFUL) return null

			if (chunkHasCube(level, pos, powered = true)) return false
			if (!chunkHasCube(level, pos, powered = false)) return true

			return null
		}
	}

}