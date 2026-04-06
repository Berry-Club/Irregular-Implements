package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.block.SimpleContainerBlock
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isTrue
import dev.aaronhowser.mods.irregular_implements.block_entity.FilteredPlatformBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

open class PlatformBlock(
	blockToCopy: Block
) : SimpleContainerBlock(Properties.ofFullCopy(blockToCopy)) {

	override fun getCollisionShape(
		state: BlockState,
		level: BlockGetter,
		pos: BlockPos,
		context: CollisionContext
	): VoxelShape {
		val entity = (context as? EntityCollisionContext)?.entity

		val entityPassesFilter = (level.getBlockEntity(pos) as? FilteredPlatformBlockEntity)
			?.entityPassesFilter(entity)
			.isTrue()

		val isBelow = !context.isAbove(Shapes.block(), pos, true)
		val isDescendingOrPassesFilter = entityPassesFilter || entity?.isDescending.isTrue()

		val shouldPassThrough = isBelow || isDescendingOrPassesFilter

		return if (shouldPassThrough) Shapes.empty() else SHAPE
	}

	override fun getInteractionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
		return SHAPE
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	companion object {
		val SHAPE: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0)
	}

}