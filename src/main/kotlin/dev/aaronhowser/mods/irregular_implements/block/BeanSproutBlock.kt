package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.CommonHooks

class BeanSproutBlock(
	properties: Properties = Properties
		.ofFullCopy(Blocks.ROSE_BUSH)
		.randomTicks()
		.dynamicShape()
) : BushBlock(properties), BonemealableBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(AGE, 0)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(AGE)
	}

	override fun codec(): MapCodec<BeanSproutBlock> {
		return CODEC
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return if (state.getValue(AGE) == MAXIMUM_AGE) SHAPE_BIG else SHAPE_SMALL
	}

	override fun randomTick(oldState: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
		if (
			oldState.getValue(AGE) >= MAXIMUM_AGE                                                          // If it's already fully grown
			|| level.getRawBrightness(pos.above(), 0) < 9                                           // If it's not bright enough
			|| !CommonHooks.canCropGrow(level, pos, oldState, random.nextInt(2) == 0)           // If it can't grow
		) return

		val newState = oldState.cycle(AGE)
		level.setBlockAndUpdate(pos, newState)

		CommonHooks.fireCropGrowPost(level, pos, oldState)
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(oldState))
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		if (level.isClientSide || state.getValue(AGE) != MAXIMUM_AGE) return InteractionResult.PASS

		val amountBeans = 1 + level.random.nextInt(2)
		val stack = ModItems.BEAN.toStack(amountBeans)

		val added = player.inventory.add(stack)
		if (!added) {
			Block.popResource(level, pos, stack)
		}

		val newState = state.setValue(AGE, 0)
		level.setBlockAndUpdate(pos, newState)

		return InteractionResult.SUCCESS
	}

	override fun isValidBonemealTarget(level: LevelReader, pos: BlockPos, state: BlockState): Boolean {
		return state.getValue(AGE) < MAXIMUM_AGE
	}

	override fun isBonemealSuccess(level: Level, random: RandomSource, pos: BlockPos, state: BlockState): Boolean {
		return true
	}

	override fun performBonemeal(level: ServerLevel, random: RandomSource, pos: BlockPos, state: BlockState) {
		val newState = state.cycle(AGE)
		level.setBlockAndUpdate(pos, newState)
	}

	companion object {
		val CODEC: MapCodec<BeanSproutBlock> = simpleCodec(::BeanSproutBlock)

		val SHAPE_SMALL: VoxelShape = box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0)
		val SHAPE_BIG: VoxelShape = box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0)

		val AGE: IntegerProperty = BlockStateProperties.AGE_7
		const val MAXIMUM_AGE = 7
	}

}