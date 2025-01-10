package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.GlobalChatDetectorBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

class GlobalChatDetectorBlock : EntityBlock, Block(
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

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return GlobalChatDetectorBlockEntity(pos, state)
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
        return true
    }

    override fun isSignalSource(state: BlockState): Boolean {
        return true
    }

    override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return if (state.getValue(ENABLED)) 15 else 0
    }

    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return getDirectSignal(state, level, pos, direction)
    }

}