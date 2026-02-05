package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.aaron.AaronExtensions.status
import dev.aaronhowser.mods.aaron.AaronUtil
import dev.aaronhowser.mods.irregular_implements.block.block_entity.EnderMailboxBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.handler.ender_letter.EnderLetterHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
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
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.items.ItemHandlerHelper
import java.util.*
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

	override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
		super.setPlacedBy(level, pos, state, placer, stack)

		if (placer is Player) {
			val blockEntity = level.getBlockEntity(pos)
			if (blockEntity is EnderMailboxBlockEntity) {
				blockEntity.setOwner(placer)
			}
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

	override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
		if (level.isClientSide) return InteractionResult.PASS

		val blockEntity = level.getBlockEntity(pos) as? EnderMailboxBlockEntity ?: return InteractionResult.PASS

		if (blockEntity.ownerUuid == player.uuid) {
			player.openMenu(blockEntity)
			return InteractionResult.SUCCESS
		} else {
			player.status(ModMessageLang.ENDER_MAILBOX_NOT_OWNER.toComponent())
			return InteractionResult.PASS
		}
	}

	override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntityTypes.ENDER_MAILBOX.get(),
			EnderMailboxBlockEntity::tick
		)
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

			val items = component.stacks
			for (i in items.indices) {
				if (!items[i].isEmpty) break

				player.status(ModMessageLang.ENDER_LETTER_EMPTY.toComponent())
				return false
			}

			val senderName = component.sender.getOrNull()
			if (senderName != null) {
				player.status(ModMessageLang.ENDER_LETTER_ALREADY_SENT.toComponent())
				return false
			}

			val recipientName = component.recipient.getOrNull()
			if (recipientName == null) {
				player.status(ModMessageLang.ENDER_LETTER_NO_RECIPIENT.toComponent())
				return false
			}

			val level = player.level() as? ServerLevel ?: return false

			val recipientUuid = AaronUtil.getCachedUuid(recipientName)

			if (recipientUuid == null) {
				player.status(ModMessageLang.ENDER_LETTER_NO_RECIPIENT.toComponent(recipientName))
				return false
			}

			val handler = EnderLetterHandler.get(level)
			val inventory = handler.getOrCreateInventory(recipientUuid)

			if (!inventory.hasRoom()) {
				player.status(ModMessageLang.ENDER_LETTER_RECIPIENT_NO_ROOM.toComponent(recipientName))
				return false
			}

			val newLetter = stack.copy()
			newLetter.set(
				ModDataComponents.ENDER_LETTER_CONTENTS,
				component.copy(sender = Optional.of(player.name.string))
			)

			ItemHandlerHelper.insertItem(inventory, newLetter, false)
			return true
		}
	}

}