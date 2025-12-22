package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.BlockGetter
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

}