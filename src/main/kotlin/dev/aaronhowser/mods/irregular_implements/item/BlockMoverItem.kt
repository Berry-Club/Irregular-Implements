package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.component.BlockDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.level.BlockEvent

class BlockMoverItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
        val player = context.player
        if (stack.isEmpty
            || player == null
            || player.isSecondaryUseActive
        ) return InteractionResult.PASS

        val blockDataComponent = stack.get(ModDataComponents.BLOCK_DATA)

        return if (blockDataComponent == null) tryPickUpBlock(player, stack, context) else tryPlaceBlock(player, stack, context)
    }

    companion object {
        fun tryPickUpBlock(player: Player, stack: ItemStack, context: UseOnContext): InteractionResult {
            val level = context.level
            val clickedPos = context.clickedPos
            val blockState = level.getBlockState(clickedPos)

            if (blockState.isAir
                || blockState.getDestroySpeed(level, clickedPos) == -1f
                || blockState.`is`(ModBlockTagsProvider.BLOCK_MOVER_BLACKLIST)
                || !level.mayInteract(player, clickedPos)
                || !player.mayUseItemAt(clickedPos, context.clickedFace, stack)
                || NeoForge.EVENT_BUS.post(BlockEvent.BreakEvent(level, clickedPos, blockState, player)).isCanceled
            ) return InteractionResult.FAIL

            val blockEntity = level.getBlockEntity(clickedPos)

            if (level.isClientSide) return InteractionResult.PASS

            val blockDataComponent = BlockDataComponent(level.registryAccess(), blockState, blockEntity)
            stack.set(ModDataComponents.BLOCK_DATA, blockDataComponent)

            level.setBlockAndUpdate(clickedPos, Blocks.AIR.defaultBlockState())

            return InteractionResult.SUCCESS
        }

        fun tryPlaceBlock(player: Player, stack: ItemStack, context: UseOnContext): InteractionResult {
            return InteractionResult.FAIL
        }
    }

}