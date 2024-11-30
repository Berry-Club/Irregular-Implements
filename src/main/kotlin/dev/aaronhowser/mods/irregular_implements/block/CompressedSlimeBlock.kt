package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.ItemAbilities
import net.neoforged.neoforge.common.ItemAbility

class CompressedSlimeBlock : Block(Properties.ofFullCopy(Blocks.SLIME_BLOCK)) {

    companion object {

        val SHAPE_0: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        val SHAPE_1: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0)
        val SHAPE_2: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0)

        val COMPRESSION_LEVEL: IntegerProperty = IntegerProperty.create("compression_level", 0, 2)
    }

    init {
        registerDefaultState(
            defaultBlockState()
                .setValue(COMPRESSION_LEVEL, 0)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(COMPRESSION_LEVEL)
    }

    override fun getShape(state: BlockState, world: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return when (state.getValue(COMPRESSION_LEVEL)) {
            0 -> SHAPE_0
            1 -> SHAPE_1
            2 -> SHAPE_2
            else -> SHAPE_0
        }
    }

    override fun getToolModifiedState(state: BlockState, context: UseOnContext, itemAbility: ItemAbility, simulate: Boolean): BlockState? {
        if (itemAbility == ItemAbilities.SHOVEL_FLATTEN) {
            return state.cycle(COMPRESSION_LEVEL)
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate)
    }

    override fun isOcclusionShapeFullBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return false
    }

    //FIXME: Doesn't proc for the smallest one
    override fun updateEntityAfterFallOn(level: BlockGetter, entity: Entity) {
        super.updateEntityAfterFallOn(level, entity)

        if (entity.deltaMovement.y < 1) {
            val compression = entity.blockStateOn.getValue(COMPRESSION_LEVEL)

            entity.setOnGround(false)
            entity.resetFallDistance()
            entity.addDeltaMovement(
                Vec3(
                    0.0,
                    0.8 + 0.4 * compression,
                    0.0
                )
            )
        }
    }

}