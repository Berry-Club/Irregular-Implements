package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CraftingTableBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class CustomCraftingTableBlock : CraftingTableBlock(
    Properties
        .ofFullCopy(Blocks.CRAFTING_TABLE)
        .noOcclusion()
        .isViewBlocking(Blocks::never)
), EntityBlock {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = CustomCraftingTableBlockEntity(pos, state)

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): ItemInteractionResult {
        if (level.isClientSide || stack.item !is BlockItem) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

        val blockItem = stack.item as BlockItem
        val block = blockItem.block

        val blockEntity = level.getBlockEntity(pos) as? CustomCraftingTableBlockEntity ?: return ItemInteractionResult.FAIL

        blockEntity.baseBlock = block

        return ItemInteractionResult.SUCCESS
    }

}