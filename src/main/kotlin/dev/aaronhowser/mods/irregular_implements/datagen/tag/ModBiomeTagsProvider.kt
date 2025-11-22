package dev.aaronhowser.mods.irregular_implements.datagen.tag

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.BiomeTagsProvider
import net.minecraft.tags.BiomeTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Biomes
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModBiomeTagsProvider(
	pOutput: PackOutput,
	pProvider: CompletableFuture<HolderLookup.Provider>,
	existingFileHelper: ExistingFileHelper
) : BiomeTagsProvider(pOutput, pProvider, IrregularImplements.MOD_ID, existingFileHelper) {

	override fun addTags(provider: HolderLookup.Provider) {
		tag(HAS_NATURE_CORE)
			.addTags(
				NATURE_CORE_OAK,
				NATURE_CORE_DARK_OAK,
				NATURE_CORE_BIRCH,
				NATURE_CORE_JUNGLE,
				NATURE_CORE_ACACIA,
				NATURE_CORE_SPRUCE,
				NATURE_CORE_MANGROVE,
				BiomeTags.IS_FOREST,
				BiomeTags.IS_HILL,
				BiomeTags.IS_MOUNTAIN,
				Tags.Biomes.IS_PLAINS,
				Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD,
				Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD,
			)
			.remove(BiomeTags.IS_OCEAN)
			.remove(Biomes.ICE_SPIKES)

		tag(BIOME_CRYSTAL_BLACKLIST)    // Empty by default (blacklist is empty by default)
		tag(NATURE_CORE_OAK)
			.add(
				Biomes.FOREST,
				Biomes.FLOWER_FOREST
			)

		tag(NATURE_CORE_DARK_OAK)
			.add(
				Biomes.DARK_FOREST
			)

		tag(NATURE_CORE_BIRCH)
			.add(
				Biomes.BIRCH_FOREST,
				Biomes.OLD_GROWTH_BIRCH_FOREST
			)

		tag(NATURE_CORE_JUNGLE)
			.addTag(BiomeTags.IS_JUNGLE)

		tag(NATURE_CORE_SPRUCE)
			.addTag(Tags.Biomes.IS_COLD_OVERWORLD)

		tag(NATURE_CORE_ACACIA)
			.addTag(BiomeTags.IS_SAVANNA)

		tag(NATURE_CORE_MANGROVE)
			.add(
				Biomes.MANGROVE_SWAMP
			)

		tag(SPAWNS_LOTUS_BUSH)
			.addTag(Tags.Biomes.IS_COLD_OVERWORLD)

		tag(SPAWNS_PITCHER_PLANT)
			.addTag(Tags.Biomes.IS_WET_OVERWORLD)

		tag(SPAWNS_GLOWING_MUSHROOM)
			.addTag(Tags.Biomes.IS_OVERWORLD)

		tag(SPAWNS_BEAN_SPROUT)
			.addTag(Tags.Biomes.IS_OVERWORLD)
			.remove(
				Tags.Biomes.IS_COLD,
				Tags.Biomes.IS_DRY,
				Tags.Biomes.IS_SPARSE_VEGETATION
			)
	}

	companion object {
		private fun create(name: String): TagKey<Biome> {
			return TagKey.create(Registries.BIOME, OtherUtil.modResource(name))
		}

		val BIOME_CRYSTAL_BLACKLIST = create("biome_crystal_blacklist")
		val HAS_NATURE_CORE = create("has_nature_core")
		val NATURE_CORE_OAK = create("nature_core_oak")
		val NATURE_CORE_DARK_OAK = create("nature_core_dark_oak")
		val NATURE_CORE_BIRCH = create("nature_core_birch")
		val NATURE_CORE_JUNGLE = create("nature_core_jungle")
		val NATURE_CORE_ACACIA = create("nature_core_acacia")
		val NATURE_CORE_SPRUCE = create("nature_core_spruce")
		val NATURE_CORE_MANGROVE = create("nature_core_mangrove")

		val SPAWNS_LOTUS_BUSH = create("spawns_lotus_bush")
		val SPAWNS_PITCHER_PLANT = create("spawns_pitcher_plant")
		val SPAWNS_GLOWING_MUSHROOM = create("spawns_glowing_mushroom")
		val SPAWNS_BEAN_SPROUT = create("spawns_bean_sprout")

	}

}