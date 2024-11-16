package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.AnalogEmitter.Companion.ENABLED
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RedstoneInterfaceBasicBlockEntity
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty

class RedstoneInterfaceBasicBlock : EntityBlock, Block(
    Properties
        .ofFullCopy(Blocks.IRON_BLOCK)
) {

    companion object {
        val POWER: IntegerProperty = BlockStateProperties.POWER
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(POWER, 0)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(POWER)
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
        return true
    }

    override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        if (!state.getValue(ENABLED)) return 0

        return state.getValue(AnalogEmitter.POWER)
    }

    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return getDirectSignal(state, level, pos, direction)
    }

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