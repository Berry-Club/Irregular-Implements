package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block_entity.FilteredPlatformBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class FilteredPlatformBlock : PlatformBlock(ModBlocks.SUPER_LUBRICANT_ICE.get()), EntityBlock {


	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is FilteredPlatformBlockEntity) {
			player.openMenu(blockEntity)
			return InteractionResult.SUCCESS
		}

		return super.useWithoutItem(state, level, pos, player, hitResult)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return FilteredPlatformBlockEntity(pos, state)
	}

}