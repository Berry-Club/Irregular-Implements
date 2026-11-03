package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.block.SimpleContainerBlock
import dev.aaronhowser.mods.irregular_implements.block_entity.RedstoneInterfaceAdvancedBlockEntity
import dev.aaronhowser.mods.irregular_implements.block_entity.RedstoneInterfaceBasicBlockEntity
import dev.aaronhowser.mods.irregular_implements.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult

class RedstoneInterfaceBlock(
	val isAdvanced: Boolean
) : SimpleContainerBlock(Properties.ofFullCopy(Blocks.IRON_BLOCK)), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(POWER, 0)
		)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return if (isAdvanced) {
			RedstoneInterfaceAdvancedBlockEntity(pos, state)
		} else {
			RedstoneInterfaceBasicBlockEntity(pos, state)
		}
	}

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		val type = if (isAdvanced) {
			ModBlockEntityTypes.ADVANCED_REDSTONE_INTERFACE.get()
		} else {
			ModBlockEntityTypes.BASIC_REDSTONE_INTERFACE.get()
		}

		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			type,
			RedstoneInterfaceBlockEntity::tick
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(POWER)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState()
			.setValue(POWER, context.level.getBestNeighborSignal(context.clickedPos))
	}

	override fun canConnectRedstone(
		state: BlockState,
		level: BlockGetter,
		pos: BlockPos,
		direction: Direction?
	): Boolean {
		return direction != null
	}

	override fun neighborChanged(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		block: Block,
		fromPos: BlockPos,
		isMoving: Boolean
	) {
		val oldPower = state.getValue(POWER)
		val newPower = level.getBestNeighborSignal(pos)
		if (oldPower != newPower) {
			level.setBlock(pos, state.setValue(POWER, newPower), 3)

			val be = level.getBlockEntity(pos)
			if (be is RedstoneInterfaceBlockEntity) {
				be.updateTargets()
			}
		}
	}

	override fun useWithoutItem(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		val blockEntity = level.getBlockEntity(pos)
		if (blockEntity is MenuProvider) {
			player.openMenu(blockEntity)
			return InteractionResult.sidedSuccess(level.isClientSide)
		}

		return super.useWithoutItem(state, level, pos, player, hitResult)
	}

	override fun onRemove(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		newState: BlockState,
		movedByPiston: Boolean
	) {
		val be = level.getBlockEntity(pos)
		if (be is RedstoneInterfaceBlockEntity) {
			be.updateTargets()
		}

		super.onRemove(state, level, pos, newState, movedByPiston)
	}


	companion object {
		val POWER: IntegerProperty = BlockStateProperties.POWER
	}

}