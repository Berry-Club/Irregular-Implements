package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider

object ModSubtitleLang {

	const val FART = "subtitle.irregular_implements.fart"

	fun add(provider: ModLanguageProvider) {
		provider.add(FART, "Pbbbt")
	}

}