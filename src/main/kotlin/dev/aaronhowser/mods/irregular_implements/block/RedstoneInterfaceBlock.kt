package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneInterfaceBasicBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceBlock(
	val isAdvanced: Boolean
) : EntityBlock, Block(
	Properties
		.ofFullCopy(Blocks.IRON_BLOCK)
) {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return RedstoneInterfaceBasicBlockEntity(pos, state)
	}

	override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston)

		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is RedstoneInterfaceBasicBlockEntity) {
			blockEntity.updateTarget()
		}
	}

}