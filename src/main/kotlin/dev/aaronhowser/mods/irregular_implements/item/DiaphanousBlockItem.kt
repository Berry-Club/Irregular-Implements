package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.DiaphanousBlockEntity
import dev.aaronhowser.mods.irregular_implements.item.component.ItemStackComponent
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Items
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.gameevent.GameEvent

class DiaphanousBlockItem : BlockItem(
    ModBlocks.DIAPHANOUS_BLOCK.get(),
    Properties()
        .stacksTo(64)
        .component(ModDataComponents.ITEMSTACK.get(), ItemStackComponent(Items.STONE.defaultInstance))
) {

    override fun place(context: BlockPlaceContext): InteractionResult {
        if (!context.canPlace()) return InteractionResult.FAIL

        val blockPlaceContext = this.updatePlacementContext(context) ?: return InteractionResult.FAIL
        val stateToPlace = this.getPlacementState(blockPlaceContext) ?: return InteractionResult.FAIL

        if (!placeBlock(blockPlaceContext, stateToPlace)) return InteractionResult.FAIL

        val clickedPos = blockPlaceContext.clickedPos
        val clickedFace = blockPlaceContext.clickedFace
        val level = blockPlaceContext.level

        val player = blockPlaceContext.player
        val usedStack = blockPlaceContext.itemInHand
        var clickedBlockState = level.getBlockState(clickedPos)

        val blockEntity = level.getBlockEntity(clickedPos.relative(clickedFace))
        if (blockEntity is DiaphanousBlockEntity) {
            val blockItemStack = usedStack.get(ModDataComponents.ITEMSTACK.get())?.itemStack
            if (blockItemStack != null) {
                blockEntity.blockToRender = blockItemStack
            }
        }

        if (clickedBlockState.`is`(stateToPlace.block)) {
            clickedBlockState = this.updateBlockStateFromTag(clickedPos, level, usedStack, clickedBlockState)

            this.updateCustomBlockEntityTag(clickedPos, level, player, usedStack, clickedBlockState)
            updateBlockEntityComponents(level, clickedPos, usedStack)

            clickedBlockState.block.setPlacedBy(level, clickedPos, clickedBlockState, player, usedStack)
            if (player is ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger(player, clickedPos, usedStack)
            }
        }

        if (player != null) {
            val soundType = clickedBlockState.getSoundType(level, clickedPos, player)
            level.playSound(
                player,
                clickedPos,
                this.getPlaceSound(clickedBlockState, level, clickedPos, player),
                SoundSource.BLOCKS,
                (soundType.getVolume() + 1.0f) / 2.0f,
                soundType.getPitch() * 0.8f
            )
        }

        level.gameEvent(GameEvent.BLOCK_PLACE, clickedPos, GameEvent.Context.of(player, clickedBlockState))
        usedStack.consume(1, player)
        return InteractionResult.sidedSuccess(level.isClientSide)
    }

}