package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.dimension.BuiltinDimensionTypes
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.FlatLevelSource
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings
import java.util.*


object ModDimensions {

	val SPECTRE_RL = OtherUtil.modResource("spectre")

	val SPECTRE_STEM_KEY: ResourceKey<LevelStem> =
		ResourceKey.create(Registries.LEVEL_STEM, SPECTRE_RL)

	val SPECTRE_LEVEL_KEY: ResourceKey<Level> =
		ResourceKey.create(Registries.DIMENSION, SPECTRE_RL)

	val SPECTRE_DIM_TYPE_KEY: ResourceKey<DimensionType> =
		ResourceKey.create(Registries.DIMENSION_TYPE, SPECTRE_RL)

	fun bootstrapType(context: BootstrapContext<DimensionType>) {
		context.register(
			SPECTRE_DIM_TYPE_KEY,
			DimensionType(
				OptionalLong.of(12000L),
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
				SPECTRE_RL,
				1f,
				DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
			)
		)
	}

	fun bootstrapStem(context: BootstrapContext<LevelStem>) {
		val dimTypeRegistry = context.lookup(Registries.DIMENSION_TYPE)
		val biomeRegistry = context.lookup(Registries.BIOME)
		val structureSetRegistry = context.lookup(Registries.STRUCTURE_SET)
		val placedFeatureRegistry = context.lookup(Registries.PLACED_FEATURE)

		val flatLevelGenSettings =
			FlatLevelGeneratorSettings.getDefault(biomeRegistry, structureSetRegistry, placedFeatureRegistry)
				.withBiomeAndLayers(
					listOf(),
					Optional.empty(),
					biomeRegistry.get(Biomes.THE_VOID).get()
				)

		val flatLevelSource = FlatLevelSource(flatLevelGenSettings)

		val stem = LevelStem(dimTypeRegistry.getOrThrow(SPECTRE_DIM_TYPE_KEY), flatLevelSource)
		context.register(SPECTRE_STEM_KEY, stem)
	}

}