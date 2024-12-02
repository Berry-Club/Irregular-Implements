package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class LotusBlock(
    properties: Properties =
        Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)
) : BushBlock(properties), BonemealableBlock {

    companion object {
        val AGE: IntegerProperty = BlockStateProperties.AGE_3
        const val MAXIMUM_AGE = 3

        val SAPLING_SHAPE: VoxelShape = box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0)

        val CODEC: MapCodec<LotusBlock> = simpleCodec(::LotusBlock)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(AGE, 0)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun codec(): MapCodec<LotusBlock> {
        return CODEC
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SAPLING_SHAPE
    }

    override fun isValidBonemealTarget(level: LevelReader, pos: BlockPos, state: BlockState): Boolean {
        return state.getValue(AGE) < MAXIMUM_AGE
    }

    override fun isBonemealSuccess(level: Level, random: RandomSource, pos: BlockPos, state: BlockState): Boolean {
        return random.nextInt(5) == 0
    }

    override fun performBonemeal(level: ServerLevel, random: RandomSource, pos: BlockPos, state: BlockState) {
        val newState = state.cycle(AGE)
        level.setBlockAndUpdate(pos, newState)
    }
}