package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FarmBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.ItemAbilities
import net.neoforged.neoforge.common.ItemAbility

class FertilizedDirtBlock : FarmBlock(
	Properties
		.ofFullCopy(Blocks.FARMLAND)
		.dynamicShape()
) {

	//FIXME: Disallow crops from being placed on non-tilled fertilized dirt

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(TILLED, false)
				.setValue(MOISTURE, MAX_MOISTURE)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		super.createBlockStateDefinition(builder)
		builder.add(TILLED)
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return if (state.getValue(TILLED)) {
			TILLED_SHAPE
		} else {
			Shapes.block()
		}
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		return true
	}

	override fun getToolModifiedState(state: BlockState, context: UseOnContext, itemAbility: ItemAbility, simulate: Boolean): BlockState? {
		if (itemAbility == ItemAbilities.HOE_TILL && !state.getValue(TILLED)) {
			return state.setValue(TILLED, true)
		}

		return super.getToolModifiedState(state, context, itemAbility, simulate)
	}

	override fun fallOn(level: Level, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
		entity.causeFallDamage(fallDistance, 1.0f, entity.damageSources().fall())
	}

	override fun isFertile(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
		return state.getValue(TILLED)
	}

	override fun isOcclusionShapeFullBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
		return !state.getValue(TILLED)
	}

	override fun isRandomlyTicking(state: BlockState): Boolean {
		return true
	}

	override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
		if (level.isClientSide) return

		val cropPos = pos.above()
		val cropState = level.getBlockState(cropPos)

		if (cropState.isRandomlyTicking) {
			repeat(3) {
				cropState.randomTick(level, cropPos, random)
			}
		}
	}

	companion object {
		val TILLED: BooleanProperty = BooleanProperty.create("tilled")
		val TILLED_SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)
	}

}