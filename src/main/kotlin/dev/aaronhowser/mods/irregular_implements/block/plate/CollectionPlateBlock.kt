package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities

class CollectionPlateBlock : BasePlateBlock() {

	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		if (entity !is ItemEntity) return
		if (level.isClientSide) return

		var insertedItem = false

		for (direction in directions) {
			if (entity.item.isEmpty) continue

			val blockPos = pos.relative(direction)
			val itemCap = level.getCapability(Capabilities.ItemHandler.BLOCK, blockPos, direction.opposite) ?: continue

			for (slotIndex in 0 until itemCap.slots) {
				val previousCount = entity.item.count
				val remainingStack = itemCap.insertItem(slotIndex, entity.item.copy(), false)

				entity.item = remainingStack
				if (remainingStack.count < previousCount) insertedItem = true

				if (remainingStack.isEmpty) {
					entity.discard()
					break
				}
			}
		}

		if (insertedItem) {
			level.playSound(
				null,
				pos,
				SoundEvents.ITEM_PICKUP,
				SoundSource.BLOCKS,
				0.2f,
				1f
			)
		}
	}

	override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
		if (super.canSurvive(state, level, pos)) return true
		if (level !is Level) return false

		return level.getCapability(Capabilities.ItemHandler.BLOCK, pos.below(), Direction.UP) != null
	}

	companion object {
		private val directions = setOf(
			Direction.DOWN,
			Direction.NORTH,
			Direction.SOUTH,
			Direction.WEST,
			Direction.EAST,
			Direction.UP
		)
	}

}