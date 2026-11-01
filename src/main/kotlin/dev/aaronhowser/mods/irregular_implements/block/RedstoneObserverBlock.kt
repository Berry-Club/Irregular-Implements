package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
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

//TODO: Needs new name
class RedstoneObserverBlock : Block(Properties.ofFullCopy(Blocks.IRON_BLOCK)), EntityBlock {

	companion object {
		@JvmField
		val CODEC: MapCodec<RedstoneObserverBlock> = simpleCodec { RedstoneObserverBlock() }
	}

	public override fun codec(): MapCodec<RedstoneObserverBlock> {
		return CODEC
	}

	init {
		registerDefaultState(stateDefinition.any())
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return RedstoneObserverBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(
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

		val blockEntity = level.getBlockEntity(pos, ModBlockEntityTypes.REDSTONE_OBSERVER.get()).orElse(null) ?: return
		RedstoneObserverBlockEntity.updateNeighborPower(level, pos, state, blockEntity, power = true, direct = true, analog = true)

	}


	override fun onRemove(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		newState: BlockState,
		isMoving: Boolean
	) {
		super.onRemove(state, level, pos, newState, isMoving)

		if (!level.isClientSide && !isMoving && state.block !== newState.block) {
			RedstoneObserverBlockEntity.updateNeighborPower(level, pos, state, null, power = true, direct = true, analog = true)
		}
	}

	override fun hasAnalogOutputSignal(state: BlockState): Boolean {
		return true
	}

	override fun isSignalSource(state: BlockState): Boolean {
		return true
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		val shiftAmount = direction.ordinal * 4
		val observer = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
		return ((observer.getDirectionalPower()).ushr(shiftAmount)).and(0b1111)
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		val shiftAmount = direction.ordinal * 4
		val observer = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
		return ((observer.getDirectionalDirect()).ushr(shiftAmount)).and(0b1111)
	}

	override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos): Int {
		val observer = level.getBlockEntity(pos) as? RedstoneObserverBlockEntity ?: return 0
		return observer.getAnalog()
	}

}