package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.DispenserBlockEntity
import net.minecraft.world.level.block.state.BlockState

class IronDropperBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : DispenserBlockEntity(ModBlockEntities.IRON_DROPPER.get(), pPos, pBlockState)