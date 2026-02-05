package dev.aaronhowser.mods.irregular_implements.datagen.language

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes

object ModEntityTypeLang {

	fun add(provider: ModLanguageProvider) {
		provider.apply {
			addEntityType(ModEntityTypes.SPECTRE_ILLUMINATOR, "Spectre Illuminator")
			addEntityType(ModEntityTypes.ARTIFICIAL_END_PORTAL, "Artificial End Portal")
			addEntityType(ModEntityTypes.GOLDEN_EGG, "Golden Egg")
			addEntityType(ModEntityTypes.GOLDEN_CHICKEN, "Golden Chicken")
			addEntityType(ModEntityTypes.WEATHER_EGG, "Weather Egg")
			addEntityType(ModEntityTypes.SPIRIT, "Spirit")
		}
	}

}