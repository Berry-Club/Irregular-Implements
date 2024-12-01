package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IgniterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition

class IgniterBlock(
    properties: Properties = Properties.ofFullCopy(Blocks.DISPENSER)
) : EntityBlock, DirectionalBlock(properties) {

    companion object {
        val CODEC: MapCodec<IgniterBlock> = simpleCodec(::IgniterBlock)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        )
    }

    override fun codec(): MapCodec<IgniterBlock> {
        return CODEC
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.nearestLookingDirection.opposite)
    }

    override fun isFireSource(state: BlockState, level: LevelReader, pos: BlockPos, direction: Direction): Boolean {
        return direction == state.getValue(FACING)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return IgniterBlockEntity(pos, state)
    }
}