package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class GlowingMushroomBlock : NoCodecBushBlock(Properties.ofFullCopy(Blocks.RED_MUSHROOM)) {

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	override fun mayPlaceOn(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
		return state.isSolidRender(level, pos)
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		val belowPos = pos.below()
		val stateBelow = level.getBlockState(belowPos)
		val soilDecision = stateBelow.canSustainPlant(level, belowPos, Direction.UP, state)

		if (stateBelow.isBlock(BlockTags.MUSHROOM_GROW_BLOCK)) {
			return true
		}

		if (!soilDecision.isDefault()) return soilDecision.isTrue()

		return level.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(stateBelow, level, belowPos)
	}

	override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
		if (random.nextInt(25) != 0) return

		var mushroomsNearby = 0

		var currentPos = pos
		val area = BlockPos.betweenClosed(
			currentPos.offset(-4, -1, -4),
			currentPos.offset(4, 1, 4)
		)

		for (checkPos in area) {
			if (level.getBlockState(checkPos).`is`(this)) {
				if (++mushroomsNearby >= 5) {
					return
				}
			}
		}

		var candidatePos = currentPos.offset(
			random.nextInt(3) - 1,
			random.nextInt(2) - random.nextInt(2),
			random.nextInt(3) - 1
		)

		for (attempt in 0..3) {
			if (level.isEmptyBlock(candidatePos) && state.canSurvive(level, candidatePos)) {
				currentPos = candidatePos
			}

			candidatePos = currentPos.offset(
				random.nextInt(3) - 1,
				random.nextInt(2) - random.nextInt(2),
				random.nextInt(3) - 1
			)
		}

		if (level.isEmptyBlock(candidatePos) && state.canSurvive(level, candidatePos)) {
			level.setBlock(candidatePos, state, 2)
		}
	}

	companion object {
		val SHAPE: VoxelShape = box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0)
	}

}