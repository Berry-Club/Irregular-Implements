package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class PlatformBlock(
    blockToCopy: Block,
) : Block(
    Properties.ofFullCopy(blockToCopy)
) {

    companion object {
        val SHAPE: VoxelShape = box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0)

        val OAK = PlatformBlock(Blocks.OAK_TRAPDOOR)
        val SPRUCE = PlatformBlock(Blocks.SPRUCE_TRAPDOOR)
        val BIRCH = PlatformBlock(Blocks.BIRCH_TRAPDOOR)
        val JUNGLE = PlatformBlock(Blocks.JUNGLE_TRAPDOOR)
        val ACACIA = PlatformBlock(Blocks.ACACIA_TRAPDOOR)
        val DARK_OAK = PlatformBlock(Blocks.DARK_OAK_TRAPDOOR)
        val CRIMSON = PlatformBlock(Blocks.CRIMSON_TRAPDOOR)
        val WARPED = PlatformBlock(Blocks.WARPED_TRAPDOOR)
        val MANGROVE = PlatformBlock(Blocks.MANGROVE_TRAPDOOR)
        val BAMBOO = PlatformBlock(Blocks.BAMBOO_TRAPDOOR)
        val CHERRY = PlatformBlock(Blocks.CHERRY_TRAPDOOR)
        val SUPER_LUBE = PlatformBlock(ModBlocks.SUPER_LUBRICANT_ICE.get())
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