package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.FlooBrickBlockEntity
import dev.aaronhowser.mods.irregular_implements.handler.floo.FlooNetworkSavedData
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.status
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

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

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		if (level !is ServerLevel) return InteractionResult.PASS
		val blockEntity = level.getBlockEntity(pos) as? FlooBrickBlockEntity ?: return InteractionResult.PASS

		val uuid = blockEntity.uuid ?: blockEntity.masterUUID

		if (uuid == null) {
			player.status(Component.literal("Nameless fireplace"))
			return InteractionResult.SUCCESS_NO_ITEM_USED
		}

		val network = FlooNetworkSavedData.get(level)
		val fireplace = network.findFireplace(uuid)

		if (fireplace == null) {
			player.status(Component.literal("Orphaned fireplace"))
			return InteractionResult.SUCCESS_NO_ITEM_USED
		}

		player.status(Component.literal("Fireplace: ${fireplace.name ?: "<unnamed>"}"))

		return InteractionResult.SUCCESS_NO_ITEM_USED
	}

}