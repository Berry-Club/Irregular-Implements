package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
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

    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return DiaphanousBlockEntity(pos, state)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.ENTITYBLOCK_ANIMATED
    }

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return Shapes.empty()
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        if (level is Level && level.isClientSide) {
            val blockEntity = level.getBlockEntity(pos) as? DiaphanousBlockEntity
            val player = ClientUtil.localPlayer

            if (blockEntity != null && player != null) {

                val distanceAllowed = 4.5
                val closeEnough = player.eyePosition.closerThan(pos.center, distanceAllowed)

                val canInteract = when (blockEntity.isInverted) {
                    true -> closeEnough

                    false -> !closeEnough
                            || player.isSecondaryUseActive
                            || player.isHolding { it.`is`(ModBlocks.DIAPHANOUS_BLOCK.asItem()) }
                }

                return if (canInteract) {
                    Shapes.block()
                } else {
                    Shapes.empty()
                }
            }

        }

        return Shapes.block()
    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (isUsuallySolid(state, level, pos)) Shapes.block() else Shapes.empty()
    }

    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos): Float {
        return if (isUsuallySolid(state, level, pos)) 1f else 0f
    }

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return isUsuallySolid(state, level, pos)
    }

}