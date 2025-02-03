package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.PlayerInterfaceBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PlayerInterfaceBlock : Block(Properties.ofFullCopy(Blocks.DISPENSER)), EntityBlock {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return PlayerInterfaceBlockEntity(pos, state)
    }

}