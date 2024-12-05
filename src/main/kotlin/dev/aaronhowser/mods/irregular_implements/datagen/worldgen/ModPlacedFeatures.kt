package dev.aaronhowser.mods.irregular_implements.datagen.worldgen

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

    val LOTUS_BUSH_PLACEMENT_KEY: ResourceKey<PlacedFeature> = registerKey("lotus_bush")
    val PITCHER_PLANT_PLACEMENT_KEY: ResourceKey<PlacedFeature> = registerKey("pitcher_plant")

    fun bootstrap(context: BootstrapContext<PlacedFeature>) {
        val configuredFeatures: HolderGetter<ConfiguredFeature<*, *>> = context.lookup(Registries.CONFIGURED_FEATURE)

        register(
            context,
            LOTUS_BUSH_PLACEMENT_KEY,
            configuredFeatures.getOrThrow(ModConfiguredFeatures.LOTUS_BUSH_KEY),
            listOf(
                RarityFilter.onAverageOnceEvery(288),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
            )
        )

        register(
            context,
            PITCHER_PLANT_PLACEMENT_KEY,
            configuredFeatures.getOrThrow(ModConfiguredFeatures.PITCHER_PLANT_KEY),
            listOf(
                RarityFilter.onAverageOnceEvery(10),
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