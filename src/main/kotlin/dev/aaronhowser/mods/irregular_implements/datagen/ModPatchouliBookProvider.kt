package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModCreativeModeTabs
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.page.AbstractPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.SpotlightPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider
import net.minecraft.client.resources.language.I18n
import net.minecraft.data.DataGenerator
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
			vararg pages: AbstractPage
		): PatchouliBookEntry {
			val builder = PatchouliBookEntry.builder()
				.category(category)
				.display(
					entryName = I18n.get(item.value().descriptionId),
					icon = item
				)

			for (page in pages) {
				builder.addPage(page)
			}

			return builder.save(consumer, item.key!!.location().path)
		}

		add(
			ModItems.SPECTRE_KEY,
			TextPage.builder()
				.title("Spectre Key")
				.text("Testttt")
				.build(),
			SpotlightPage.builder()
				.item(ModItems.SPECTRE_KEY)
				.build()
		)

	}

	private fun blocks(consumer: Consumer<PatchouliBookElement>, book: PatchouliBook) {
		val blocksCategory = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Blocks",
				description = "All of the mods' blocks!",
				icon = ModBlocks.PLAYER_INTERFACE
			)
			.save(consumer, "blocks")
	}

}