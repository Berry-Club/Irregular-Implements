package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderEnergyDistributorBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class EnderEnergyDistributorBlock : Block(Properties.ofFullCopy(Blocks.OBSIDIAN)), EntityBlock {

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return EnderEnergyDistributorBlockEntity(pos, state)
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		val blockEntity = level.getBlockEntity(pos)

		if (blockEntity is MenuProvider) {
			player.openMenu(blockEntity)
			return InteractionResult.SUCCESS
		}

		return InteractionResult.PASS
	}

	override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, movedByPiston: Boolean) {
		val be = level.getBlockEntity(pos)
		if (be is EnderEnergyDistributorBlockEntity) {
			Containers.dropContents(level, pos, be.container)
		}
		super.onRemove(state, level, pos, newState, movedByPiston)
	}

	override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntities.ENDER_ENERGY_DISTRIBUTOR.get(),
			EnderEnergyDistributorBlockEntity::tick
		)
	}

}