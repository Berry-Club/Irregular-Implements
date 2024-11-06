package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty

class RainbowLampBlock : Block(
    Properties
        .ofFullCopy(Blocks.REDSTONE_LAMP)
        .lightLevel { 15 }
) {

    companion object {
        val COLOR: IntegerProperty = IntegerProperty.create("color", 0, 15)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(COLOR, 0)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(COLOR)
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
        if (redstoneStrength in COLOR.possibleValues) {
            level.setBlockAndUpdate(pos, state.setValue(COLOR, redstoneStrength))
        }
    }

}