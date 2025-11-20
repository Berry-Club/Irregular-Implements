package dev.aaronhowser.mods.irregular_implements.datagen.patchouli

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModCreativeModeTabs
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.SpotlightPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider
import net.minecraft.data.DataGenerator
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
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

		PatchouliItemsCategory.addItems(consumer, book)
		PatchouliBlocksCategory.blocks(consumer, book)
	}

	companion object {

		fun major(text: String): String {
			return colored(PatchouliBookProvider.Companion.TextColor.LIGHT_PURPLE, text)
		}

		fun minor(text: String): String {
			return colored(PatchouliBookProvider.Companion.TextColor.DARK_AQUA, text)
		}

		fun good(text: String): String {
			return colored(PatchouliBookProvider.Companion.TextColor.GREEN, text)
		}

		fun bad(text: String): String {
			return colored(PatchouliBookProvider.Companion.TextColor.RED, text)
		}

		fun stacksSpotlight(
			list: List<ItemStack>,
			title: String,
			text: String,
			linkRecipe: Boolean
		): SpotlightPage {
			val builder = SpotlightPage.Companion.builder()
				.text(text)
				.linkRecipe(linkRecipe)

			if (title.isNotEmpty()) {
				builder.title(title)
			}

			for (item in list) {
				builder.addItemStack(item)
			}

			return builder.build()
		}

		fun spotlight(
			list: List<ItemLike>,
			title: String,
			text: String,
			linkRecipe: Boolean
		): SpotlightPage {
			val builder = SpotlightPage.Companion.builder()
				.text(text)
				.linkRecipe(linkRecipe)

			if (title.isNotEmpty()) {
				builder.title(title)
			}

			for (item in list) {
				builder.addItemLike(item)
			}

			return builder.build()
		}

		fun dottedLines(vararg lines: String): String {
			val sb = StringBuilder()

			for (line in lines) {
				sb.append(LI).append(line)
			}

			return sb.toString()
		}
	}

}