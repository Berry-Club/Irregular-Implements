package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.block.block_entity.CustomCraftingTableBlockEntity
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class CraftingCarver : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {

        fun tryReplaceWithCraftingTable(level: Level, pos: BlockPos): Boolean {
            val blockEntityThere = level.getBlockEntity(pos) != null
            if (blockEntityThere) return tryRemoveCraftingTable(level, pos)

            val stateThere = level.getBlockState(pos)
            if (stateThere.isAir
                || stateThere.`is`(ModBlockTagsProvider.CUSTOM_CRAFTING_TABLE_BLACKLIST)
                || !stateThere.isCollisionShapeFullBlock(level, pos)
            ) return false

            val placedSuccessfully = level.setBlockAndUpdate(
                pos,
                ModBlocks.CUSTOM_CRAFTING_TABLE.get().defaultBlockState()
            )

            if (!placedSuccessfully) return false

            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity !is CustomCraftingTableBlockEntity) {
                level.setBlockAndUpdate(pos, stateThere)
                return false
            }

            blockEntity.renderedBlockState = stateThere

            return true
        }

        fun tryRemoveCraftingTable(level: Level, pos: BlockPos): Boolean {
            val blockEntity = level.getBlockEntity(pos) as? CustomCraftingTableBlockEntity
                ?: return false

            val stateRendered = blockEntity.renderedBlockState

            val removedSuccessfully = level.setBlockAndUpdate(
                pos,
                stateRendered
            )

            return removedSuccessfully
        }

    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos

        if (level.isClientSide) return InteractionResult.SUCCESS

        return if (tryReplaceWithCraftingTable(level, clickedPos)) {
            InteractionResult.SUCCESS
        } else {
            InteractionResult.FAIL
        }
    }

}