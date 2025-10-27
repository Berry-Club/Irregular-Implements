package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.MoonPhaseDetectorBlock.Companion.INVERTED
import dev.aaronhowser.mods.irregular_implements.block.MoonPhaseDetectorBlock.Companion.POWER
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class MoonPhaseDetectorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.MOON_PHASE_DETECTOR.get(), pos, blockState) {
	companion object {
		fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: MoonPhaseDetectorBlockEntity) {
			if (level.gameTime % 20 != 0L) return

			val moonPhase = level.moonPhase

			val isInverted = state.getValue(INVERTED)

			val power = if (isInverted) {
				moonPhase * 2
			} else {
				14 - moonPhase * 2
			}

			if (state.getValue(POWER) != power) {
				val newState = state.setValue(POWER, power)
				level.setBlockAndUpdate(pos, newState)
			}
		}
	}
}