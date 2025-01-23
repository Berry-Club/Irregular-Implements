package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider
import com.klikli_dev.modonomicon.api.datagen.book.BookModel
import com.klikli_dev.modonomicon.book.BookDisplayMode
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories.BlocksCategoryProvider
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.categories.ItemsCategoryProvider
import java.util.function.BiConsumer

class ModModonomiconProvider(
    defaultLang: BiConsumer<String, String>,
) : SingleBookSubProvider("guide", IrregularImplements.ID, defaultLang) {

    override fun additionalSetup(book: BookModel): BookModel {
        return book
            .withCreativeTab(modLoc("creative_tab"))
            .withDisplayMode(BookDisplayMode.INDEX)
            .withBookTextOffsetX(3)
            .withBookTextOffsetY(3)
            .withBookTextOffsetWidth(-3)
    }

    override fun generateCategories() {
        this.add(ItemsCategoryProvider(this).generate())
        this.add(BlocksCategoryProvider(this).generate())
    }

    override fun bookName(): String {
        return "Irregular Implements Instructional Index"
    }

    override fun bookTooltip(): String {
        return "A guide to all things genetic"
    }

    override fun registerDefaultMacros() {
        // Nothing
    }
}