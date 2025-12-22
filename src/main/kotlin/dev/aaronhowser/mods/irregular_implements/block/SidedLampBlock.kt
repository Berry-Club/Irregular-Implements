package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedstoneLampBlock
import net.minecraft.world.level.block.state.BlockState

class SidedLampBlock(
	val litOnServer: Boolean
) : RedstoneLampBlock(Properties.ofFullCopy(Blocks.REDSTONE_LAMP)) {

	override fun getLightEmission(state: BlockState, level: BlockGetter, pos: BlockPos): Int {
		if (state.getValue(LIT)) {
			val isServer = level is ServerLevel
			return if (isServer == litOnServer) 15 else 0
		}

		return 0
	}

	override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
		if (state.getValue(LIT)) {
			level.lightEngine.checkBlock(pos)
		}
	}

}