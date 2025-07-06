package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.BetterFire
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FireBlock
import net.minecraft.world.level.block.state.BlockState

class BlazeFireBlock : FireBlock(
	Properties
		.ofFullCopy(Blocks.FIRE)
), BetterFire {

	override fun `irregular_implements$getTickDelayFactor`(): Float {
		return 0.1f
	}

	@Suppress("OVERRIDE_DEPRECATION")
	override fun getIgniteOdds(state: BlockState): Int {
		return super.getIgniteOdds(state) * 3
	}

	@Suppress("OVERRIDE_DEPRECATION")
	override fun getBurnOdds(state: BlockState): Int {
		return super.getBurnOdds(state) * 3
	}

	override fun getFlammability(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return super.getFlammability(state, level, pos, direction) * 3
	}

	override fun getFireSpreadSpeed(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return super.getFireSpreadSpeed(state, level, pos, direction) * 3
	}

	override fun getStateWithAge(level: LevelAccessor, pos: BlockPos, age: Int): BlockState {
		val possibleState = super.getStateWithAge(level, pos, age)

		return if (possibleState.block == Blocks.FIRE) {
			defaultBlockState().setValue(AGE, age)
		} else {
			possibleState
		}
	}

}