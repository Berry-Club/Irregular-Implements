package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBiomeTagsProvider
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.common.world.BiomeModifiers
import net.neoforged.neoforge.registries.NeoForgeRegistries


object ModBiomeModifiers {

	val LOTUS_BUSH = registerKey("lotus_bush")
	val PITCHER_PLANT = registerKey("pitcher_plant")
	val NATURE_CORE = registerKey("nature_core")

	fun bootstrap(context: BootstrapContext<BiomeModifier>) {
		val placedFeatures: HolderGetter<PlacedFeature> = context.lookup(Registries.PLACED_FEATURE)
		val biomes: HolderGetter<Biome> = context.lookup(Registries.BIOME)

		context.register(
			LOTUS_BUSH,
			BiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD),
				HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LOTUS_BUSH)),
				GenerationStep.Decoration.VEGETAL_DECORATION
			)
		)

		context.register(
			PITCHER_PLANT,
			BiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(Tags.Biomes.IS_WET_OVERWORLD),
				HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PITCHER_PLANT)),
				GenerationStep.Decoration.VEGETAL_DECORATION
			)
		)

		context.register(
			NATURE_CORE,
			BiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(ModBiomeTagsProvider.HAS_NATURE_CORE),
				HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NATURE_CORE)),
				GenerationStep.Decoration.SURFACE_STRUCTURES
			)
		)

	}

	private fun registerKey(name: String): ResourceKey<BiomeModifier> {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, OtherUtil.modResource(name))
	}

}