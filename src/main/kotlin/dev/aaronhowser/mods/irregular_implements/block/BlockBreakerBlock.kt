package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockBreakerBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class BlockBreakerBlock : Block(
    Properties.ofFullCopy(Blocks.DISPENSER)
), EntityBlock {

    companion object {
        val FACING: DirectionProperty = BlockStateProperties.FACING
        val IS_UPGRADED: BooleanProperty = BooleanProperty.create("is_upgraded")
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.nearestLookingDirection.opposite)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BlockBreakerBlockEntity(pos, state)
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return BaseEntityBlock.createTickerHelper(
            blockEntityType,
            ModBlockEntities.BLOCK_BREAKER.get(),
            BlockBreakerBlockEntity::tick
        )
    }

    override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston)

        val blockEntity = level.getBlockEntity(pos) as? BlockBreakerBlockEntity ?: return
        blockEntity.neighborChanged(state, level, pos)
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
        if (level.isClientSide) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        val blockEntity = level.getBlockEntity(pos) as? BlockBreakerBlockEntity ?: return ItemInteractionResult.FAIL

        if (stack.`is`(ModItems.DIAMOND_BREAKER) && !state.getValue(IS_UPGRADED)) {
            blockEntity.upgrade()
            val newState = state.setValue(IS_UPGRADED, true)
            level.setBlockAndUpdate(pos, newState)

            stack.shrink(1)
            return ItemInteractionResult.SUCCESS
        }

        if (state.getValue(IS_UPGRADED) && player.isSecondaryUseActive && stack.isEmpty) {
            blockEntity.downgrade()
            val newState = state.setValue(IS_UPGRADED, false)
            level.setBlockAndUpdate(pos, newState)

            return ItemInteractionResult.SUCCESS
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
    }

}