package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.MoonPhaseDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class MoonPhaseDetectorBlock : Block(
	Properties
		.ofFullCopy(Blocks.DAYLIGHT_DETECTOR)
), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(POWER, 0)
				.setValue(INVERTED, false)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(POWER, INVERTED)
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		return SHAPE
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return state.getValue(POWER)
	}

	override fun isSignalSource(state: BlockState): Boolean {
		return true
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return MoonPhaseDetectorBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
		return if (level.isClientSide) {
			null
		} else {
			BaseEntityBlock.createTickerHelper(
				blockEntityType,
				ModBlockEntityTypes.MOON_PHASE_DETECTOR.get(),
				MoonPhaseDetectorBlockEntity::tick
			)
		}
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		if (level.isClientSide || !player.mayInteract(level, pos)) return InteractionResult.PASS

		val newState = state.cycle(INVERTED)
		level.setBlockAndUpdate(pos, newState)

		return InteractionResult.SUCCESS
	}

	companion object {
		val POWER: IntegerProperty = BlockStateProperties.POWER
		val INVERTED: BooleanProperty = BlockStateProperties.INVERTED

		val SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0)
	}

}