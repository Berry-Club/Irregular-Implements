package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.defaultBlockState
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isItem
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModMobEffects
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.MultifaceBlock
import net.minecraft.world.level.block.PipeBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.IShearable

class SakanadeBlock : Block(
	Properties
		.ofFullCopy(Blocks.VINE)
		.isViewBlocking(Blocks::never)
		.replaceable()
), IShearable {

	private val shapesByState: Map<BlockState, VoxelShape>

	init {
		registerDefaultState(
			stateDefinition
				.any()
				.setValue(NORTH, false)
				.setValue(EAST, false)
				.setValue(SOUTH, false)
				.setValue(WEST, false)
				.setValue(UP, false)
				.setValue(DOWN, false)
		)

		shapesByState = stateDefinition.possibleStates.associateWith(::calculateShape)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN)
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return shapesByState.getValue(state)
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		return hasFaces(removeUnsupportedFaces(state, level, pos))
	}

	override fun updateShape(state: BlockState, direction: Direction, neighborState: BlockState, level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos): BlockState {
		val updatedState = removeUnsupportedFaces(state, level, pos)
		return if (hasFaces(updatedState)) updatedState else Blocks.AIR.defaultBlockState()
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		val level = context.level
		val clickedPos = context.clickedPos

		val clickedState = level.getBlockState(clickedPos)
		val isAddingFace = clickedState.isBlock(this)

		val stateToPlace = if (isAddingFace) clickedState else defaultBlockState()

		for (direction in context.nearestLookingDirections) {
			val property = PROPERTY_BY_DIRECTION.getValue(direction)
			val alreadyHasFace = isAddingFace && clickedState.getValue(property)

			if (!alreadyHasFace && canAttachTo(level, clickedPos, direction)) {
				return stateToPlace.setValue(property, true)
			}
		}

		return null
	}

	override fun canBeReplaced(state: BlockState, useContext: BlockPlaceContext): Boolean {
		return !useContext.itemInHand.isItem(asItem()) || countFaces(state) < PROPERTY_BY_DIRECTION.size
	}

	override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
		return true
	}

	override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return Shapes.empty()
	}

	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		if (level.isClientSide || entity !is LivingEntity) return

		entity.addEffect(MobEffectInstance(ModMobEffects.COLLAPSE, COLLAPSE_DURATION_TICKS))
	}

	companion object {
		val SHAPE_UP: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0)
		val SHAPE_WEST: VoxelShape = box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0)
		val SHAPE_EAST: VoxelShape = box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0)
		val SHAPE_NORTH: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0)
		val SHAPE_SOUTH: VoxelShape = box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0)
		val SHAPE_DOWN: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)

		val NORTH: BooleanProperty = BlockStateProperties.NORTH
		val EAST: BooleanProperty = BlockStateProperties.EAST
		val SOUTH: BooleanProperty = BlockStateProperties.SOUTH
		val WEST: BooleanProperty = BlockStateProperties.WEST
		val UP: BooleanProperty = BlockStateProperties.UP
		val DOWN: BooleanProperty = BlockStateProperties.DOWN

		val PROPERTY_BY_DIRECTION: Map<Direction, BooleanProperty> = PipeBlock.PROPERTY_BY_DIRECTION

		private const val COLLAPSE_DURATION_TICKS = 8 * 20

		private fun calculateShape(state: BlockState): VoxelShape {
			var voxelShape = Shapes.empty()

			if (state.getValue(UP)) voxelShape = SHAPE_UP
			if (state.getValue(NORTH)) voxelShape = Shapes.or(voxelShape, SHAPE_NORTH)
			if (state.getValue(SOUTH)) voxelShape = Shapes.or(voxelShape, SHAPE_SOUTH)
			if (state.getValue(EAST)) voxelShape = Shapes.or(voxelShape, SHAPE_EAST)
			if (state.getValue(WEST)) voxelShape = Shapes.or(voxelShape, SHAPE_WEST)
			if (state.getValue(DOWN)) voxelShape = Shapes.or(voxelShape, SHAPE_DOWN)

			return if (voxelShape.isEmpty) Shapes.block() else voxelShape
		}

		private fun countFaces(state: BlockState): Int {
			var count = 0

			for (property in PROPERTY_BY_DIRECTION.values) {
				if (state.getValue(property)) count++
			}

			return count
		}

		private fun hasFaces(state: BlockState): Boolean {
			return countFaces(state) > 0
		}

		private fun canAttachTo(level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
			val neighborPos = pos.relative(direction)
			val neighborState = level.getBlockState(neighborPos)

			if (neighborState.isBlock(ModBlocks.SAKANADE_SPORES)) {
				return neighborState.getValue(PROPERTY_BY_DIRECTION.getValue(direction))
			}

			return MultifaceBlock.canAttachTo(
				level,
				direction,
				neighborPos,
				neighborState
			)
		}

		private fun canRemainAttached(level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
			if (canAttachTo(level, pos, direction)) return true

			val stateAbove = level.getBlockState(pos.above())
			return stateAbove.isBlock(ModBlocks.SAKANADE_SPORES) &&
					stateAbove.getValue(PROPERTY_BY_DIRECTION.getValue(direction))
		}

		private fun removeUnsupportedFaces(state: BlockState, level: BlockGetter, pos: BlockPos): BlockState {
			var updatedState = state

			for (direction in Direction.entries) {
				val property = PROPERTY_BY_DIRECTION.getValue(direction)
				if (state.getValue(property) && !canRemainAttached(level, pos, direction)) {
					updatedState = updatedState.setValue(property, false)
				}
			}

			return updatedState
		}

		@JvmStatic
		fun addToMushroom(
			level: LevelAccessor,
			origin: BlockPos,
			mutablePos: BlockPos.MutableBlockPos
		) {
			if (mutablePos.x == origin.x && mutablePos.z == origin.z) return

			val sporesPos = mutablePos.below()
			if (!level.getBlockState(sporesPos).canBeReplaced()) return

			level.setBlock(
				sporesPos,
				ModBlocks.SAKANADE_SPORES.defaultBlockState().setValue(UP, true),
				UPDATE_ALL
			)
		}
	}

}