package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.block.block_entity.BlockDestabilizerBlockEntity
import dev.aaronhowser.mods.irregular_implements.menu.BlockDestabilizerScreen
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult

class BlockDestabilizerBlock(
    properties: Properties = Properties
        .ofFullCopy(Blocks.DISPENSER)
) : EntityBlock, DirectionalBlock(properties) {

    companion object {
        val CODEC: MapCodec<BlockDestabilizerBlock> = simpleCodec(::BlockDestabilizerBlock)

        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ENABLED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, ENABLED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.nearestLookingDirection.opposite)
    }

    override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighborBlock: Block, neighborPos: BlockPos, movedByPiston: Boolean) {
        val powered = level.hasNeighborSignal(pos)
        val currentlyEnabled = state.getValue(ENABLED)

        if (powered != currentlyEnabled) {
            val newState = state.setValue(ENABLED, powered)
            level.setBlockAndUpdate(pos, newState)

            if (powered) {
                val blockEntity = level.getBlockEntity(pos) as? BlockDestabilizerBlockEntity ?: return

                if (blockEntity.state == BlockDestabilizerBlockEntity.State.IDLE) {
                    blockEntity.initStart()
                }
            }
        }
    }

    override fun useWithoutItem(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pPlayer: Player,
        pHitResult: BlockHitResult
    ): InteractionResult {
        val blockEntity = pLevel.getBlockEntity(pPos) as? BlockDestabilizerBlockEntity ?: return InteractionResult.FAIL

        if (pLevel.isClientSide) {
            val screen = BlockDestabilizerScreen(blockEntity)
            Minecraft.getInstance().setScreen(screen)
        } else {
//            ModPacketHandler.messagePlayer(
//                pPlayer as ServerPlayer,
//                TellClientChatDetectorChanged(
//                    blockEntity.stopsMessage,
//                    blockEntity.regexString
//                )
//            )
        }

        return InteractionResult.SUCCESS
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BlockDestabilizerBlockEntity(pos, state)
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return BaseEntityBlock.createTickerHelper(
            blockEntityType,
            ModBlockEntities.BLOCK_DESTABILIZER.get(),
            BlockDestabilizerBlockEntity::tick
        )
    }

    override fun codec(): MapCodec<BlockDestabilizerBlock> {
        return CODEC
    }
}