package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
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
			if (strongStalkIsDonePredicate.invoke(level, pos)) {
				level.setBlockAndUpdate(
					pos.above(),
					ModBlocks.BEAN_POD.get().defaultBlockState()
				)
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

		return stateBelow.`is`(this) || stateBelow.isBlock(BlockTags.DIRT)
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
				blockState.isBlock(ModBlocks.BEAN_STALK.get()) -> 3f
				blockState.isBlock(ModBlocks.LESSER_BEAN_STALK.get()) -> 2f
				else -> 1f
			}
		}

		/**
		 * Whether or not the Magic Bean's stalk is done growing, and the block above it should be a Bean Pod.
		 */
		private var strongStalkIsDonePredicate: (Level, BlockPos) -> Boolean = ::defaultStrongIsDonePredicate

		private fun defaultStrongIsDonePredicate(level: Level, pos: BlockPos): Boolean {
			// If the block above it is at max build height, it's done
			if (pos.y + 1 >= level.maxBuildHeight) {
				return true
			}

			// If the block two above it is indestructible, it's done
			// That's because if it grew again, it wouldn't be able to replace the block above itself with either another Stalk or a Bean Pod
			val stateTwoAbove = level.getBlockState(pos.above(2))
			return stateTwoAbove.getDestroySpeed(level, pos.above(2)) == -1.0f
		}

		/**
		 * Mostly meant to be called from KubeJS.
		 *
		 * Here's an example:
		 *
		 * ```js
		 * const $BeanStalkBlock = Java.loadClass('dev.aaronhowser.mods.irregular_implements.block.BeanStalkBlock')
		 *
		 * $BeanStalkBlock.setMagicBeanStalkIsDoneGrowingPredicate((level, pos) => pos.y >= 100)
		 * ```
		 *
		 * @param predicate A predicate that takes in a [Level] and a [BlockPos] of a just-grown Magic Bean Stalk, and returns a [Boolean] of if it's done growing yet and the block above should be turned into a Bean Pod.
		 */
		@JvmStatic
		fun setMagicBeanStalkIsDoneGrowingPredicate(predicate: (Level, BlockPos) -> Boolean) {
			strongStalkIsDonePredicate = predicate
		}
	}

}