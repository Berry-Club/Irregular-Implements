package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.FlooBrickBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FlooBrickBlock : Block(
	Properties.ofFullCopy(Blocks.BRICKS)
		.strength(2f, 10f)
), EntityBlock {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return FlooBrickBlockEntity(pos, state)
	}

	override fun onRemove(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, movedByPiston: Boolean) {
		if (oldState.`is`(newState.block)) return

		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is FlooBrickBlockEntity) blockEntity.blockBroken()

		super.onRemove(oldState, level, pos, newState, movedByPiston)
	}

}