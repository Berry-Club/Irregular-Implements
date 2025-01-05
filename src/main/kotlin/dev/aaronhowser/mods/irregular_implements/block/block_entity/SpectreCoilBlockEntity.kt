package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.SpectreCoilBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class SpectreCoilBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.SPECTRE_COIL.get(), pPos, pBlockState) {

    constructor(pPos: BlockPos, pBlockState: BlockState, pType: SpectreCoilBlock.Type) : this(pPos, pBlockState) {
        this.type = pType
    }

    var type: SpectreCoilBlock.Type = SpectreCoilBlock.Type.BASIC
        private set

}