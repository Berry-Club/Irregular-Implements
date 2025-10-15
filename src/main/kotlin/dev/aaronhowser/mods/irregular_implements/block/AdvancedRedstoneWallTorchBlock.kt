package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.AdvancedRedstoneTorchBlock.Companion.GREEN_COLOR
import dev.aaronhowser.mods.irregular_implements.block.AdvancedRedstoneTorchBlock.Companion.RED_COLOR
import dev.aaronhowser.mods.irregular_implements.block.block_entity.AdvancedRedstoneTorchBlockEntity
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.nextRange
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RedstoneTorchBlock
import net.minecraft.world.level.block.RedstoneWallTorchBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class AdvancedRedstoneWallTorchBlock : RedstoneWallTorchBlock(Properties.ofFullCopy(Blocks.REDSTONE_WALL_TORCH)), EntityBlock {

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is MenuProvider) {
			player.openMenu(blockEntity)
			return InteractionResult.sidedSuccess(level.isClientSide)
		}

		return super.useWithoutItem(state, level, pos, player, hitResult)
	}

	override fun getSignal(blockState: BlockState, blockAccess: BlockGetter, pos: BlockPos, side: Direction): Int {
		if (side == blockState.getValue(FACING)) {
			return 0
		}

		val blockEntity = blockAccess.getBlockEntity(pos)
		if (blockEntity is AdvancedRedstoneTorchBlockEntity) {
			val isLit = blockState.getValue(LIT)
			return blockEntity.getStrength(isLit)
		}

		return super.getSignal(blockState, blockAccess, pos, side)
	}

	override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
		val color = if (state.getValue(RedstoneTorchBlock.LIT)) RED_COLOR else GREEN_COLOR

		val dir = state.getValue(FACING).opposite

		val scaling = 0.27

		val x = pos.x.toDouble() + random.nextRange(0.4, 0.6) + scaling * dir.stepX
		val y = pos.y.toDouble() + random.nextRange(0.6, 0.8) + 0.22
		val z = pos.z.toDouble() + random.nextRange(0.4, 0.6) + scaling * dir.stepZ

		level.addParticle(
			DustParticleOptions(color, 1f),
			x, y, z,
			0.0, 0.0, 0.0
		)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return AdvancedRedstoneTorchBlockEntity(pos, state)
	}

}