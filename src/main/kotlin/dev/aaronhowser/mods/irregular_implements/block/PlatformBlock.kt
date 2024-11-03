package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class PlatformBlock(
    val blockSetType: BlockSetType?,
) : Block(
    Properties
        .ofFullCopy(
            when (blockSetType) {
                BlockSetType.OAK -> Blocks.OAK_TRAPDOOR
                BlockSetType.SPRUCE -> Blocks.SPRUCE_TRAPDOOR
                BlockSetType.BIRCH -> Blocks.BIRCH_TRAPDOOR
                BlockSetType.JUNGLE -> Blocks.JUNGLE_TRAPDOOR
                BlockSetType.ACACIA -> Blocks.ACACIA_TRAPDOOR
                BlockSetType.DARK_OAK -> Blocks.DARK_OAK_TRAPDOOR
                BlockSetType.CRIMSON -> Blocks.CRIMSON_TRAPDOOR
                BlockSetType.WARPED -> Blocks.WARPED_TRAPDOOR
                BlockSetType.MANGROVE -> Blocks.MANGROVE_TRAPDOOR
                BlockSetType.BAMBOO -> Blocks.BAMBOO_TRAPDOOR
                BlockSetType.CHERRY -> Blocks.CHERRY_TRAPDOOR
                null -> Blocks.BLUE_ICE
                else -> error("Unknown block set type: $blockSetType")
            }
        )
) {

    companion object {
        val SHAPE: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0)
    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (context.isAbove(Shapes.block(), pos, true) && !(context as? EntityCollisionContext)?.entity?.isDescending.isTrue) {
            SHAPE
        } else {
            Shapes.empty()
        }
    }

    override fun getInteractionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return SHAPE
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

}