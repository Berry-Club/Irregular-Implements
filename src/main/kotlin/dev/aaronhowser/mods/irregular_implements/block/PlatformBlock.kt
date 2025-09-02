package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.FilteredPlatformBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class PlatformBlock(
	blockToCopy: Block,
	private val hasEntity: Boolean = false
) : Block(
	Properties.ofFullCopy(blockToCopy)
), EntityBlock {

	override fun getCollisionShape(
		state: BlockState,
		level: BlockGetter,
		pos: BlockPos,
		context: CollisionContext
	): VoxelShape {
		val entity = (context as? EntityCollisionContext)?.entity

		val entityPassesFilter = (level.getBlockEntity(pos) as? FilteredPlatformBlockEntity)
			?.entityPassesFilter(entity)
			.isTrue

		val isBelow = !context.isAbove(Shapes.block(), pos, true)
		val isDescendingOrPassesFilter = entityPassesFilter || entity?.isDescending.isTrue

		val shouldPassThrough = isBelow || isDescendingOrPassesFilter

		return if (shouldPassThrough) Shapes.empty() else SHAPE
	}


	override fun getInteractionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
		return SHAPE
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
		return if (this.hasEntity) {
			FilteredPlatformBlockEntity(pos, state)
		} else {
			null
		}
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {

		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is FilteredPlatformBlockEntity) {
			player.openMenu(blockEntity)
			return InteractionResult.SUCCESS
		}

		return super.useWithoutItem(state, level, pos, player, hitResult)
	}

	override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, movedByPiston: Boolean) {
		val be = level.getBlockEntity(pos)
		if (be is FilteredPlatformBlockEntity) {
			Containers.dropContents(level, pos, be.container)
		}
		super.onRemove(state, level, pos, newState, movedByPiston)
	}

	companion object {
		val SHAPE: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0)

		val OAK = PlatformBlock(Blocks.OAK_TRAPDOOR)
		val SPRUCE = PlatformBlock(Blocks.SPRUCE_TRAPDOOR)
		val BIRCH = PlatformBlock(Blocks.BIRCH_TRAPDOOR)
		val JUNGLE = PlatformBlock(Blocks.JUNGLE_TRAPDOOR)
		val ACACIA = PlatformBlock(Blocks.ACACIA_TRAPDOOR)
		val DARK_OAK = PlatformBlock(Blocks.DARK_OAK_TRAPDOOR)
		val CRIMSON = PlatformBlock(Blocks.CRIMSON_TRAPDOOR)
		val WARPED = PlatformBlock(Blocks.WARPED_TRAPDOOR)
		val MANGROVE = PlatformBlock(Blocks.MANGROVE_TRAPDOOR)
		val BAMBOO = PlatformBlock(Blocks.BAMBOO_TRAPDOOR)
		val CHERRY = PlatformBlock(Blocks.CHERRY_TRAPDOOR)
		val SUPER_LUBE = PlatformBlock(ModBlocks.SUPER_LUBRICANT_ICE.get())
		val FILTERED_SUPER_LUBE = PlatformBlock(ModBlocks.SUPER_LUBRICANT_ICE.get(), hasEntity = true)
	}

}