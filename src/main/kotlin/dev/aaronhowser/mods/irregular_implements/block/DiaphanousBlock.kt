package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class DiaphanousBlock : Block(
    Properties
        .ofFullCopy(Blocks.STONE)
        .noOcclusion()
        .isViewBlocking(::isUsuallySolid)
        .isSuffocating(::isUsuallySolid)
), EntityBlock {

    companion object {
        fun isValidBlock(block: Block, level: Level): Boolean {
            return block.defaultBlockState().renderShape == RenderShape.MODEL
                    && block !is AirBlock
                    && block.defaultBlockState().isCollisionShapeFullBlock(level, BlockPos.ZERO)    //TODO: Does this crash if (0, 0, 0) is unloaded?
                    && !block.defaultBlockState().`is`(ModBlockTagsProvider.DIAPHANOUS_BLOCK_BLACKLIST)
        }

        fun isUsuallySolid(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
            val blockEntity = blockGetter.getBlockEntity(blockPos)

            return if (blockEntity is DiaphanousBlockEntity) {
                blockEntity.isInverted
            } else {
                false
            }
        }

        private val SHAPE_EMPTY: VoxelShape = Shapes.empty()
        private val SHAPE_FULL: VoxelShape = Shapes.block()
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return DiaphanousBlockEntity(pos, state)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.ENTITYBLOCK_ANIMATED
    }

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return SHAPE_EMPTY
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        if (context !is EntityCollisionContext) return Shapes.empty()
        val player = context.entity as? Player
        val blockEntity = level.getBlockEntity(pos) as? DiaphanousBlockEntity

        if (blockEntity == null || player == null) return Shapes.block()

        val distanceAllowed = 4.5
        val closeEnough = player.eyePosition.closerThan(pos.center, distanceAllowed)

        val canInteract = (closeEnough == blockEntity.isInverted)
                || player.isSecondaryUseActive
                || player.isHolding { it.`is`(ModBlocks.DIAPHANOUS_BLOCK.asItem()) }

        return if (canInteract) {
            SHAPE_FULL
        } else {
            SHAPE_EMPTY
        }
    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (isUsuallySolid(state, level, pos)) SHAPE_FULL else SHAPE_EMPTY
    }

    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos): Float {
        return if (isUsuallySolid(state, level, pos)) 1f else 0f
    }

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return isUsuallySolid(state, level, pos)
    }

}