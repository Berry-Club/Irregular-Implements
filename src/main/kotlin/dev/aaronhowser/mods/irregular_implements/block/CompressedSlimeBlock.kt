package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.ItemAbilities
import net.neoforged.neoforge.common.ItemAbility
import net.neoforged.neoforge.event.level.BlockEvent

class CompressedSlimeBlock : Block(Properties.ofFullCopy(Blocks.SLIME_BLOCK)) {

    companion object {

        val SHAPE_0: VoxelShape = box(0.01, 0.0, 0.01, 15.99, 8.0, 15.99)
        val SHAPE_1: VoxelShape = box(0.01, 0.0, 0.01, 15.99, 4.0, 15.99)
        val SHAPE_2: VoxelShape = box(0.01, 0.0, 0.01, 15.99, 2.0, 15.99)

        val COMPRESSION_LEVEL: IntegerProperty = IntegerProperty.create("compression_level", 0, 2)

        fun modifySlimeBlock(event: BlockEvent.BlockToolModificationEvent) {
            val ability = event.itemAbility
            if (ability != ItemAbilities.SHOVEL_FLATTEN) return

            val clickedState = event.state
            if (clickedState.block != Blocks.SLIME_BLOCK) return

            event.finalState = ModBlocks.COMPRESSED_SLIME_BLOCK.get().defaultBlockState()
        }
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

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        val shape = getShape(state, level, pos, context)

        return if (context.isAbove(shape, pos, true)) shape else Shapes.empty()
    }

    override fun getToolModifiedState(state: BlockState, context: UseOnContext, itemAbility: ItemAbility, simulate: Boolean): BlockState? {
        if (itemAbility != ItemAbilities.SHOVEL_FLATTEN) return super.getToolModifiedState(state, context, itemAbility, simulate)

        return when (state.getValue(COMPRESSION_LEVEL)) {
            0 -> state.setValue(COMPRESSION_LEVEL, 1)
            1 -> state.setValue(COMPRESSION_LEVEL, 2)
            2 -> Blocks.SLIME_BLOCK.defaultBlockState()
            else -> state
        }
    }

    override fun isOcclusionShapeFullBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return false
    }

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        val compression = state.getValue(COMPRESSION_LEVEL)

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