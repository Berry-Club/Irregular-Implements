package dev.aaronhowser.mods.irregular_implements.datagen.datapack.biome_modifiers

import com.mojang.serialization.MapCodec
import net.minecraft.core.Holder
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.levelgen.placement.RarityFilter
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo

data class WeightedBiomeModifier(
    val pointsPerBiomeTag: Map<TagKey<Biome>, Int>,
    val startChance: Int,
    val placedFeature: Holder<PlacedFeature>,
    val generationStep: GenerationStep.Decoration
) : BiomeModifier {

    companion object {
        val CODEC = MapCodec.of()
    }

    override fun modify(
        biome: Holder<Biome>,
        phase: BiomeModifier.Phase,
        builder: ModifiableBiomeInfo.BiomeInfo.Builder
    ) {
        if (phase != BiomeModifier.Phase.ADD) return

        var chanceMult = this.startChance

        for ((tag, points) in this.pointsPerBiomeTag) {
            if (biome.`is`(tag)) chanceMult += points
        }

        if (chanceMult <= 0) return

        val placement = RarityFilter.onAverageOnceEvery(18 * chanceMult)
        val generationSettings = builder.generationSettings

        generationSettings.addFeature(
            this.generationStep,
            this.placedFeature
        )

    }

    override fun codec(): MapCodec<WeightedBiomeModifier> {
        TODO("Not yet implemented")
    }
}