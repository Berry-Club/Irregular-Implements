package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState

abstract class SpecialChestBlockEntity(
	type: BlockEntityType<*>,
	pos: BlockPos,
	state: BlockState
) : ChestBlockEntity(type, pos, state) {

	override fun getDefaultName(): Component {
		return this.blockState.block.name
	}

	class NatureChestBlockEntity(pos: BlockPos, state: BlockState) : SpecialChestBlockEntity(ModBlockEntities.NATURE_CHEST.get(), pos, state)
	class WaterChestBlockEntity(pos: BlockPos, state: BlockState) : SpecialChestBlockEntity(ModBlockEntities.WATER_CHEST.get(), pos, state)
}