package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon

import com.klikli_dev.modonomicon.book.BookDisplayMode
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories.BlocksModonomiconCategory
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories.ItemsModonomiconCategory
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBook
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.dsl.modonomiconBook
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.provider.ModonomiconBookProvider
import java.util.function.BiConsumer

class ModModonomiconProvider(
	defaultLanguage: BiConsumer<String, String>
) : ModonomiconBookProvider(
	bookId = "guide",
	namespace = IrregularImplements.MOD_ID,
	name = "Irregular Implements Instructional Index",
	tooltip = "A guide to Irregular Implements",
	defaultLanguage = defaultLanguage
) {

	override fun buildBook(): ModonomiconBook = modonomiconBook(
		namespace = IrregularImplements.MOD_ID,
		saveName = "guide",
		name = "Irregular Implements Instructional Index",
		tooltip = "A guide to Irregular Implements",
		registries = registries()
	) {
		description = "A guide to Irregular Implements"
		creativeTab = modLoc("creative_tab")
		displayMode = BookDisplayMode.INDEX
		bookTextOffsetX = 3
		bookTextOffsetY = 3
		bookTextOffsetWidth = -3

		BlocksModonomiconCategory.generate(this)
		ItemsModonomiconCategory.generate(this)
	}
}