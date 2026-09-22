package dev.aaronhowser.mods.irregular_implements.datagen.modonomicon

import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBookCategory
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.book_element.ModonomiconBookEntry
import dev.aaronhowser.mods.patchoulidatagen.modonomicon.provider.ModonomiconText

object ModonomiconBookText {

	fun bookText(vararg paragraphs: String): String {
		return ModonomiconText.paragraphs(*paragraphs)
	}

	fun doubleSpacedLines(vararg lines: String): String {
		return ModonomiconText.paragraphs(*lines)
	}

	fun lines(vararg lines: String): String {
		return lines.joinToString("  \\\n")
	}

	fun list(vararg items: String): String {
		return items.joinToString("\n- ", prefix = "- ", postfix = "\n")
	}

	fun major(text: String): String = colored("ff55ff", text)

	fun minor(text: String): String = colored("5555ff", text)

	fun bad(text: String): String = colored("ff5555", text)

	fun internalLink(entry: ModonomiconBookEntry, text: String): String {
		return ModonomiconText.entryLink(entry, text)
	}

	fun internalLink(category: ModonomiconBookCategory, text: String): String {
		return ModonomiconText.categoryLink(category, text)
	}

	fun italic(text: String): String = "_${text}_"

	fun bold(text: String): String = "**$text**"

	private fun colored(hexColor: String, text: String): String {
		return "[#]($hexColor)$text[#]()"
	}
}