package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.PipeBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.IShearable

class SakanadeBlock : Block(
    Properties
        .ofFullCopy(Blocks.MOSS_CARPET)
        .isViewBlocking(Blocks::never)
), IShearable {

    companion object {
        @JvmStatic
        fun addToMushroom(
            level: LevelAccessor,
            origin: BlockPos,
            config: HugeMushroomFeatureConfiguration,
            mutablePos: BlockPos.MutableBlockPos
        ) {
            if (mutablePos.x == origin.x && mutablePos.z == origin.z) return

            if (level.getBlockState(mutablePos.below()).canBeReplaced()) {
                level.setBlock(
                    mutablePos.below(),
                    ModBlocks.SAKANADE.get().defaultBlockState(),
                    1 or 2
                )
            }
        }

        val SHAPE_UP: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0)
        val SHAPE_WEST: VoxelShape = box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0)
        val SHAPE_EAST: VoxelShape = box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        val SHAPE_NORTH: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0)
        val SHAPE_SOUTH: VoxelShape = box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0)
        val SHAPE_DOWN: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)

        val NORTH: BooleanProperty = BlockStateProperties.NORTH
        val EAST: BooleanProperty = BlockStateProperties.EAST
        val SOUTH: BooleanProperty = BlockStateProperties.SOUTH
        val WEST: BooleanProperty = BlockStateProperties.WEST
        val UP: BooleanProperty = BlockStateProperties.UP
        val DOWN: BooleanProperty = BlockStateProperties.DOWN

        val PROPERTY_BY_DIRECTION: Map<Direction, BooleanProperty> = PipeBlock.PROPERTY_BY_DIRECTION
            .filter { it.key != Direction.DOWN }

        private const val SHAPE_OFFSET = 1f

        private lateinit var shapesCache: Map<BlockState, VoxelShape>

        private fun calculateShape(state: BlockState): VoxelShape {
            var voxelShape = Shapes.empty()

            if (state.getValue(UP)) voxelShape = SHAPE_UP
            if (state.getValue(NORTH)) voxelShape = Shapes.or(voxelShape, SHAPE_NORTH)
            if (state.getValue(SOUTH)) voxelShape = Shapes.or(voxelShape, SHAPE_SOUTH)
            if (state.getValue(EAST)) voxelShape = Shapes.or(voxelShape, SHAPE_EAST)
            if (state.getValue(WEST)) voxelShape = Shapes.or(voxelShape, SHAPE_WEST)
            if (state.getValue(DOWN)) voxelShape = Shapes.or(voxelShape, SHAPE_DOWN)

            return if (voxelShape.isEmpty) Shapes.block() else voxelShape
        }
    }

    init {
        this.registerDefaultState(
            this.stateDefinition
                .any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
        )

        shapesCache = stateDefinition.possibleStates.associateWith(::calculateShape)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return shapesCache[state] ?: calculateShape(state)
    }

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return true
    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.empty()
    }

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (entity is LivingEntity) {
            entity.addEffect(MobEffectInstance(ModEffects.COLLAPSE, 20 * 8))
        }
    }

}