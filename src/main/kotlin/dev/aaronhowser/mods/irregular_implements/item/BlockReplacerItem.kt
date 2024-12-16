package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks

class BlockReplacerItem : Item(
    Properties()
        .stacksTo(1)
        .component(ModDataComponents.STACK_LIST, emptyList())
) {

    override fun useOn(context: UseOnContext): InteractionResult {

        val level = context.level
        val clickedPos = context.clickedPos
        val clickedState = level.getBlockState(clickedPos)

        if (clickedState.`is`(ModBlockTagsProvider.BLOCK_REPLACER_BLACKLIST)) return InteractionResult.PASS

//        val storedStacks = context.itemInHand.get(ModDataComponents.STACK_LIST)
//            ?.filter { it.item is BlockItem }
//            ?: return InteractionResult.PASS

        val storedStacks = listOf(Blocks.STONE.asItem().defaultInstance)

        if (storedStacks.isEmpty()) return InteractionResult.PASS

        val possibleBlocksToPlace = storedStacks.filterNot { clickedState.`is`((it.item as BlockItem).block) }

        val stackToPlace = possibleBlocksToPlace.randomOrNull() ?: return InteractionResult.PASS

        val stateToPlace = (stackToPlace.item as BlockItem)
            .block
            .getStateForPlacement(BlockPlaceContext(context))
            ?: return InteractionResult.PASS

        level.destroyBlock(clickedPos, true)    //TODO: Make it pop out of the clicked face
        level.setBlockAndUpdate(clickedPos, stateToPlace)

        return InteractionResult.SUCCESS
    }

}