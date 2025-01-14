package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block

class CustomCraftingTableBlockItem : ItemNameBlockItem(
    ModBlocks.CUSTOM_CRAFTING_TABLE.get(), Properties()
) {

    companion object {
        fun ofBlock(block: Block): ItemStack {
            val stack = ModItems.CUSTOM_CRAFTING_TABLE.toStack()

            stack.set(ModDataComponents.BLOCK, block)

            return stack
        }
    }

    override fun place(context: BlockPlaceContext): InteractionResult {
        val result = super.place(context)

        if (!result.consumesAction()) {
            return result
        }

        val blockToRender = context.itemInHand.get(ModDataComponents.BLOCK)

        if (blockToRender != null) {
            val stateToRender = getPlacementState(context)

            if (stateToRender != null) {
                val blockEntity = context.level.getBlockEntity(context.clickedPos)

                if (blockEntity is CustomCraftingTableBlockEntity) {
                    blockEntity.renderedBlockState = stateToRender
                }
            }
        }

        return result
    }

}