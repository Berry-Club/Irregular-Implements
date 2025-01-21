package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ItemCollectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreCoilBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.capabilities.Capabilities

class ItemCollectorBlock(
    private val isAdvanced: Boolean
) : Block(Properties.ofFullCopy(Blocks.HOPPER)), EntityBlock {

    companion object {

        val SHAPE_SOUTH: VoxelShape = box(6.0, 6.0, 16.0 - 5.0, 10.0, 10.0, 16.0)
        val SHAPE_NORTH: VoxelShape = box(6.0, 6.0, 0.0, 10.0, 10.0, 5.0)
        val SHAPE_EAST: VoxelShape = box(16.0 - 5.0, 6.0, 6.0, 16.0, 10.0, 10.0)
        val SHAPE_WEST: VoxelShape = box(0.0, 6.0, 6.0, 5.0, 10.0, 10.0)
        val SHAPE_DOWN: VoxelShape = box(6.0, 0.0, 6.0, 10.0, 5.0, 10.0)
        val SHAPE_UP: VoxelShape = box(6.0, 16.0 - 5.0, 6.0, 10.0, 16.0, 10.0)

        val FACING: DirectionProperty = BlockStateProperties.FACING
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.DOWN)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.clickedFace.opposite)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return when (state.getValue(FACING)) {
            Direction.NORTH -> SHAPE_NORTH
            Direction.SOUTH -> SHAPE_SOUTH
            Direction.WEST -> SHAPE_WEST
            Direction.EAST -> SHAPE_EAST
            Direction.UP -> SHAPE_UP
            Direction.DOWN -> SHAPE_DOWN
            else -> Shapes.block()
        }
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val facing = state.getValue(FACING)
        val onBlockPos = pos.relative(facing)

        val itemHandler = (level as? Level)?.getCapability(Capabilities.ItemHandler.BLOCK, onBlockPos, facing.opposite)

        return itemHandler != null
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return if (this.isAdvanced) {
            null
        } else {
            ItemCollectorBlockEntity(pos, state)
        }
    }

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        return if (this.isAdvanced) {
            null
        } else {
            BaseEntityBlock.createTickerHelper(
                blockEntityType,
                ModBlockEntities.ITEM_COLLECTOR.get(),
                ItemCollectorBlockEntity::tick
            )
        }
    }

}