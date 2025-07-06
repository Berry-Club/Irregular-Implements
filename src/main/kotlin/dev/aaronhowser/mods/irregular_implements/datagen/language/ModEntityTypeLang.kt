package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes

object ModEntityTypeLang {

	fun add(provider: ModLanguageProvider) {
		provider.addEntityType(ModEntityTypes.SPECTRE_ILLUMINATOR, "Spectre Illuminator")
		provider.addEntityType(ModEntityTypes.INDICATOR_DISPLAY, "Indicator Display")
		provider.addEntityType(ModEntityTypes.ARTIFICIAL_END_PORTAL, "Artificial End Portal")
		provider.addEntityType(ModEntityTypes.GOLDEN_EGG, "Golden Egg")
		provider.addEntityType(ModEntityTypes.GOLDEN_CHICKEN, "Golden Chicken")
	}

}