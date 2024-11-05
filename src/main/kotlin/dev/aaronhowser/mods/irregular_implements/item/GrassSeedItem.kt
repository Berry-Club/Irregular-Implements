package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_BLACK
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_BLUE
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_BROWN
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_CYAN
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_GRAY
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_GREEN
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_LIGHT_BLUE
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_LIGHT_GRAY
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_LIME
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_MAGENTA
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_ORANGE
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_PINK
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_PURPLE
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_RED
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_WHITE
import dev.aaronhowser.mods.irregular_implements.registries.ModItems.GRASS_SEEDS_YELLOW
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.registries.DeferredItem

class GrassSeedItem(
    val dyeColor: DyeColor?
) : Item(Properties()) {

    val resultBlock: Block = if (dyeColor == null) Blocks.GRASS_BLOCK else ModBlocks.getColoredGrass(dyeColor).get()

    override fun useOn(context: UseOnContext): InteractionResult {
        val clickedPos = context.clickedPos
        val level = context.level

        val clickedState = level.getBlockState(clickedPos)
        if (!clickedState.`is`(Blocks.DIRT)) return InteractionResult.PASS

        level.setBlockAndUpdate(clickedPos, resultBlock.defaultBlockState())

        if (!context.player?.hasInfiniteMaterials().isTrue) {
            val usedStack = context.itemInHand
            usedStack.shrink(1)
        }

        return InteractionResult.SUCCESS
    }

    companion object {
        fun getFromColor(color: DyeColor?): DeferredItem<GrassSeedItem> {
            return when (color) {
                null -> GRASS_SEEDS
                DyeColor.WHITE -> GRASS_SEEDS_WHITE
                DyeColor.ORANGE -> GRASS_SEEDS_ORANGE
                DyeColor.MAGENTA -> GRASS_SEEDS_MAGENTA
                DyeColor.LIGHT_BLUE -> GRASS_SEEDS_LIGHT_BLUE
                DyeColor.YELLOW -> GRASS_SEEDS_YELLOW
                DyeColor.LIME -> GRASS_SEEDS_LIME
                DyeColor.PINK -> GRASS_SEEDS_PINK
                DyeColor.GRAY -> GRASS_SEEDS_GRAY
                DyeColor.LIGHT_GRAY -> GRASS_SEEDS_LIGHT_GRAY
                DyeColor.CYAN -> GRASS_SEEDS_CYAN
                DyeColor.PURPLE -> GRASS_SEEDS_PURPLE
                DyeColor.BLUE -> GRASS_SEEDS_BLUE
                DyeColor.BROWN -> GRASS_SEEDS_BROWN
                DyeColor.GREEN -> GRASS_SEEDS_GREEN
                DyeColor.RED -> GRASS_SEEDS_RED
                DyeColor.BLACK -> GRASS_SEEDS_BLACK
            }
        }
    }

}