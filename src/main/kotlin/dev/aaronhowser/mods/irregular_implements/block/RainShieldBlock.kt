package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

class RainShieldBlock : EntityBlock, Block(
    Properties
        .of()
        .sound(SoundType.STONE)
        .strength(2f)
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = RainShieldBlockEntity(pos, state)

    companion object {
        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(ENABLED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(ENABLED, true)
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
        return true
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        block: Block,
        fromPos: BlockPos,
        isMoving: Boolean
    ) {
        if (level.isClientSide) return

        val redstoneStrength = level.getBestNeighborSignal(pos)
        level.setBlockAndUpdate(pos, state.setValue(ENABLED, redstoneStrength > 0))
    }

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