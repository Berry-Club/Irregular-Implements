package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.entries

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase
import com.klikli_dev.modonomicon.api.datagen.EntryBackground
import com.klikli_dev.modonomicon.api.datagen.EntryProvider
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel
import com.mojang.datafixers.util.Either
import com.mojang.datafixers.util.Pair
import net.minecraft.ChatFormatting
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import kotlin.jvm.optionals.getOrNull

abstract class BaseEntryProvider : EntryProvider {

	constructor(
		parent: CategoryProviderBase?,
		name: String,
		icon: DeferredItem<*>
	) : this(parent, name, icon, icon.id.path)

	constructor(
		parent: CategoryProviderBase?,
		name: String,
		icon: DeferredBlock<*>
	) : this(parent, name, icon, icon.id.path)

	constructor(
		parent: CategoryProviderBase?,
		name: String,
		icon: ResourceLocation,
		entryId: String
	) : super(parent) {
		this.name = name
		this.entryId = entryId
		this.icon = Either.left(icon)
	}

	constructor(
		parent: CategoryProviderBase?,
		name: String,
		icon: ItemStack,
		entryId: String
	) : super(parent) {
		this.name = name
		this.entryId = entryId
		this.icon = Either.right(icon)
	}

	constructor(
		parent: CategoryProviderBase?,
		name: String,
		icon: ItemLike,
		entryId: String
	) : this(parent, name, icon.asItem().defaultInstance, entryId)

	val name: String
	val entryId: String
	val icon: Either<ResourceLocation, ItemStack>

	override fun entryName(): String {
		return this.name
	}

	override fun entryId(): String {
		return this.entryId
	}

	override fun entryBackground(): Pair<Int, Int> {
		return EntryBackground.DEFAULT
	}

	override fun entryIcon(): BookIconModel {
		return if (this.icon.left().isPresent) {
			BookIconModel.create(this.icon.left().get())
		} else {
			BookIconModel.create(this.icon.right().get())
		}
	}

	override fun entryDescription(): String {
		return ""
	}

	companion object {
		fun coloredText(color: ChatFormatting, text: String): String {
			val colorHexInt = color.color ?: error("Invalid color $color")
			val colorString = String.format("%06X", 0xFFFFFF and colorHexInt)

			return "[#](${colorString})$text[#]()"
		}

		fun major(text: String): String = coloredText(ChatFormatting.DARK_PURPLE, text)
		fun minor(text: String): String = coloredText(ChatFormatting.DARK_AQUA, text)
		fun bad(text: String): String = coloredText(ChatFormatting.RED, text)

		fun paragraphs(vararg paragraphs: String): String {
			return paragraphs.joinToString(separator = " \\\n  \\\n")
		}
	}

	fun block(text: String, entryId: String): String {
		return "[${text}](entry://blocks/${entryId})"
	}

	fun item(text: String, entryId: String): String {
		return "[${text}](entry://items/${entryId})"
	}

	private var pageIndex = 0

	fun textPage(
		text: String
	): BookTextPageModel {
		return textPage(
			"page_${this.pageIndex++}",
			"",
			text
		)
	}

	fun textPage(
		title: String,
		text: String
	): BookTextPageModel {
		return textPage(
			"page_${this.pageIndex++}",
			title,
			text
		)
	}

	fun textPage(
		pageId: String,
		title: String,
		text: String
	): BookTextPageModel {
		val page = this.page(pageId) {
			BookTextPageModel.create()
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
		}

		this.pageTitle(title)
		this.pageText(text)

		return page
	}

	fun spotlightPage(
		itemLike: ItemLike,
		text: String
	): BookSpotlightPageModel {
		return this.spotlightPage(itemLike.asItem().defaultInstance, "", text)
	}

	fun spotlightPage(
		text: String
	): BookSpotlightPageModel {
		val stack = this.icon.right().getOrNull() ?: error("No item stack for spotlight page")
		return this.spotlightPage(stack, "", text)
	}

	fun spotlightPage(
		title: String,
		text: String
	): BookSpotlightPageModel {
		val stack = this.icon.right().getOrNull() ?: error("No item stack for spotlight page")
		return this.spotlightPage(stack, title, text)
	}

	fun spotlightPage(
		itemStack: ItemStack,
		text: String
	): BookSpotlightPageModel {
		return this.spotlightPage(itemStack, "", text)
	}

	fun spotlightPage(
		itemStack: ItemStack,
		title: String,
		text: String,
	): BookSpotlightPageModel {

		val page = this.page("page_${this.pageIndex++}") {
			BookSpotlightPageModel.create()
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
				.withItem(itemStack)
		}

		this.pageTitle(title)
		this.pageText(text)

		return page
	}

	fun spotlightPage(
		ingredient: Ingredient,
		text: String
	): BookSpotlightPageModel {
		return this.spotlightPage(ingredient, "", text)
	}

	fun spotlightPage(
		ingredient: Ingredient,
		title: String,
		text: String
	): BookSpotlightPageModel {
		val page = this.page("page_${this.pageIndex++}") {
			BookSpotlightPageModel.create()
				.withTitle(this.context().pageTitle())
				.withText(this.context().pageText())
				.withItem(ingredient)
		}

		this.pageTitle(title)
		this.pageText(text)

		return page
	}

}