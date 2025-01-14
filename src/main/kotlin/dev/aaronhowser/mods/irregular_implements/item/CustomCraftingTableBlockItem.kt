package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

class CustomCraftingTableBlockItem : ItemNameBlockItem(
    ModBlocks.CUSTOM_CRAFTING_TABLE.get(), Properties()
) {

    companion object {
        fun ofBlock(block: Block): ItemStack {
            val stack = ModItems.CUSTOM_CRAFTING_TABLE_BLOCK.toStack()

            stack.set(ModDataComponents.BLOCK, block)

            return stack
        }
    }

}