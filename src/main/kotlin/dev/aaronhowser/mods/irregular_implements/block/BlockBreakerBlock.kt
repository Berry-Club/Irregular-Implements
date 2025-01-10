package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockBreakerBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
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
                .setValue(IS_UPGRADED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, IS_UPGRADED)
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
        blockEntity.neighborChanged(state, level)
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

        if (!stack.`is`(ModItems.DIAMOND_BREAKER) || state.getValue(IS_UPGRADED)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

        blockEntity.upgrade(stack.copyWithCount(1))

        val newState = state.setValue(IS_UPGRADED, true)
        level.setBlockAndUpdate(pos, newState)

        stack.consume(1, player)
        return ItemInteractionResult.SUCCESS
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        if (level.isClientSide
            || !player.isSecondaryUseActive
            || !state.getValue(IS_UPGRADED)
        ) return InteractionResult.PASS

        val blockEntity = level.getBlockEntity(pos) as? BlockBreakerBlockEntity ?: return InteractionResult.FAIL
        blockEntity.downgrade(player)

        val newState = state.setValue(IS_UPGRADED, false)
        level.setBlockAndUpdate(pos, newState)

        return InteractionResult.SUCCESS
    }

    override fun onRemove(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, movedByPiston: Boolean) {
        if (oldState.`is`(newState.block)) return

        val blockEntity = level.getBlockEntity(pos) as? BlockBreakerBlockEntity
        if (blockEntity != null) {
            OtherUtil.dropStackAt(blockEntity.diamondBreaker.copy(), level, pos.center, instantPickup = false)
        }

        super.onRemove(oldState, level, pos, newState, movedByPiston)
    }

}