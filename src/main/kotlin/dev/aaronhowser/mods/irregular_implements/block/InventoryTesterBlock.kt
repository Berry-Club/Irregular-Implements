package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.InventoryTesterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.capabilities.Capabilities

class InventoryTesterBlock : Block(Properties.ofFullCopy(Blocks.IRON_BLOCK)), EntityBlock {

    companion object {
        val SHAPE_SOUTH: VoxelShape = box(6.0, 6.0, 15.0, 10.0, 10.0, 16.0)
        val SHAPE_NORTH: VoxelShape = box(6.0, 6.0, 0.0, 10.0, 10.0, 1.0)
        val SHAPE_EAST: VoxelShape = box(15.0, 6.0, 6.0, 16.0, 10.0, 10.0)
        val SHAPE_WEST: VoxelShape = box(0.0, 6.0, 6.0, 1.0, 10.0, 10.0)
        val SHAPE_DOWN: VoxelShape = box(6.0, 0.0, 6.0, 10.0, 1.0, 10.0)
        val SHAPE_UP: VoxelShape = box(6.0, 15.0, 6.0, 10.0, 16.0, 10.0)

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
        return when (state.getValue(DirectionalBlock.FACING)) {
            Direction.NORTH -> SHAPE_NORTH
            Direction.SOUTH -> SHAPE_SOUTH
            Direction.WEST -> SHAPE_WEST
            Direction.EAST -> SHAPE_EAST
            Direction.UP -> SHAPE_UP
            Direction.DOWN -> SHAPE_DOWN
            else -> Shapes.block()
        }
    }

    override fun updateShape(state: BlockState, direction: Direction, neighborState: BlockState, level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos): BlockState {
        return if (state.canSurvive(level, pos)) {
            state
        } else {
            Blocks.AIR.defaultBlockState()
        }
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val facing = state.getValue(DirectionalBlock.FACING)
        val onBlockPos = pos.relative(facing)

        val itemHandler = (level as? Level)?.getCapability(Capabilities.ItemHandler.BLOCK, onBlockPos, facing.opposite)

        return itemHandler != null
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return InventoryTesterBlockEntity(pos, state)
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        val blockEntity = level.getBlockEntity(pos)

        if (blockEntity is InventoryTesterBlockEntity) {
            player.openMenu(blockEntity)
            return InteractionResult.SUCCESS
        }

        return InteractionResult.PASS
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
        return true
    }

    override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        val blockEntity = level.getBlockEntity(pos)

        if (blockEntity is InventoryTesterBlockEntity) {
            return if (blockEntity.isEmittingRedstone) 15 else 0
        }

        return 0
    }

    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return getDirectSignal(state, level, pos, direction)
    }

}