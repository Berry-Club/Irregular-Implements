package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.CommonHooks

class BeanSproutBlock(
    properties: Properties = Properties
        .ofFullCopy(Blocks.ROSE_BUSH)
        .randomTicks()
        .dynamicShape()
) : BushBlock(properties) {

    companion object {
        val CODEC: MapCodec<BeanSproutBlock> = simpleCodec(::BeanSproutBlock)

        val SHAPE_SMALL: VoxelShape = box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0)
        val SHAPE_BIG: VoxelShape = box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0)

        val AGE: IntegerProperty = BlockStateProperties.AGE_7
        const val MAXIMUM_AGE = 7
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

    override fun codec(): MapCodec<BeanSproutBlock> {
        return CODEC
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (state.getValue(AGE) == MAXIMUM_AGE) SHAPE_BIG else SHAPE_SMALL
    }

    override fun randomTick(oldState: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (
            oldState.getValue(AGE) >= MAXIMUM_AGE                                                          // If it's already fully grown
            || level.getRawBrightness(pos.above(), 0) < 9                                           // If it's not bright enough
            || !CommonHooks.canCropGrow(level, pos, oldState, random.nextInt(2) == 0)           // If it can't grow
        ) return

        val newState = oldState.cycle(AGE)
        level.setBlockAndUpdate(pos, newState)

        CommonHooks.fireCropGrowPost(level, pos, oldState)
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(oldState))
    }

}