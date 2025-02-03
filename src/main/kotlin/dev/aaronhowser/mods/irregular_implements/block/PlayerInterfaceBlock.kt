package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.PlayerInterfaceBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PlayerInterfaceBlock : Block(Properties.ofFullCopy(Blocks.DISPENSER)), EntityBlock {

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is PlayerInterfaceBlockEntity && placer != null) {
            blockEntity.ownerUuid = placer.uuid
        }

    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return PlayerInterfaceBlockEntity(pos, state)
    }

}