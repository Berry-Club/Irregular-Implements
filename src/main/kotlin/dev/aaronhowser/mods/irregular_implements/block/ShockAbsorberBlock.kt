package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty

class ShockAbsorberBlock : Block(Properties.of()) {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(POWER, 0)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(POWER)
	}

	override fun fallOn(level: Level, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
		val currentPower = state.getValue(POWER)
		val newPower = fallDistance.toInt().coerceIn(0, 15)
		if (currentPower >= newPower) return

		level.scheduleTick(pos, this, 20)
		level.setBlockAndUpdate(pos, state.setValue(POWER, newPower))
	}

	override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
		level.setBlockAndUpdate(pos, state.setValue(POWER, 0))
	}

	override fun isSignalSource(state: BlockState): Boolean {
		return true
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return state.getValue(POWER)
	}

	companion object {
		val POWER: IntegerProperty = BlockStateProperties.POWER
	}


}