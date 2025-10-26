package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModCreativeModeTabs
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.multiblock.PatchouliMultiblock
import dev.aaronhowser.mods.patchoulidatagen.page.AbstractPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.MultiblockPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.SpotlightPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider
import net.minecraft.core.Direction
import net.minecraft.data.DataGenerator
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndRodBlock
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import java.util.function.Consumer

class ModPatchouliBookProvider(
	generator: DataGenerator,
	bookName: String
) : PatchouliBookProvider(generator, bookName, IrregularImplements.MOD_ID) {

	override fun buildPages(consumer: Consumer<PatchouliBookElement>) {
		val book = PatchouliBook.builder()
			.setBookText(
				bookModId = IrregularImplements.MOD_ID,
				name = "Irregular Implements Instructional Index",
				landingText = "Welcome to the Irregular Implements!"
			)
			.creativeTab(ModCreativeModeTabs.MOD_TAB.key!!.location().toString())
			.hideProgress()
			.save(consumer)

		items(consumer, book)
		blocks(consumer, book)
	}

	private fun items(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
		val category = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Items",
				description = "All of the mods' items!",
				icon = ModItems.SPECTRE_KEY
			)
			.save(consumer, "items")

		fun add(
			item: DeferredItem<*>,
			name: String,
			vararg pages: AbstractPage
		): PatchouliBookEntry {
			val builder = PatchouliBookEntry.builder()
				.category(category)
				.display(
					entryName = name,
					icon = item
				)

			for (page in pages) {
				builder.addPage(page)
			}

			return builder.save(consumer, item.key!!.location().path)
		}

		add(
			ModItems.STABLE_ENDER_PEARL,
			"Stable Ender Pearl",
			TextPage.basicTextPage(
				"Stable Ender Pearl",
				doubleSpacedLines(
					"Use to bind yourself to the Pearl.",
					"After seven seconds of existing as an item entity, the Pearl will teleport the bound player to its location."
				)
			),
			SpotlightPage.basicPage(
				ModItems.STABLE_ENDER_PEARL,
				doubleSpacedLines(
					"You don't have to drop it yourself! It'll still teleport you if it's dropped in any other way.",
					"It has to be loaded though, and you must be in the same dimension!"
				)
			)
		)

		add(
			ModItems.EVIL_TEAR,
			"Evil Tear",
			TextPage.basicTextPage(
				"Evil Tear",
				"A tear dropped by malevolent spirits."
			),
			MultiblockPage.builder()
				.name("Artificial End Portal")
				.multiblock(
					"Artificial End Portal",
					PatchouliMultiblock.builder()
						.setSymmetrical()
						.pattern(
							arrayOf(
								"     ",
								"     ",
								"  E  ",
								"     ",
								"     ",
							),
							arrayOf(
								"     ",
								"     ",
								"  R  ",
								"     ",
								"     ",
							),
							arrayOf(
								"     ",
								"     ",
								"     ",
								"     ",
								"     ",
							),
							arrayOf(
								"     ",
								"     ",
								"     ",
								"     ",
								"     ",
							),
							arrayOf(
								"BBBBB",
								"B   B",
								"B 0 B",
								"B   B",
								"BBBBB"
							),
							arrayOf(
								"     ",
								" EEE ",
								" EEE ",
								" EEE ",
								"     ",
							)
						)
						.map('B', Tags.Blocks.OBSIDIANS)
						.map('E', Tags.Blocks.END_STONES)
						.map('R', Blocks.END_ROD, EndRodBlock.FACING, Direction.DOWN)
						.build()
				)
				.build()
		)

	}

	private fun blocks(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
		val category = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Blocks",
				description = "All of the mods' blocks!",
				icon = ModBlocks.PLAYER_INTERFACE
			)
			.save(consumer, "blocks")

		fun add(
			block: DeferredBlock<*>,
			name: String,
			vararg pages: AbstractPage
		): PatchouliBookEntry {
			val builder = PatchouliBookEntry.builder()
				.category(category)
				.display(
					entryName = name,
					icon = block
				)

			for (page in pages) {
				builder.addPage(page)
			}

			return builder.save(consumer, block.key!!.location().path)
		}

		add(
			ModBlocks.SPECTRE_LOG,
			"Spectre Log",
			TextPage.basicTextPage(
				"Spectre Log",
				"A log block infused with otherworldly energy."
			)
		)

	}

}