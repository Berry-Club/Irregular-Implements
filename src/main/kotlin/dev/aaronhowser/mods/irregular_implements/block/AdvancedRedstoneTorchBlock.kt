package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.AdvancedRedstoneTorchBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedstoneTorchBlock
import net.minecraft.world.level.block.state.BlockState

class AdvancedRedstoneTorchBlock : RedstoneTorchBlock(Properties.ofFullCopy(Blocks.REDSTONE_TORCH)) {

	override fun getSignal(blockState: BlockState, blockAccess: BlockGetter, pos: BlockPos, side: Direction): Int {
		val blockEntity = blockAccess.getBlockEntity(pos)
		if (blockEntity is AdvancedRedstoneTorchBlockEntity) {
			val isLit = blockState.getValue(LIT)
			return blockEntity.getStrength(isLit)
		}

		return super.getSignal(blockState, blockAccess, pos, side)
	}

}