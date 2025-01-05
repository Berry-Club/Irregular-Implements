package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor
import net.neoforged.neoforge.common.ItemAbilities
import net.neoforged.neoforge.common.ItemAbility

class SpectreLogBlock : RotatedPillarBlock(
    Properties
        .ofFullCopy(Blocks.OAK_LOG)
        .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
) {

    //TODO: Emi
    override fun getToolModifiedState(state: BlockState, context: UseOnContext, itemAbility: ItemAbility, simulate: Boolean): BlockState? {
        if (itemAbility != ItemAbilities.AXE_STRIP) return super.getToolModifiedState(state, context, itemAbility, simulate)

        return ModBlocks.STRIPPED_SPECTRE_LOG.get()
            .defaultBlockState()
            .setValue(AXIS, state.getValue(AXIS))
    }

}