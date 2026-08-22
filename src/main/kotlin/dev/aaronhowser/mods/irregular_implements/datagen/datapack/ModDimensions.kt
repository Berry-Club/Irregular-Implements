package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.Level
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.FlatLevelSource
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings
import java.util.*

object ModDimensions {

	val SPECTRE_ID: ResourceLocation = OtherUtil.modResource("spectre")

	val SPECTRE_LEVEL_STEM_KEY: ResourceKey<LevelStem> =
		ResourceKey.create(Registries.LEVEL_STEM, SPECTRE_ID)

	val SPECTRE_LEVEL_KEY: ResourceKey<Level> =
		ResourceKey.create(Registries.DIMENSION, SPECTRE_ID)

	val SPECTRE_DIMENSION_TYPE_KEY: ResourceKey<DimensionType> =
		ResourceKey.create(Registries.DIMENSION_TYPE, SPECTRE_ID)

	fun bootstrapDimensionTypes(context: BootstrapContext<DimensionType>) {
		context.register(
			SPECTRE_DIMENSION_TYPE_KEY,
			DimensionType(
				OptionalLong.of(6000L),
				false,
				false,
				false,
				false,
				1.0,
				false,
				false,
				0,
				256,
				256,
				BlockTags.INFINIBURN_OVERWORLD,
				SPECTRE_ID,
				1f,
				DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
			)
		)
	}

	fun bootstrapLevelStems(context: BootstrapContext<LevelStem>) {
		val dimensionTypeRegistry = context.lookup(Registries.DIMENSION_TYPE)
		val biomeRegistry = context.lookup(Registries.BIOME)
		val structureSetRegistry = context.lookup(Registries.STRUCTURE_SET)
		val placedFeatureRegistry = context.lookup(Registries.PLACED_FEATURE)

		val generatorSettings =
			FlatLevelGeneratorSettings.getDefault(biomeRegistry, structureSetRegistry, placedFeatureRegistry)
				.withBiomeAndLayers(
					listOf(),
					Optional.empty(),
					biomeRegistry.getOrThrow(ModBiomes.SPECTRAL_BIOME_RK)
				)

		val generator = FlatLevelSource(generatorSettings)
		val dimensionType = dimensionTypeRegistry.getOrThrow(SPECTRE_DIMENSION_TYPE_KEY)

		context.register(SPECTRE_LEVEL_STEM_KEY, LevelStem(dimensionType, generator))
	}

}