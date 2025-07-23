package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

object BlockSchematics {

	fun getNatureCore(log: BlockState, leaves: BlockState): Map<BlockPos, BlockState> {
		val positionedBlocks = mutableMapOf<BlockPos, BlockState>()

		for (dX in -1..1) for (dY in -1..1) for (dZ in -1..1) {
			val offsetPos = BlockPos(dX, dY, dZ)

			if (dY == 0) {
				if (dX == 0 && dZ == 0) {
					positionedBlocks[offsetPos] = ModBlocks.NATURE_CORE.get().defaultBlockState()
				} else if (dX == 0 || dZ == 0) {
					positionedBlocks[offsetPos] = leaves
				} else {
					positionedBlocks[offsetPos] = log
				}

				continue
			}

			if ((dX == 0 || dZ == 0) && dX != dZ) {
				positionedBlocks[offsetPos] = log
			} else {
				positionedBlocks[offsetPos] = leaves
			}
		}

		return positionedBlocks
	}

}