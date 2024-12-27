package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class DiaphanousBlock : Block(Properties.ofFullCopy(Blocks.STONE)), EntityBlock {

    companion object {
        fun isValidBlock(block: Block): Boolean {
            return block.defaultBlockState().renderShape == RenderShape.MODEL
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

        return if (blockEntity.isInverted) Shapes.block() else Shapes.empty()
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        if (level is Level && level.isClientSide) {
            val blockEntity = level.getBlockEntity(pos) as? DiaphanousBlockEntity
            val player = ClientUtil.localPlayer

            if (blockEntity != null && player != null) {
                if (blockEntity.isInverted) return Shapes.block()

                return if (player.isHolding { it.item is BlockItem && (it.item as BlockItem).block == ModBlocks.DIAPHANOUS_BLOCK.get() }) {
                    Shapes.block()
                } else {
                    Shapes.empty()
                }
            }

        }

        return Shapes.block()
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        val blockToRender = stack.get(ModDataComponents.BLOCK)
        val isInverted = stack.has(ModDataComponents.IS_INVERTED)
        if (blockToRender != null) {
            val blockEntity = level.getBlockEntity(pos) as? DiaphanousBlockEntity

            blockEntity?.renderedBlock = blockToRender
            blockEntity?.isInverted = isInverted
        }
    }

}