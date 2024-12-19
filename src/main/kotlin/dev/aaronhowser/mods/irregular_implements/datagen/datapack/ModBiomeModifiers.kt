package dev.aaronhowser.mods.irregular_implements.datagen.datapack

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

    val ADD_LOTUS_BUSH = registerKey("lotus_bush")
    val ADD_PITCHER_PLANT = registerKey("pitcher_plant")

    fun bootstrap(context: BootstrapContext<BiomeModifier>) {
        val placedFeatures: HolderGetter<PlacedFeature> = context.lookup(Registries.PLACED_FEATURE)
        val biomes: HolderGetter<Biome> = context.lookup(Registries.BIOME)

        context.register(
            ADD_LOTUS_BUSH,
            BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LOTUS_BUSH_PLACEMENT_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        )

        context.register(
            ADD_PITCHER_PLANT,
            BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_WET_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PITCHER_PLANT_PLACEMENT_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        )

    }

    private fun registerKey(name: String): ResourceKey<BiomeModifier> {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, OtherUtil.modResource(name))
    }

}