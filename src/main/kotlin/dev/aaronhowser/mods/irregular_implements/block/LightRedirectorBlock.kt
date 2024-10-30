package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.LightRedirectorBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class LightRedirectorBlock : EntityBlock, Block(
    Properties
        .of()
        .sound(SoundType.WOOD)
        .strength(2f)
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = LightRedirectorBlockEntity(pos, state)

}