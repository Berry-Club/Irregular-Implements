package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpectreCoilBlockEntity
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
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
import java.awt.Color
import java.util.function.Supplier

class SpectreCoilBlock private constructor(
    val type: Type
) : Block(
    Properties
        .ofFullCopy(Blocks.IRON_BLOCK)
), EntityBlock {

    companion object {
        val FACING: DirectionProperty = BlockStateProperties.FACING

        private const val HEIGHT = 1.5

        var SHAPE_NORTH: VoxelShape = box(5.0, 5.0, 0.0, 11.0, 11.0, HEIGHT)
        var SHAPE_SOUTH: VoxelShape = box(5.0, 5.0, 16.0 - HEIGHT, 11.0, 11.0, 16.0)
        var SHAPE_WEST: VoxelShape = box(0.0, 5.0, 5.0, HEIGHT, 11.0, 11.0)
        var SHAPE_EAST: VoxelShape = box(16.0 - HEIGHT, 5.0, 5.0, 16.0, 11.0, 11.0)
        var SHAPE_UP: VoxelShape = box(5.0, 16.0 - HEIGHT, 5.0, 11.0, 16.0, 11.0)
        var SHAPE_DOWN: VoxelShape = box(5.0, 0.0, 5.0, 11.0, HEIGHT, 11.0)

        val BASIC = SpectreCoilBlock(Type.BASIC)
        val REDSTONE = SpectreCoilBlock(Type.REDSTONE)
        val ENDER = SpectreCoilBlock(Type.ENDER)
        val NUMBER = SpectreCoilBlock(Type.NUMBER)
        val GENESIS = SpectreCoilBlock(Type.GENESIS)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.DOWN)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(DirectionalBlock.FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(DirectionalBlock.FACING, context.clickedFace.opposite)
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

        return level.getBlockState(onBlockPos).isFaceSturdy(level, onBlockPos, facing.opposite, SupportType.CENTER)
    }

    enum class Type(
        val color: Int,
        val amountGetter: Supplier<Int>,
        val isGenerator: Boolean
    ) {
        BASIC(
            color = Color.CYAN.rgb,
            amountGetter = { ServerConfig.SPECTRE_BASIC_RATE.get() },
            isGenerator = false
        ),
        REDSTONE(
            color = Color.RED.rgb,
            amountGetter = { ServerConfig.SPECTRE_REDSTONE_RATE.get() },
            isGenerator = false
        ),
        ENDER(
            color = Color(200, 0, 210).rgb,
            amountGetter = { ServerConfig.SPECTRE_ENDER_RATE.get() },
            isGenerator = false
        ),
        NUMBER(
            color = Color.GREEN.rgb,
            amountGetter = { ServerConfig.SPECTRE_NUMBER_RATE.get() },
            isGenerator = true
        ),
        GENESIS(
            color = Color.ORANGE.rgb,
            amountGetter = { ServerConfig.SPECTRE_GENESIS_RATE.get() },
            isGenerator = true
        ),
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return SpectreCoilBlockEntity(pos, state, this.type)
    }

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        return BaseEntityBlock.createTickerHelper(
            blockEntityType,
            ModBlockEntities.SPECTRE_COIL.get(),
            SpectreCoilBlockEntity::tick
        )
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        if (placer == null) return
        val blockEntity = level.getBlockEntity(pos) as? SpectreCoilBlockEntity ?: return

        blockEntity.ownerUuid = placer.uuid ?: return
    }

}