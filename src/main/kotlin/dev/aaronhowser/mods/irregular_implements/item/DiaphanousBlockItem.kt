package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.item.component.ItemStackComponent
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.gameevent.GameEvent

class DiaphanousBlockItem : BlockItem(
    ModBlocks.DIAPHANOUS_BLOCK.get(),
    Properties()
        .stacksTo(64)
        .component(ModDataComponents.ITEMSTACK.get(), ItemStackComponent(Items.DIRT.defaultInstance))
) {

    // Mostly the same
    override fun place(context: BlockPlaceContext): InteractionResult {
        if (!context.canPlace()) return InteractionResult.FAIL

        val blockPlaceContext = this.updatePlacementContext(context) ?: return InteractionResult.FAIL
        val stateToPlace = this.getPlacementState(blockPlaceContext) ?: return InteractionResult.FAIL

        if (!placeBlock(blockPlaceContext, stateToPlace)) return InteractionResult.FAIL

        val posToPlace = blockPlaceContext.clickedPos
        val level = blockPlaceContext.level
        val player = blockPlaceContext.player
        val usedStack = blockPlaceContext.itemInHand
        var clickedBlockState = level.getBlockState(posToPlace)

        // This is the only part that's different
        val blockEntity = level.getBlockEntity(posToPlace)
        if (blockEntity is DiaphanousBlockEntity) {
            val blockItemStack = usedStack.get(ModDataComponents.ITEMSTACK.get())?.itemStack
            if (blockItemStack != null) {
                blockEntity.blockToRender = blockItemStack
            }
        }

        if (clickedBlockState.`is`(stateToPlace.block)) {
            clickedBlockState = this.updateBlockStateFromTag(posToPlace, level, usedStack, clickedBlockState)

            this.updateCustomBlockEntityTag(posToPlace, level, player, usedStack, clickedBlockState)
            updateBlockEntityComponents(level, posToPlace, usedStack)

            clickedBlockState.block.setPlacedBy(level, posToPlace, clickedBlockState, player, usedStack)
            if (player is ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger(player, posToPlace, usedStack)
            }
        }

        if (player != null) {
            val soundType = clickedBlockState.getSoundType(level, posToPlace, player)
            level.playSound(
                player,
                posToPlace,
                this.getPlaceSound(clickedBlockState, level, posToPlace, player),
                SoundSource.BLOCKS,
                (soundType.getVolume() + 1.0f) / 2.0f,
                soundType.getPitch() * 0.8f
            )
        }

        level.gameEvent(GameEvent.BLOCK_PLACE, posToPlace, GameEvent.Context.of(player, clickedBlockState))
        usedStack.consume(1, player)
        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag)

        val itemStack = stack.get(ModDataComponents.ITEMSTACK.get())?.itemStack
        if (itemStack != null) {
            tooltipComponents.add(itemStack.displayName)
        }
    }

    override fun overrideOtherStackedOnMe(
        thisStack: ItemStack,
        otherStack: ItemStack,
        slot: Slot,
        action: ClickAction,
        player: Player,
        access: SlotAccess
    ): Boolean {
        if (action != ClickAction.SECONDARY) return false

        val otherStackBlock = (otherStack.item as? BlockItem)?.block ?: return false

        val blockIsFull = otherStackBlock.defaultBlockState().isCollisionShapeFullBlock(player.level(), player.blockPosition())
        if (!blockIsFull) return false

        thisStack.set(
            ModDataComponents.ITEMSTACK.get(),
            ItemStackComponent(otherStack)
        )

        return true
    }

}