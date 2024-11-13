package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class DiaphanousBlock : EntityBlock, Block(
    Properties
        .ofFullCopy(Blocks.STONE)
        .noOcclusion()
        .isRedstoneConductor(Blocks::never)
        .isViewBlocking(::shouldViewBlock)
        .isSuffocating(::shouldSuffocate)
) {

    companion object {
        val NOT_SOLID: BooleanProperty = BooleanProperty.create("not_solid")

        private fun shouldViewBlock(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
            if (blockState.getValue(NOT_SOLID)) return false

            val blockEntity = blockGetter.getBlockEntity(blockPos) as? DiaphanousBlockEntity ?: return false
            if (blockEntity.alpha < 1f) return false
            val blockItem = blockEntity.blockToRender.item as? BlockItem ?: return false

            return blockItem.block.defaultBlockState().isViewBlocking(blockGetter, blockPos)
        }

        private fun shouldSuffocate(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
            if (blockState.getValue(NOT_SOLID)) return false

            val blockEntity = blockGetter.getBlockEntity(blockPos) as? DiaphanousBlockEntity ?: return false
            if (blockEntity.alpha < 1f) return false
            val blockItem = blockEntity.blockToRender.item as? BlockItem ?: return false
            return blockItem.block.defaultBlockState().isSuffocating(blockGetter, blockPos)
        }

    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(NOT_SOLID, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(NOT_SOLID)
    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (state.getValue(NOT_SOLID)) {
            Shapes.empty()
        } else {
            Shapes.block()
        }
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = DiaphanousBlockEntity(pos, state)

    override fun <T : BlockEntity?> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        if (blockEntityType != ModBlockEntities.DIAPHANOUS_BLOCK.get()) return null

        return BlockEntityTicker { tLevel, tPos, tState, _ ->
            DiaphanousBlockEntity.tick(tLevel, tPos, tState)
        }
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): ItemInteractionResult {
        if (stack.item !is BlockItem) return ItemInteractionResult.FAIL

        val blockEntity = level.getBlockEntity(pos) as? DiaphanousBlockEntity ?: return ItemInteractionResult.FAIL
        blockEntity.blockToRender = stack.copy()

        return ItemInteractionResult.SUCCESS
    }

}