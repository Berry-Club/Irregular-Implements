package dev.aaronhowser.mods.irregular_implements.datagen.datapack.placement_filters

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import dev.aaronhowser.mods.irregular_implements.registry.ModPlacementModifierTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.placement.PlacementContext
import net.minecraft.world.level.levelgen.placement.PlacementFilter
import net.minecraft.world.level.levelgen.placement.PlacementModifierType

data class WeightedBiomeRarityFilter(
    val pointsPerBiomeTag: Map<TagKey<Biome>, Int>,
) : PlacementFilter() {

    companion object {
        val CODEC: MapCodec<WeightedBiomeRarityFilter> =
            Codec.unboundedMap(
                TagKey.codec(Registries.BIOME),
                Codec.INT
            )
                .fieldOf("points_per_biome_tag")
                .xmap(::WeightedBiomeRarityFilter, WeightedBiomeRarityFilter::pointsPerBiomeTag)
    }

    override fun type(): PlacementModifierType<*> {
        return ModPlacementModifierTypes.WEIGHTED_BIOME_RARITY.get()
    }

    override fun shouldPlace(context: PlacementContext, random: RandomSource, pos: BlockPos): Boolean {
        TODO("Not yet implemented")
    }
}