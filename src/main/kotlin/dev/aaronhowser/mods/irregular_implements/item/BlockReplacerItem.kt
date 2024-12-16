package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.component.DataComponents
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.level.BlockEvent

class BlockReplacerItem : Item(
    Properties()
        .stacksTo(1)
) {

    // TODO: Cooldown based on how the mining time of the block? Or maybe based on the difference in mining time between the block placed and the block broken?
    // TODO: Maybe add a mining level? Maybe like it uses the highest tier tool stored or something
    override fun useOn(context: UseOnContext): InteractionResult {

        val level = context.level as? ServerLevel ?: return InteractionResult.PASS
        val player = context.player ?: return InteractionResult.PASS
        val usedStack = context.itemInHand
        val clickedPos = context.clickedPos
        val clickedState = level.getBlockState(clickedPos)

        if (clickedState.getDestroySpeed(level, clickedPos) == -1f
            || clickedState.`is`(ModBlockTagsProvider.BLOCK_REPLACER_BLACKLIST)
        ) return InteractionResult.PASS

        if (!player.mayUseItemAt(clickedPos, context.clickedFace, usedStack)) return InteractionResult.PASS

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

        if (!stateToPlace.canSurvive(level, clickedPos)
            && NeoForge.EVENT_BUS.post(BlockEvent.BreakEvent(level, clickedPos, clickedState, player)).isCanceled
        ) return InteractionResult.PASS

        val drops = Block.getDrops(clickedState, level, clickedPos, level.getBlockEntity(clickedPos))

        level.captureBlockSnapshots = true
        level.setBlockAndUpdate(clickedPos, stateToPlace)
        level.captureBlockSnapshots = false

        val snapshots = level.capturedBlockSnapshots.toList()
        level.capturedBlockSnapshots.clear()

        for (snapshot in snapshots) {
            if (NeoForge.EVENT_BUS.post(BlockEvent.EntityPlaceEvent(snapshot, clickedState, player)).isCanceled) {
                level.restoringBlockSnapshots = true
                snapshot.restore(snapshot.flags or Block.UPDATE_CLIENTS)
                level.restoringBlockSnapshots = false
                return InteractionResult.FAIL
            }
        }

        val originalStateSoundType = clickedState.soundType
        level.playSound(
            null,
            clickedPos,
            originalStateSoundType.breakSound,
            SoundSource.BLOCKS,
            (originalStateSoundType.volume + 1.0f) / 2.0f,
            originalStateSoundType.pitch * 0.8f
        )

        val newStateSoundType = stateToPlace.soundType
        level.playSound(
            null,
            clickedPos,
            newStateSoundType.placeSound,
            SoundSource.BLOCKS,
            (newStateSoundType.volume + 1.0f) / 2.0f,
            newStateSoundType.pitch * 0.8f
        )

        for (drop in drops) {
            Block.popResourceFromFace(level, clickedPos, context.clickedFace, drop)
        }

        stackToPlace.consume(1, player)

        usedStack.set(
            DataComponents.CONTAINER,
            ItemContainerContents.fromItems(storedStacks)
        )

        return InteractionResult.SUCCESS
    }

    override fun overrideOtherStackedOnMe(
        thisStack: ItemStack,
        other: ItemStack,
        slot: Slot,
        action: ClickAction,
        player: Player,
        access: SlotAccess
    ): Boolean {
        if (action != ClickAction.SECONDARY
            || !slot.allowModification(player)
            || other.item !is BlockItem
        ) return false

        val currentContents = thisStack.get(DataComponents.CONTAINER) ?: ItemContainerContents.fromItems(listOf())
        val storedStacks = currentContents.nonEmptyItems().toList()

        val mayInsert = storedStacks.any { it.item == other.item } || storedStacks.size + 1 <= ServerConfig.BLOCK_REPLACER_UNIQUE_BLOCKS.get()
        if (!mayInsert) return false

        val newContents = OtherUtil.flattenStacks(storedStacks + other.copy())
        thisStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(newContents))

        other.count = 0

        player.level().playSound(
            null,
            player.blockPosition(),
            SoundEvents.ITEM_PICKUP,
            SoundSource.PLAYERS,
            1f,
            0.33f
        )

        return true
    }

    override fun overrideStackedOnOther(
        thisStack: ItemStack,
        slot: Slot,
        action: ClickAction,
        player: Player
    ): Boolean {
        if (action != ClickAction.SECONDARY || !slot.allowModification(player)) return false

        val otherStack = slot.item
        if (!otherStack.isEmpty) return false

        val currentContents = thisStack.get(DataComponents.CONTAINER) ?: ItemContainerContents.fromItems(listOf())
        val storedStacks = currentContents.nonEmptyItems().toMutableList()

        if (storedStacks.isEmpty()) return false

        val stackToInsert = storedStacks.removeLast()
        slot.set(stackToInsert.copy())

        thisStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(storedStacks))

        player.level().playSound(
            null,
            player.blockPosition(),
            SoundEvents.ITEM_PICKUP,
            SoundSource.PLAYERS,
            1f,
            0.33f
        )

        return true
    }

}