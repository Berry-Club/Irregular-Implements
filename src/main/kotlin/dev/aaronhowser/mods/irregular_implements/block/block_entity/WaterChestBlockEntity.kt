package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState

class WaterChestBlockEntity(
    pos: BlockPos,
    blockState: BlockState
) : ChestBlockEntity(ModBlockEntities.WATER_CHEST.get(), pos, blockState) {

    override fun getDefaultName(): Component {
        return this.blockState.block.name
    }

}