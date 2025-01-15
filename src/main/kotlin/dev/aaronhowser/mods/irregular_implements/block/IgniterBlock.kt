package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.block.block_entity.IgniterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult
import java.util.*

class IgniterBlock(
    properties: Properties = Properties.ofFullCopy(Blocks.DISPENSER)
) : DirectionalBlock(properties), EntityBlock {

    companion object {
        val CODEC: MapCodec<IgniterBlock> = simpleCodec(::IgniterBlock)

        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED

        fun ignite(level: Level, igniterPos: BlockPos, igniterState: BlockState) {
            if (level.isClientSide) return

            val facing = igniterState.getValue(FACING)
            val targetPos = igniterPos.relative(facing)
            val targetState = level.getBlockState(targetPos)

            val canPlaceFire = targetState.canBeReplaced()
            if (canPlaceFire) {
                val fireState = (Blocks.FIRE as FireBlock).getStateForPlacement(level, targetPos)

                level.setBlockAndUpdate(targetPos, fireState)
            }
        }

        fun extinguish(level: Level, igniterPos: BlockPos, igniterState: BlockState) {
            if (level.isClientSide) return

            val facing = igniterState.getValue(FACING)
            val targetPos = igniterPos.relative(facing)
            val targetState = level.getBlockState(targetPos)

            if (targetState.`is`(BlockTags.FIRE)) {
                level.removeBlock(targetPos, false)
            }
        }
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ENABLED, false)
        )
    }

    override fun codec(): MapCodec<IgniterBlock> {
        return CODEC
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, ENABLED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.nearestLookingDirection.opposite)
    }

    override fun isFireSource(state: BlockState, level: LevelReader, pos: BlockPos, direction: Direction): Boolean {
        return direction == state.getValue(FACING)
    }

    override fun isFlammable(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Boolean {
        return direction == state.getValue(FACING)
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        neighborBlock: Block,
        neighborPos: BlockPos,
        movedByPiston: Boolean
    ) {
        if (level.isClientSide) return

        val isPowered = level.hasNeighborSignal(pos)
        val wasEnabled = state.getValue(ENABLED)

        val isTurningOn = isPowered && !wasEnabled
        val isTurningOff = !isPowered && wasEnabled

        if (isPowered != wasEnabled) {
            val newState = state.setValue(ENABLED, isPowered)
            level.setBlockAndUpdate(pos, newState)
        }

//        when (state.getValue(IGNITER_MODE)) {
//            Mode.KEEP_IGNITED -> if (isPowered) ignite(level, pos, state)
//
//            Mode.IGNITE -> if (isTurningOn) ignite(level, pos, state)
//
//            Mode.TOGGLE -> if (isTurningOn) ignite(level, pos, state) else if (isTurningOff) extinguish(level, pos, state)
//
//            null -> state.setValue(IGNITER_MODE, Mode.TOGGLE)
//        }
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        if (level.isClientSide) return InteractionResult.SUCCESS

//        val nextMode = state.cycle(IGNITER_MODE)
//        level.setBlockAndUpdate(pos, nextMode)
//
//        val modeName = nextMode.getValue(IGNITER_MODE).serializedName
//        player.sendSystemMessage(modeName.toComponent())

        return InteractionResult.SUCCESS
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return IgniterBlockEntity(pos, state)
    }

}