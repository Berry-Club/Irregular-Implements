package dev.aaronhowser.mods.irregular_implements.block.plate

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities

class CollectionPlateBlock : BasePlateBlock() {

	//TODO: Maybe make a plop sound on inserting items?
	override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
		if (entity !is ItemEntity) return

		for (direction in directions) {
			if (entity.item.isEmpty) continue

			val blockPos = pos.relative(direction)
			val itemCap = level.getCapability(Capabilities.ItemHandler.BLOCK, blockPos, direction.opposite) ?: continue

			for (slotIndex in 0 until itemCap.slots) {
				val remainingStack = itemCap.insertItem(slotIndex, entity.item.copy(), false)

				entity.item = remainingStack

				if (remainingStack.isEmpty) {
					entity.discard()
					break
				}
			}
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