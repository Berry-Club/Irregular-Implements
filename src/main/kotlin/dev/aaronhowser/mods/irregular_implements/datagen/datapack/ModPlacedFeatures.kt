package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.*


object ModPlacedFeatures {

    val LOTUS_BUSH: ResourceKey<PlacedFeature> = registerKey("lotus_bush")
    val PITCHER_PLANT: ResourceKey<PlacedFeature> = registerKey("pitcher_plant")
    val NATURE_CORE: ResourceKey<PlacedFeature> = registerKey("nature_core")

    fun bootstrap(context: BootstrapContext<PlacedFeature>) {
        val configuredFeatures: HolderGetter<ConfiguredFeature<*, *>> = context.lookup(Registries.CONFIGURED_FEATURE)

        register(
            context,
            LOTUS_BUSH,
            configuredFeatures.getOrThrow(ModConfiguredFeatures.LOTUS_BUSH),
            listOf(
                RarityFilter.onAverageOnceEvery(288),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
            )
        )

        register(
            context,
            PITCHER_PLANT,
            configuredFeatures.getOrThrow(ModConfiguredFeatures.PITCHER_PLANT),
            listOf(
                RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
            )
        )

        register(
            context,
            NATURE_CORE,
            configuredFeatures.getOrThrow(ModConfiguredFeatures.NATURE_CORE),
            listOf(
                RarityFilter.onAverageOnceEvery(500),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
            )
        )

    }

    private fun registerKey(name: String): ResourceKey<PlacedFeature> {
        return ResourceKey.create(Registries.PLACED_FEATURE, OtherUtil.modResource(name))
    }

    private fun register(
        context: BootstrapContext<PlacedFeature>,
        key: ResourceKey<PlacedFeature>,
        configuration: Holder<ConfiguredFeature<*, *>>,
        modifiers: List<PlacementModifier>
    ) {
        context.register(key, PlacedFeature(configuration, java.util.List.copyOf(modifiers)))
    }

}