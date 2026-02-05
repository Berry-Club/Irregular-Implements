package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModMobEffects

object ModEffectLang {

	fun add(provider: ModLanguageProvider) {
		provider.apply {
			addEffect(ModMobEffects.FIRE_IMBUE, "Fire Imbue")
			addEffect(ModMobEffects.POISON_IMBUE, "Poison Imbue")
			addEffect(ModMobEffects.EXPERIENCE_IMBUE, "Experience Imbue")
			addEffect(ModMobEffects.WITHER_IMBUE, "Wither Imbue")
			addEffect(ModMobEffects.COLLAPSE_IMBUE, "Collapse Imbue")
			addEffect(ModMobEffects.SPECTRE_IMBUE, "Spectre Imbue")
			addEffect(ModMobEffects.COLLAPSE, "Collapse")
		}
	}

}