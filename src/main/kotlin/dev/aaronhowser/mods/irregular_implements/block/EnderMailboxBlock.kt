package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderMailboxBlockEntity
import dev.aaronhowser.mods.irregular_implements.handler.ender_letter.EnderLetterHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.items.ItemHandlerHelper
import kotlin.jvm.optionals.getOrNull

class EnderMailboxBlock : Block(Properties.ofFullCopy(Blocks.IRON_BLOCK)), EntityBlock {

	init {
		registerDefaultState(
			stateDefinition.any()
				.setValue(IS_FLAG_RAISED, false)
				.setValue(FACING, Direction.NORTH)
		)
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return EnderMailboxBlockEntity(pos, state)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(IS_FLAG_RAISED, FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState()
			.setValue(FACING, context.nearestLookingDirections.first { it.axis.isHorizontal })
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		return canSupportCenter(level, pos.below(), Direction.UP)
	}

	override fun updateShape(state: BlockState, direction: Direction, neighborState: BlockState, level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos): BlockState {
		return if (state.canSurvive(level, pos)) {
			state
		} else {
			Blocks.AIR.defaultBlockState()
		}
	}

	override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
		val direction = state.getValue(FACING)

		return when (direction) {
			Direction.NORTH, Direction.SOUTH -> SHAPE_NS
			Direction.EAST, Direction.WEST -> SHAPE_EW
			else -> SHAPE_NS
		}
	}

	override fun useItemOn(
		stack: ItemStack,
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hand: InteractionHand,
		hitResult: BlockHitResult
	): ItemInteractionResult {
		if (!stack.`is`(ModItems.ENDER_LETTER)) return super.useItemOn(stack, state, level, pos, player, hand, hitResult)

		if (sendLetter(player, stack)) {
			stack.shrink(1)
			return ItemInteractionResult.SUCCESS
		} else {
			return ItemInteractionResult.CONSUME
		}
	}

	companion object {
		val IS_FLAG_RAISED: BooleanProperty = BooleanProperty.create("is_flag_raised")
		val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING

		val SHAPE_NS: VoxelShape = box(5.0, 0.0, 1.0, 11.0, 22.0, 15.0)
		val SHAPE_EW: VoxelShape = box(1.0, 0.0, 5.0, 15.0, 22.0, 11.0)

		fun sendLetter(
			player: Player,
			stack: ItemStack
		): Boolean {
			val component = stack.get(ModDataComponents.ENDER_LETTER_CONTENTS) ?: return false
			val recipientName = component.recipient.getOrNull()
			if (recipientName == null) {
				player.displayClientMessage(Component.literal("This letter has no recipient!"), true)
				return false
			}

			val level = player.level() as? ServerLevel ?: return false
			val recipient = level.server.playerList.getPlayerByName(recipientName)

			if (recipient == null) {
				player.displayClientMessage(Component.literal("The recipient '$recipientName' is not online!"), true)
				return false
			}

			val handler = EnderLetterHandler.get(level)
			val inventory = handler.getInventory(recipient)

			if (!inventory.hasRoom()) {
				player.displayClientMessage(Component.literal("The recipient '$recipientName' has no room for your letter!"), true)
				return false
			}

			ItemHandlerHelper.insertItem(inventory, stack.copy(), false)
			return true
		}
	}

}