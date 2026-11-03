package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isServerSide
import dev.aaronhowser.mods.irregular_implements.block_entity.RedstoneObserverBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
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
import net.minecraft.world.level.redstone.NeighborUpdater
import kotlin.jvm.optionals.getOrNull

//TODO: Needs new name
class RedstoneObserverBlock : Block(
	Properties.ofFullCopy(Blocks.IRON_BLOCK)
		.isRedstoneConductor(Blocks::never)
), EntityBlock {

	init {
		registerDefaultState(stateDefinition.any())
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return RedstoneObserverBlockEntity(pos, state)
	}

	override fun <T : BlockEntity> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntityTypes.REDSTONE_OBSERVER.get(),
			RedstoneObserverBlockEntity::tick
		)
	}

	override fun onPlace(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		oldState: BlockState,
		isMoving: Boolean
	) {
		super.onPlace(state, level, pos, oldState, isMoving)

		if (level.isClientSide || isMoving) return
		RedstoneObserverBlockEntity.updateNeighborPower(level, pos, state)
	}


	override fun onRemove(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		newState: BlockState,
		isMoving: Boolean
	) {
		super.onRemove(state, level, pos, newState, isMoving)

		if (level.isServerSide && !isMoving && state.block !== newState.block) {
			for (direction in NeighborUpdater.UPDATE_ORDER) {
				val neighborPos = pos.relative(direction)
				level.updateNeighborsAtExceptFromFacing(neighborPos, state.block, direction.opposite)
			}

			level.updateNeighbourForOutputSignal(pos, state.block)
		}
	}

	override fun hasAnalogOutputSignal(state: BlockState): Boolean {
		return true
	}

	override fun isSignalSource(state: BlockState): Boolean {
		return true
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		val observer = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
		return observer.getWeakPower(direction)
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		val observer = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
		return observer.getStrongPower(direction)
	}

	override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos): Int {
		val observer = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
		return observer.getAnalogPower()
	}

}