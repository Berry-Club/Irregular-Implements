package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.BlockTags
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.registries.DeferredItem

class GrassSeedItem(
	val dyeColor: DyeColor?,
	properties: Properties
) : Item(properties) {

	val resultBlock: Block by lazy {
		if (dyeColor != null) {
			ModBlocks.getColoredGrass(dyeColor)?.get() ?: Blocks.GRASS_BLOCK
		} else {
			Blocks.GRASS_BLOCK
		}
	}

	override fun useOn(context: UseOnContext): InteractionResult {
		val clickedPos = context.clickedPos
		val level = context.level

		val clickedState = level.getBlockState(clickedPos)
		if (
			clickedState.`is`(resultBlock)
			|| !clickedState.`is`(BlockTags.DIRT)
		) return InteractionResult.PASS

		level.setBlockAndUpdate(clickedPos, resultBlock.defaultBlockState())
		level.playSound(
			null,
			clickedPos,
			SoundEvents.GRASS_BREAK,
			SoundSource.BLOCKS
		)

		context.itemInHand.consume(1, context.player)

		return InteractionResult.SUCCESS
	}

	companion object {
		@Suppress("REDUNDANT_ELSE_IN_WHEN")
		fun getFromColor(color: DyeColor?): DeferredItem<GrassSeedItem>? {
			return when (color) {
				null -> ModItems.GRASS_SEEDS
				DyeColor.WHITE -> ModItems.GRASS_SEEDS_WHITE
				DyeColor.ORANGE -> ModItems.GRASS_SEEDS_ORANGE
				DyeColor.MAGENTA -> ModItems.GRASS_SEEDS_MAGENTA
				DyeColor.LIGHT_BLUE -> ModItems.GRASS_SEEDS_LIGHT_BLUE
				DyeColor.YELLOW -> ModItems.GRASS_SEEDS_YELLOW
				DyeColor.LIME -> ModItems.GRASS_SEEDS_LIME
				DyeColor.PINK -> ModItems.GRASS_SEEDS_PINK
				DyeColor.GRAY -> ModItems.GRASS_SEEDS_GRAY
				DyeColor.LIGHT_GRAY -> ModItems.GRASS_SEEDS_LIGHT_GRAY
				DyeColor.CYAN -> ModItems.GRASS_SEEDS_CYAN
				DyeColor.PURPLE -> ModItems.GRASS_SEEDS_PURPLE
				DyeColor.BLUE -> ModItems.GRASS_SEEDS_BLUE
				DyeColor.BROWN -> ModItems.GRASS_SEEDS_BROWN
				DyeColor.GREEN -> ModItems.GRASS_SEEDS_GREEN
				DyeColor.RED -> ModItems.GRASS_SEEDS_RED
				DyeColor.BLACK -> ModItems.GRASS_SEEDS_BLACK
				else -> null
			}
		}

		fun getAllSeeds(): List<DeferredItem<GrassSeedItem>> {
			return listOf(
				ModItems.GRASS_SEEDS_WHITE,
				ModItems.GRASS_SEEDS_ORANGE,
				ModItems.GRASS_SEEDS_MAGENTA,
				ModItems.GRASS_SEEDS_LIGHT_BLUE,
				ModItems.GRASS_SEEDS_YELLOW,
				ModItems.GRASS_SEEDS_LIME,
				ModItems.GRASS_SEEDS_PINK,
				ModItems.GRASS_SEEDS_GRAY,
				ModItems.GRASS_SEEDS_LIGHT_GRAY,
				ModItems.GRASS_SEEDS_CYAN,
				ModItems.GRASS_SEEDS_PURPLE,
				ModItems.GRASS_SEEDS_BLUE,
				ModItems.GRASS_SEEDS_BROWN,
				ModItems.GRASS_SEEDS_GREEN,
				ModItems.GRASS_SEEDS_RED,
				ModItems.GRASS_SEEDS_BLACK,
				ModItems.GRASS_SEEDS
			)
		}
	}

}