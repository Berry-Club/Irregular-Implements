package dev.aaronhowser.mods.irregular_implements.datagen.worldgen

import dev.aaronhowser.mods.irregular_implements.block.LotusBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Direction
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

object ModConfiguredFeatures {

    val LOTUS_BUSH_KEY: ResourceKey<ConfiguredFeature<*, *>> = registerKey("lotus_bush")
    val PITCHER_PLANT_KEY: ResourceKey<ConfiguredFeature<*, *>> = registerKey("pitcher_plant")

    fun bootstrap(context: BootstrapContext<ConfiguredFeature<*, *>>) {
        register(
            context,
            LOTUS_BUSH_KEY,
            Feature.RANDOM_PATCH,
            singleBlock(
                ModBlocks.LOTUS.get()
                    .defaultBlockState()
                    .setValue(LotusBlock.AGE, LotusBlock.MAXIMUM_AGE)
            )
        )

        register(
            context,
            PITCHER_PLANT_KEY,
            Feature.FLOWER,
            singleBlock(
                ModBlocks.PITCHER_PLANT.get()
                    .defaultBlockState()
            )
        )

    }

    private fun singleBlock(stateToPlace: BlockState): RandomPatchConfiguration {
        return RandomPatchConfiguration(
            96,
            0,
            0,
            PlacementUtils.filtered(
                Feature.SIMPLE_BLOCK,
                SimpleBlockConfiguration(
                    BlockStateProvider.simple(stateToPlace)
                ),
                BlockPredicate.allOf(
                    BlockPredicate.ONLY_IN_AIR_PREDICATE,
                    BlockPredicate.matchesTag(
                        Direction.DOWN.normal,
                        BlockTags.DIRT
                    )
                )
            )
        )
    }


    fun registerKey(name: String): ResourceKey<ConfiguredFeature<*, *>> {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, OtherUtil.modResource(name))
    }

    private fun <FC : FeatureConfiguration, F : Feature<FC>> register(
        context: BootstrapContext<ConfiguredFeature<*, *>>,
        key: ResourceKey<ConfiguredFeature<*, *>>,
        feature: F,
        configuration: FC
    ) {
        context.register(key, ConfiguredFeature(feature, configuration))
    }

}