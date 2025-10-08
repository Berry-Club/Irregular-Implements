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
		if (this.dyeColor == null) Blocks.GRASS_BLOCK else ModBlocks.getColoredGrass(dyeColor).get()
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

		fun getAllColoredSeeds(): List<DeferredItem<GrassSeedItem>> {
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
				ModItems.GRASS_SEEDS_BLACK
			)
		}

		fun getAllSeeds(): List<DeferredItem<GrassSeedItem>> {
			return getAllColoredSeeds() + ModItems.GRASS_SEEDS
		}

		fun getFromColor(color: DyeColor?): DeferredItem<GrassSeedItem>? {
			return if (color == null) {
				ModItems.GRASS_SEEDS
			} else {
				getAllColoredSeeds().firstOrNull { it.get().dyeColor == color }
			}
		}
	}

}