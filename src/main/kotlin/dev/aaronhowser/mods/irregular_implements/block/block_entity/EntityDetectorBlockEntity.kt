package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isServerSide
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class EntityDetectorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.ENTITY_DETECTOR.get(), pos, blockState) {

	fun tick() {

	}

	companion object {
		fun tick(
			level: Level,
			pos: BlockPos,
			state: BlockState,
			blockEntity: EntityDetectorBlockEntity
		) {
			if (level.isServerSide) blockEntity.tick()
		}
	}

}