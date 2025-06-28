package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
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

class ContactButtonBlock(
	properties: Properties =
		Properties
			.ofFullCopy(Blocks.STONE)
) : DirectionalBlock(properties) {

	companion object {
		val CODEC: MapCodec<ContactButtonBlock> = simpleCodec(::ContactButtonBlock)

		val ENABLED: BooleanProperty = BlockStateProperties.ENABLED

		fun handleClickBlock(
			level: Level,
			pos: BlockPos,
		) {
			if (level.isClientSide) return

			for (direction in Direction.entries) {
				val blockPos = pos.relative(direction)
				val blockState = level.getBlockState(blockPos)

				if (!blockState.`is`(ModBlocks.CONTACT_BUTTON.get())) continue
				if (blockState.getValue(FACING) != direction.opposite) continue
				if (blockState.getValue(ENABLED)) continue

				press(level, blockPos, blockState)
			}
		}

		private fun press(
			level: Level,
			pos: BlockPos,
			blockState: BlockState
		) {
			val newState = blockState.cycle(ENABLED)
			level.setBlockAndUpdate(pos, newState)

			level.playSound(
				null,
				pos,
				SoundEvents.STONE_BUTTON_CLICK_ON,
				SoundSource.BLOCKS,
			)

			level.scheduleTick(pos, ModBlocks.CONTACT_BUTTON.get(), 20)
		}
	}

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(ENABLED, false)
		)
	}

	override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
		val newState = state.setValue(ENABLED, false)
		level.setBlockAndUpdate(pos, newState)

		level.playSound(
			null,
			pos,
			SoundEvents.STONE_BUTTON_CLICK_OFF,
			SoundSource.BLOCKS,
		)
	}

	override fun codec(): MapCodec<ContactButtonBlock> = CODEC

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(FACING, ENABLED)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirection.opposite)
	}

	override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return if (state.getValue(ENABLED)) 15 else 0
	}

	override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
		return if (state.getValue(ENABLED)) 15 else 0
	}

	override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
		return direction != state.getValue(FACING).opposite
	}

}