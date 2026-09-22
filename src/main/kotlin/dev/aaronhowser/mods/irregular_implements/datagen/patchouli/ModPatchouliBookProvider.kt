package dev.aaronhowser.mods.irregular_implements.datagen.patchouli

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.patchoulidatagen.patchouli.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.patchouli.dsl.patchouliBook
import dev.aaronhowser.mods.patchoulidatagen.patchouli.provider.PatchouliBookProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation

class ModPatchouliBookProvider(
	generator: DataGenerator,
	private val registries: HolderLookup.Provider
) : PatchouliBookProvider(generator, registries, "guide") {

	override fun buildBook(): PatchouliBook = patchouliBook(
		namespace = IrregularImplements.MOD_ID,
		name = "item.irregular_implements.guide_book",
		landingText = "book.irregular_implements.landing_text"
	) {
		version = "2"
		creativeTab = "irregular_implements:creative_tab"
		showProgress = false
		bookTexture = ResourceLocation.fromNamespaceAndPath("patchouli", "textures/gui/book_gray.png")

		BlocksPatchouliCategory.generate(this)
		ItemsPatchouliCategory.generate(this)
	}
}