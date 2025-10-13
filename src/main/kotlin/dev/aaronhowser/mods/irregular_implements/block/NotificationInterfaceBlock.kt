package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.AutoPlacerBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.NotificationInterfaceBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult

class NotificationInterfaceBlock : Block(Properties.ofFullCopy(Blocks.DISPENSER)), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(ENABLED, false)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(ENABLED)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return NotificationInterfaceBlockEntity(pos, state)
	}

	override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
		super.setPlacedBy(level, pos, state, placer, stack)

		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is NotificationInterfaceBlockEntity && placer != null) {
			blockEntity.ownerUuid = placer.uuid
		}
	}

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is NotificationInterfaceBlockEntity) {
			player.openMenu(blockEntity)
			blockEntity.sendStringUpdate()
		}

		return InteractionResult.SUCCESS
	}

	override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
		val blockEntity = level.getBlockEntity(pos) as? NotificationInterfaceBlockEntity ?: return

		val isPowered = level.hasNeighborSignal(pos)
		val wasEnabled = state.getValue(ENABLED)

		if (isPowered != wasEnabled) {
			val newState = state.setValue(ENABLED, isPowered)
			level.setBlockAndUpdate(pos, newState)

			if (isPowered) {
				blockEntity.notifyOwner()
			}
		}
	}

	override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, movedByPiston: Boolean) {
		if (!state.`is`(newState.block)) {
			val be = level.getBlockEntity(pos)
			if (be is NotificationInterfaceBlockEntity) {
				Containers.dropContents(level, pos, be.container)
			}
		}

		super.onRemove(state, level, pos, newState, movedByPiston)
	}

	companion object {
		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
	}

}