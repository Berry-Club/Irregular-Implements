package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class DiaphanousBlockItem : ItemNameBlockItem(
    ModBlocks.DIAPHANOUS_BLOCK.get(), Properties()
) {

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {

        val block = stack.get(ModDataComponents.BLOCK)
        if (block != null) {
            tooltipComponents.add(block.name)
        }

        val isInverted = stack.has(ModDataComponents.IS_INVERTED)
        if (isInverted) {
            tooltipComponents.add(Component.literal("Inverted"))
        }

    }

}