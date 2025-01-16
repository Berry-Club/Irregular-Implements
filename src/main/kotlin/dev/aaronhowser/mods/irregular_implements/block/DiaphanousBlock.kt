package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.minecraft.world.item.BlockItem
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

class DiaphanousBlock : Block(Properties.ofFullCopy(Blocks.STONE)), EntityBlock {

    companion object {
        fun isValidBlock(block: Block, level: Level): Boolean {
            return block.defaultBlockState().renderShape == RenderShape.MODEL
                    && block.defaultBlockState().getCollisionShape(level, BlockPos.ZERO) != Shapes.empty()    //TODO: Does this crash if (0, 0, 0) is unloaded?
                    && !block.defaultBlockState().`is`(ModBlockTagsProvider.DIAPHANOUS_BLOCK_BLACKLIST)
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

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        val blockEntity = level.getBlockEntity(pos) as? DiaphanousBlockEntity ?: return Shapes.block()

        return if (blockEntity.isInverted) {
            blockEntity.renderedBlockState.getCollisionShape(level, pos, context)
        } else {
            Shapes.empty()
        }
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        if (level is Level && level.isClientSide) {
            val blockEntity = level.getBlockEntity(pos) as? DiaphanousBlockEntity
            val player = ClientUtil.localPlayer

            if (blockEntity != null && player != null) {
                if (blockEntity.isInverted) return Shapes.block()

                return if (player.isSecondaryUseActive || player.isHolding { it.item is BlockItem && (it.item as BlockItem).block == ModBlocks.DIAPHANOUS_BLOCK.get() }) {
                    blockEntity.renderedBlockState.getShape(level, pos, context)
                } else {
                    Shapes.empty()
                }
            }

        }

        return Shapes.block()
    }

}