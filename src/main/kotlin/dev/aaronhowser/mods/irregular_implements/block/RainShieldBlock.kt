package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

class RainShieldBlock : EntityBlock, Block(
	Properties
		.of()
		.sound(SoundType.STONE)
		.strength(2f)
) {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(ENABLED, true)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(ENABLED)
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		return true
	}

	override fun neighborChanged(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		block: Block,
		fromPos: BlockPos,
		isMoving: Boolean
	) {
		if (level.isClientSide) return

		val redstoneStrength = level.getBestNeighborSignal(pos)
		level.setBlockAndUpdate(pos, state.setValue(ENABLED, redstoneStrength == 0))
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = RainShieldBlockEntity(pos, state)

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntities.RAIN_SHIELD.get(),
			RainShieldBlockEntity::tick
		)
	}

	companion object {
		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
	}

}