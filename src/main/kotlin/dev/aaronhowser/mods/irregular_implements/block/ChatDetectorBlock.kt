package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

class ChatDetectorBlock : EntityBlock, Block(
    Properties
        .ofFullCopy(Blocks.DISPENSER)
) {

    companion object {
        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
    }

    init {
        registerDefaultState(
            defaultBlockState()
                .setValue(ENABLED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(ENABLED)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is ChatDetectorBlockEntity && placer != null) {
            blockEntity.ownerUuid = placer.uuid
        }

    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return ChatDetectorBlockEntity(pos, state)
    }
}