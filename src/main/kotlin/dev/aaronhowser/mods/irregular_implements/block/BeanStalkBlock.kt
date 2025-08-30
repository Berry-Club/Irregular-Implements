package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class BeanStalkBlock(
	val isStrong: Boolean
) : Block(
	Properties
		.ofFullCopy(Blocks.BAMBOO)
		.offsetType(OffsetType.NONE)
) {

	override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
		super.onPlace(state, level, pos, oldState, movedByPiston)

		if (!level.getBlockState(pos.below()).`is`(this)) {
			level.scheduleTick(pos, this, 5)
		}
	}

	override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
		if (this.isStrong) {
			if (level.maxBuildHeight == pos.y + 2) {
				val blockAbove = level.getBlockState(pos.above())

				if (blockAbove.getDestroySpeed(level, pos.above()) != -1.0f) {
					level.setBlockAndUpdate(
						pos.above(),
						ModBlocks.BEAN_POD.get().defaultBlockState()
					)
				}

				return
			}
		} else {
			if (pos.y >= level.maxBuildHeight || !level.isEmptyBlock(pos.above())) return
		}

		val blockAbove = level.getBlockState(pos.above())
		if (blockAbove.getDestroySpeed(level, pos.above()) == -1.0f) return

		if (!level.isEmptyBlock(pos.above())) {
			level.destroyBlock(pos.above(), true)
		}

		level.setBlockAndUpdate(
			pos.above(),
			this.defaultBlockState()
		)

		level.playSound(
			null,
			pos.above(),
			SoundEvents.BONE_MEAL_USE,
			SoundSource.BLOCKS,
		)

		for (player in level.players()) {
			level.sendParticles(
				ParticleTypes.HAPPY_VILLAGER,
				pos.above().x + 0.5,
				pos.above().y + 0.5,
				pos.above().z + 0.5,
				5,
				0.25,
				0.25,
				0.25,
				0.0
			)
		}

		level.scheduleTick(pos.above(), this, if (this.isStrong) 1 else 5)
	}

	override fun updateShape(state: BlockState, direction: Direction, neighborState: BlockState, level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos): BlockState {
		return if (state.canSurvive(level, pos)) {
			state
		} else {
			Blocks.AIR.defaultBlockState()
		}
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		val stateBelow = level.getBlockState(pos.below())

		return stateBelow.`is`(this) || stateBelow.`is`(BlockTags.DIRT)
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	companion object {
		val SHAPE: VoxelShape = box(6.4, 0.0, 6.4, 9.6, 16.0, 9.6)

		@JvmStatic
		fun climbingFactor(entity: Entity): Float {
			val blockState = entity.inBlockState

			return when {
				blockState.`is`(ModBlocks.BEAN_STALK.get()) -> 3f
				blockState.`is`(ModBlocks.LESSER_BEAN_STALK.get()) -> 2f
				else -> 1f
			}
		}
	}

}