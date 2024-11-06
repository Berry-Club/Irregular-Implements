package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FlowerBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.capability.IFluidHandler

class PitcherPlantBlock : FlowerBlock(
    MobEffects.WATER_BREATHING,
    5f,
    Properties
        .ofFullCopy(Blocks.POPPY)
) {

    override fun isRandomlyTicking(state: BlockState): Boolean = true

    override fun randomTick(
        state: BlockState,
        level: ServerLevel,
        pos: BlockPos,
        random: RandomSource
    ) {
        for (direction in Direction.entries) {
            val offsetPos = pos.relative(direction)

            val fluidCap = level.getCapability(
                Capabilities.FluidHandler.BLOCK,
                offsetPos,
                direction.opposite
            ) ?: continue

            val amountThatFits = fluidCap.fill(FluidStack(Fluids.WATER, 1000 * 100), IFluidHandler.FluidAction.SIMULATE)
            fluidCap.fill(FluidStack(Fluids.WATER, amountThatFits), IFluidHandler.FluidAction.EXECUTE)
        }
    }

}