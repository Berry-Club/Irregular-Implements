package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects

object ModEffectLang {

	fun add(provider: ModLanguageProvider) {
		provider.apply {
			addEffect(ModEffects.FIRE_IMBUE, "Fire Imbue")
			addEffect(ModEffects.POISON_IMBUE, "Poison Imbue")
			addEffect(ModEffects.EXPERIENCE_IMBUE, "Experience Imbue")
			addEffect(ModEffects.WITHER_IMBUE, "Wither Imbue")
			addEffect(ModEffects.COLLAPSE_IMBUE, "Collapse Imbue")
			addEffect(ModEffects.SPECTRE_IMBUE, "Spectre Imbue")
			addEffect(ModEffects.COLLAPSE, "Collapse")
		}
	}

}