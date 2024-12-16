package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import net.minecraft.core.component.DataComponents
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks

class BlockReplacerItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {

        val level = context.level
        val clickedPos = context.clickedPos
        val clickedState = level.getBlockState(clickedPos)

        if (clickedState.`is`(ModBlockTagsProvider.BLOCK_REPLACER_BLACKLIST)) return InteractionResult.PASS

        val usedStack = context.itemInHand

        val component = usedStack.get(DataComponents.CONTAINER) ?: ItemContainerContents.fromItems(
            listOf(
                Blocks.STONE.asItem().defaultInstance.copyWithCount(32),
                Blocks.GRASS_BLOCK.asItem().defaultInstance.copyWithCount(32),
                Items.DIAMOND_PICKAXE.defaultInstance
            )
        )

        val storedStacks = component.nonEmptyItems().toList()

        if (storedStacks.isEmpty()) return InteractionResult.PASS

        val storedBlockStacks = storedStacks.filter { it.item is BlockItem }
        val possibleBlocksToPlace = storedBlockStacks.filterNot { clickedState.`is`((it.item as BlockItem).block) }

        val stackToPlace = possibleBlocksToPlace.randomOrNull() ?: return InteractionResult.PASS

        val stateToPlace = (stackToPlace.item as BlockItem)
            .block
            .getStateForPlacement(BlockPlaceContext(context))
            ?: return InteractionResult.PASS

        stackToPlace.consume(1, context.player)

        usedStack.set(
            DataComponents.CONTAINER,
            ItemContainerContents.fromItems(storedStacks)
        )

        level.destroyBlock(clickedPos, true)    //TODO: Make it pop out of the clicked face
        level.setBlockAndUpdate(clickedPos, stateToPlace)

        return InteractionResult.SUCCESS
    }

}