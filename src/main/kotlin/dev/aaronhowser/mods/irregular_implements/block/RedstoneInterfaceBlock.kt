package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneInterfaceAdvancedBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneInterfaceBasicBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceBlock(
	val isAdvanced: Boolean
) : EntityBlock, Block(
	Properties
		.ofFullCopy(Blocks.IRON_BLOCK)
) {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return if (isAdvanced) {
			RedstoneInterfaceAdvancedBlockEntity(pos, state)
		} else {
			RedstoneInterfaceBasicBlockEntity(pos, state)
		}
	}

	override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston)

		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is RedstoneInterfaceBlockEntity) {
			blockEntity.updateTargets()
		}
	}

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		val type = if (isAdvanced) {
			ModBlockEntities.ADVANCED_REDSTONE_INTERFACE.get()
		} else {
			ModBlockEntities.BASIC_REDSTONE_INTERFACE.get()
		}

		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			type,
			RedstoneInterfaceBlockEntity::tick
		)
	}

}