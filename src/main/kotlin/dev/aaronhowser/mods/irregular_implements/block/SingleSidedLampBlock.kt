package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedstoneLampBlock
import net.minecraft.world.level.block.state.BlockState
import java.util.function.ToIntFunction

class SingleSidedLampBlock private constructor(
    private val onlyOn: Side
) : RedstoneLampBlock(
    Properties
        .ofFullCopy(Blocks.REDSTONE_LAMP)
        .lightLevel(NEVER_LIGHT)
) {

    private enum class Side { CLIENT, SERVER }

    companion object {
        private val NEVER_LIGHT = ToIntFunction { _: BlockState -> 0 }

        val QUARTZ = SingleSidedLampBlock(Side.CLIENT)      // Can see the light but doesn't effect mob spawns
        val LAPIS = SingleSidedLampBlock(Side.SERVER)       // Can't see the light but does effect mob spawns
    }

    override fun getLightEmission(state: BlockState, level: BlockGetter, pos: BlockPos): Int {
        val onServer = level is ServerLevel
        if (onServer && onlyOn == Side.CLIENT) return 0

        return if (state.getValue(LIT)) 15 else 0
    }

    override fun hasDynamicLightEmission(state: BlockState): Boolean {
        return true
    }

}