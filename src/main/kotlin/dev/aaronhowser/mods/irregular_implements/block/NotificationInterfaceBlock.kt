package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.NotificationInterfaceBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class NotificationInterfaceBlock : Block(Properties.ofFullCopy(Blocks.DISPENSER)), EntityBlock {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return NotificationInterfaceBlockEntity(pos, state)
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is NotificationInterfaceBlockEntity) {
            player.openMenu(blockEntity)
            blockEntity.sendStringUpdate()
        }

        return InteractionResult.SUCCESS
    }

}