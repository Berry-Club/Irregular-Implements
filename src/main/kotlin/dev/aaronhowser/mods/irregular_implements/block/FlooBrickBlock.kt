package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FlooBrickBlock : Block(
	Properties.ofFullCopy(Blocks.BRICKS)
		.strength(2f, 10f)
), EntityBlock {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
		TODO("Not yet implemented")
	}

}