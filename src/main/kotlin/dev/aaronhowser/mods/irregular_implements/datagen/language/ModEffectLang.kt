package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects

object ModEffectLang {

	fun add(provider: ModLanguageProvider) {
		provider.addEffect(ModEffects.FIRE_IMBUE, "Fire Imbue")
		provider.addEffect(ModEffects.POISON_IMBUE, "Poison Imbue")
		provider.addEffect(ModEffects.EXPERIENCE_IMBUE, "Experience Imbue")
		provider.addEffect(ModEffects.WITHER_IMBUE, "Wither Imbue")
		provider.addEffect(ModEffects.COLLAPSE_IMBUE, "Collapse Imbue")
		provider.addEffect(ModEffects.SPECTRE_IMBUE, "Spectre Imbue")
		provider.addEffect(ModEffects.COLLAPSE, "Collapse")
	}

}