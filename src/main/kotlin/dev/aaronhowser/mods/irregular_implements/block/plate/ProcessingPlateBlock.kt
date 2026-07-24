package dev.aaronhowser.mods.irregular_implements.block.plate

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.getDirectionName
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.status
import dev.aaronhowser.mods.irregular_implements.block_entity.ProcessingPlateBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.ItemHandlerHelper

class ProcessingPlateBlock : BasePlateBlock(), EntityBlock {

	init {
		registerDefaultState(
			defaultBlockState()
				.setValue(INPUT, Direction.NORTH)
				.setValue(OUTPUT, Direction.SOUTH)
		)
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(INPUT, OUTPUT)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState()
			.setValue(INPUT, context.horizontalDirection.opposite)
			.setValue(OUTPUT, context.horizontalDirection)
	}

	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		if (level.isClientSide || entity !is ItemEntity || entity.item.isEmpty) return

		val itemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos.below(), Direction.UP) ?: return
		val remainingStack = ItemHandlerHelper.insertItemStacked(itemHandler, entity.item.copy(), false)
		entity.item = remainingStack

		if (remainingStack.isEmpty) entity.discard()
	}

	override fun useWithoutItem(
		state: BlockState,
		level: Level,
		pos: BlockPos,
		player: Player,
		hitResult: BlockHitResult
	): InteractionResult {
		val property = if (player.isSecondaryUseActive) OUTPUT else INPUT

		val directions = if (property == OUTPUT) {
			listOf(
				Direction.NORTH,
				Direction.EAST,
				Direction.SOUTH,
				Direction.WEST
			)
		} else {
			listOf(
				Direction.DOWN,
				Direction.NORTH,
				Direction.EAST,
				Direction.SOUTH,
				Direction.WEST,
				Direction.UP
			)
		}

		val currentDirection = state.getValue(property)
		val nextDirection = directions[(directions.indexOf(currentDirection) + 1) % directions.size]

		if (!level.isClientSide) {
			level.setBlockAndUpdate(pos, state.setValue(property, nextDirection))
			val message = if (property == OUTPUT) {
				ModMessageLang.PROCESSING_PLATE_OUTPUT_DIRECTION
			} else {
				ModMessageLang.PROCESSING_PLATE_INPUT_DIRECTION
			}
			player.status(message.toComponent(nextDirection.getDirectionName()))
		}

		return InteractionResult.sidedSuccess(level.isClientSide)
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		if (super.canSurvive(state, level, pos)) return true
		if (level !is Level) return false

		return level.getCapability(Capabilities.ItemHandler.BLOCK, pos.below(), Direction.UP) != null
	}

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
		return ProcessingPlateBlockEntity(pos, state)
	}

	override fun <T : BlockEntity?> getTicker(
		level: Level,
		state: BlockState,
		blockEntityType: BlockEntityType<T>
	): BlockEntityTicker<T>? {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			ModBlockEntityTypes.PROCESSING_PLATE.get(),
			ProcessingPlateBlockEntity::tick
		)
	}

	companion object {
		val INPUT: DirectionProperty = DirectionProperty.create("input")
		val OUTPUT: DirectionProperty = DirectionProperty.create("output", Direction.Plane.HORIZONTAL)
	}
}