package dev.aaronhowser.mods.irregular_implements.datagen.patchouli

import dev.aaronhowser.mods.patchoulidatagen.patchouli.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.patchouli.provider.PatchouliBookProvider
import dev.aaronhowser.mods.patchoulidatagen.patchouli.provider.TextColor

object PatchouliBookText {

	fun bookText(vararg paragraphs: String): String {
		val formattedParagraphs = mutableListOf<String>()
		for (paragraph in paragraphs) {
			val formattedParagraph = paragraph
				.replace(Regex("(?m)^- ")) { "$(li)" }
				.replace("\n", "$(br)")

			formattedParagraphs.add(formattedParagraph)
		}

		return formattedParagraphs.joinToString("$(br2)")
	}

	fun major(text: String): String {
		return PatchouliBookProvider.colored(TextColor.LIGHT_PURPLE, text)
	}

	fun minor(text: String): String {
		return PatchouliBookProvider.colored(TextColor.BLUE, text)
	}

	fun bad(text: String): String {
		return PatchouliBookProvider.colored(TextColor.RED, text)
	}

	fun internalLink(entry: PatchouliBookEntry, text: String): String {
		return PatchouliBookProvider.internalLink(entry, text)
	}
}