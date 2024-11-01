package dev.aaronhowser.mods.irregular_implements.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult

class AnalogEmitter(
    properties: Properties = Properties.ofFullCopy(Blocks.TARGET)
) : DirectionalBlock(properties) {

    companion object {
        private val CODEC = simpleCodec(::AnalogEmitter)
        val ENABLED: BooleanProperty = BlockStateProperties.ENABLED
        val POWER: IntegerProperty = BlockStateProperties.POWER
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ENABLED, false)
                .setValue(POWER, 0)
        )
    }

    override fun codec(): MapCodec<AnalogEmitter> = CODEC

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, ENABLED, POWER)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState()
            .setValue(FACING, context.nearestLookingDirection.opposite)
            .setValue(ENABLED, false)
            .setValue(POWER, 0)
    }

    override fun useWithoutItem(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hitResult: BlockHitResult
    ): InteractionResult {
        if (level.isClientSide) return InteractionResult.SUCCESS

        println("Power was ${state.getValue(POWER)}")
        state.cycle(POWER)
        level.setBlock(pos, state, 3)
        level.updateNeighborsAt(pos, this)
        val newPower = state.getValue(POWER)
        println("Power is now $newPower")

        val pitch = 0.5f + (newPower.toFloat() / 15f) * (2f - 0.5f)   // 0.5 to 5
        level.playSound(
            null,
            pos,
            SoundEvents.UI_BUTTON_CLICK.value(),
            SoundSource.BLOCKS,
            1f,
            pitch
        )

        return InteractionResult.SUCCESS
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?): Boolean {
        return true
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        block: Block,
        fromPos: BlockPos,
        isMoving: Boolean
    ) {
        if (level.isClientSide) return

        val facing = state.getValue(FACING)
        val facingSideIsPowered = level.hasSignal(pos, facing)

        state.setValue(ENABLED, facingSideIsPowered)
    }

    override fun isSignalSource(state: BlockState): Boolean {
        return true
    }

    override fun getDirectSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        val enabled = state.getValue(ENABLED)
        if (!enabled) return 0

        val facing = state.getValue(FACING)
        if (facing == direction.opposite) return 16
        return 0
    }

    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return getDirectSignal(state, level, pos, direction)
    }

}