package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.NatureChestBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class NatureChestBlock : ChestBlock(
    Properties.ofFullCopy(Blocks.CHEST),
    { ModBlockEntities.NATURE_CHEST.get() }
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return NatureChestBlockEntity(pos, state)
    }

}