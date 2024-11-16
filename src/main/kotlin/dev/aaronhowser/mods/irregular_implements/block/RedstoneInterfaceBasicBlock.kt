package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneInterfaceBasicBlockEntity
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceBasicBlock : EntityBlock, Block(
    Properties
        .ofFullCopy(Blocks.IRON_BLOCK)
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return RedstoneInterfaceBasicBlockEntity(pos, state)
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        if (blockEntityType != ModBlockEntities.REDSTONE_INTERFACE.get()) return null

        return BlockEntityTicker { tLevel, tPos, tState, _ ->
            RedstoneInterfaceBasicBlockEntity.tick(tLevel, tPos, tState)
        }
    }

}