package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RainShieldBlock : EntityBlock, Block(
    Properties
        .of()
        .sound(SoundType.STONE)
        .strength(2f)
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = RainShieldBlockEntity(pos, state)

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        movedByPiston: Boolean
    ) {
        val blockEntity = level.getBlockEntity(pos) as? RainShieldBlockEntity
        if (blockEntity != null) {
            synchronized(RainShieldBlockEntity.shields) {
                RainShieldBlockEntity.shields.remove(blockEntity)
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston)
    }

}