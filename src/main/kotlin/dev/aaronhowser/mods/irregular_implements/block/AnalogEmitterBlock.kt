package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult

class AnalogEmitterBlock(
	properties: Properties =
		Properties
			.ofFullCopy(Blocks.TARGET)
			.isRedstoneConductor(Blocks::never)
) : DirectionalBlock(properties) {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(FACING, Direction.NORTH)
				.setValue(ENABLED, false)
				.setValue(POWER, 0)
		)
	}

	override fun codec(): MapCodec<AnalogEmitterBlock> = CODEC

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING, ENABLED, POWER)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	//TODO: GUI?
	override fun useWithoutItem(
		oldState: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		if (level.isClientSide) return InteractionResult.SUCCESS
		if (!player.getItemInHand(player.usedItemHand).isEmpty) return InteractionResult.PASS

		var newPower = oldState.getValue(POWER) + if (player.isSecondaryUseActive) -1 else 1
		newPower = newPower and 15

		val component = newPower.toString()
			.toComponent()
			.withStyle(ChatFormatting.RED)

		player.displayClientMessage(component, true)

		val newState = oldState.setValue(POWER, newPower)
		level.setBlockAndUpdate(pos, newState)

		val pitch = 0.5f + (newPower.toFloat() / 15f) * (2f - 0.5f)   // 0.5 to 5
		level.playSound(
			null,
			pos,
			SoundEvents.UI_BUTTON_CLICK.value(),
			SoundSource.BLOCKS,
			0.15f,
			pitch
		)

		return InteractionResult.SUCCESS
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		return true
	}

	override fun neighborChanged(
		oldState: BlockState,
		level: Level,
		pos: BlockPos,
		block: Block,
		fromPos: BlockPos,
		isMoving: Boolean
	) {
		if (level.isClientSide) return

		val facing = oldState.getValue(FACING)
		val facingSideIsPowered = level.hasSignal(pos.relative(facing), facing.opposite)

		val newState = oldState.setValue(ENABLED, facingSideIsPowered)
		level.setBlockAndUpdate(pos, newState)
	}

	override fun isSignalSource(state: BlockState): Boolean {
		return true
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		if (!state.getValue(ENABLED)) return 0

		return state.getValue(POWER)
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return getDirectSignal(state, level, pos, direction)
	}

	companion object {
		private val CODEC = simpleCodec(::AnalogEmitterBlock)
		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
		val POWER: IntegerProperty = BlockStateProperties.POWER
	}

}