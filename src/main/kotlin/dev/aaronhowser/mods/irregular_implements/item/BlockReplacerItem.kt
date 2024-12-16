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
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.level.BlockEvent

class BlockReplacerItem : Item(
    Properties()
        .stacksTo(1)
) {

    // TODO: Cooldown based on how the mining time of the block? Or maybe based on the difference in mining time between the block placed and the block broken?
    // TODO: Check for cancellation of events, and if the player is allowed to use the item
    override fun useOn(context: UseOnContext): InteractionResult {

        val level = context.level
        val clickedPos = context.clickedPos
        val clickedState = level.getBlockState(clickedPos)

        if (clickedState.getDestroySpeed(level, clickedPos) == -1f
            || clickedState.`is`(ModBlockTagsProvider.BLOCK_REPLACER_BLACKLIST)
        ) return InteractionResult.PASS

        val usedStack = context.itemInHand

        val player = context.player
        if (player != null && !player.mayUseItemAt(clickedPos, context.clickedFace, usedStack)) return InteractionResult.PASS

        //TODO: Obvious placeholder
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
        val possibleBlocksToPlace = storedBlockStacks.filter {
            val block = (it.item as BlockItem).block

            !clickedState.`is`(block) && block.getStateForPlacement(BlockPlaceContext(context)) != null
        }

        val stackToPlace = possibleBlocksToPlace.randomOrNull() ?: return InteractionResult.PASS

        val stateToPlace = (stackToPlace.item as BlockItem)
            .block
            .getStateForPlacement(BlockPlaceContext(context))
            ?: return InteractionResult.PASS

        if (player != null
            && NeoForge.EVENT_BUS.post(BlockEvent.BreakEvent(level, clickedPos, clickedState, player)).isCanceled
            || !stateToPlace.canSurvive(level, clickedPos)
        ) return InteractionResult.PASS

        stackToPlace.consume(1, player)

        usedStack.set(
            DataComponents.CONTAINER,
            ItemContainerContents.fromItems(storedStacks)
        )

        level.destroyBlock(clickedPos, true)    //TODO: Make it pop out of the clicked face
        level.setBlockAndUpdate(clickedPos, stateToPlace)

        return InteractionResult.SUCCESS
    }

}