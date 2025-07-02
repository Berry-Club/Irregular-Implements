package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
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

		fun chunkHasCube(level: Level, pos: BlockPos): Boolean {
			val chunkPos = level.getChunk(pos).pos
			return CUBES.any { it.level?.getChunk(it.worldPosition)?.pos == chunkPos }
		}
	}

}