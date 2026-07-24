package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState

class SpecialChestBlockEntity(
	type: BlockEntityType<out ChestBlockEntity>,
	pos: BlockPos,
	state: BlockState
) : ChestBlockEntity(type, pos, state) {

	override fun getDefaultName(): Component {
		return this.blockState.block.name
	}

	companion object {
		fun nature(pos: BlockPos, state: BlockState): SpecialChestBlockEntity =
			SpecialChestBlockEntity(ModBlockEntityTypes.NATURE_CHEST.get(), pos, state)

		fun water(pos: BlockPos, state: BlockState): SpecialChestBlockEntity =
			SpecialChestBlockEntity(ModBlockEntityTypes.NATURE_CHEST.get(), pos, state)
	}

}