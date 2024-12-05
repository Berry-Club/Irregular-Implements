package dev.aaronhowser.mods.irregular_implements.datagen.worldgen

import dev.aaronhowser.mods.irregular_implements.block.LotusBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

object ModConfiguredFeatures {

    val LOTUS_BUSH_KEY: ResourceKey<ConfiguredFeature<*, *>> = registerKey("lotus_bush")

    fun bootstrap(context: BootstrapContext<ConfiguredFeature<*, *>>) {

        register(
            context, LOTUS_BUSH_KEY,
            Feature.RANDOM_PATCH,
            FeatureUtils.simplePatchConfiguration(
                Feature.SIMPLE_BLOCK,
                SimpleBlockConfiguration(
                    BlockStateProvider.simple(
                        ModBlocks.LOTUS.get()
                            .defaultBlockState()
                            .setValue(LotusBlock.AGE, LotusBlock.MAXIMUM_AGE)
                    )
                ),
                listOf(Blocks.GRASS_BLOCK)
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