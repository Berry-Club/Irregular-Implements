package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.AdvancedRedstoneTorchBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RedstoneWallTorchBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AdvancedRedstoneWallTorchBlock : RedstoneWallTorchBlock(Properties.ofFullCopy(Blocks.REDSTONE_WALL_TORCH)), EntityBlock {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return AdvancedRedstoneTorchBlockEntity(pos, state)
	}

}