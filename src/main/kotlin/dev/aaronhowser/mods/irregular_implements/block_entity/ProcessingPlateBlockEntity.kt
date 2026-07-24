package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.irregular_implements.block.plate.ProcessingPlateBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.ItemHandlerHelper

class ProcessingPlateBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.PROCESSING_PLATE.get(), pos, blockState) {

	private fun tick() {
		val level = level as? ServerLevel ?: return
		if (level.gameTime % TRANSFER_INTERVAL != 0L) return

		val inputDirection = blockState.getValue(ProcessingPlateBlock.INPUT)
		val outputDirection = blockState.getValue(ProcessingPlateBlock.OUTPUT)

		val inputHandler = level.getCapability(
			Capabilities.ItemHandler.BLOCK,
			worldPosition.relative(inputDirection),
			inputDirection.opposite
		) ?: return

		val outputHandler = level.getCapability(
			Capabilities.ItemHandler.BLOCK,
			worldPosition.relative(outputDirection),
			outputDirection.opposite
		)

		for (slot in 0 until inputHandler.slots) {
			val available = inputHandler.extractItem(slot, MAX_TRANSFER, true)
			if (available.isEmpty) continue

			val amount = getTransferableAmount(outputHandler, available)
			if (amount <= 0) return

			val extracted = inputHandler.extractItem(slot, amount, false)
			if (extracted.isEmpty) return

			val remainder = if (outputHandler == null) {
				extracted
			} else {
				ItemHandlerHelper.insertItemStacked(outputHandler, extracted, false)
			}

			if (!remainder.isEmpty) dropItem(level, outputDirection, remainder)
			return
		}
	}

	private fun getTransferableAmount(outputHandler: IItemHandler?, stack: ItemStack): Int {
		if (outputHandler == null) return stack.count

		val remainder = ItemHandlerHelper.insertItemStacked(outputHandler, stack.copy(), true)
		return stack.count - remainder.count
	}

	private fun dropItem(level: ServerLevel, outputDirection: Direction, stack: ItemStack) {
		val center = worldPosition.center
		val item = ItemEntity(
			level,
			center.x + outputDirection.stepX * 0.65,
			worldPosition.y + 0.1,
			center.z + outputDirection.stepZ * 0.65,
			stack
		)
		item.setDeltaMovement(outputDirection.stepX * 0.1, 0.0, outputDirection.stepZ * 0.1)
		item.setPickUpDelay(10)
		level.addFreshEntity(item)
	}

	companion object {
		private const val TRANSFER_INTERVAL = 10L
		private const val MAX_TRANSFER = 64

		fun tick(
			level: Level,
			pos: BlockPos,
			state: BlockState,
			blockEntity: ProcessingPlateBlockEntity
		) {
			if (!level.isClientSide) blockEntity.tick()
		}
	}
}