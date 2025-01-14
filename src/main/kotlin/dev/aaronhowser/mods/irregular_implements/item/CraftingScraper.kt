package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class CraftingScraper : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos

        val clickedBlockEntity = level.getBlockEntity(clickedPos)
        if (clickedBlockEntity != null) return InteractionResult.FAIL

        val clickedState = level.getBlockState(clickedPos)
        if (clickedState.isAir) return InteractionResult.FAIL

        level.setBlockAndUpdate(
            clickedPos,
            ModBlocks.CUSTOM_CRAFTING_TABLE.get().defaultBlockState()
        )

        val blockEntity = level.getBlockEntity(clickedPos)
        if (blockEntity is CustomCraftingTableBlockEntity) {
            blockEntity.renderedBlockState = clickedState
        }

        return InteractionResult.SUCCESS
    }

}