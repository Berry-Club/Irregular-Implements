package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.level.biome.Biome

data class BiomePointsDataComponent(
    val biome: Holder<Biome>,
    val points: Int
) {

    companion object {
        private val BIOME_STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, Holder<Biome>> =
            ByteBufCodecs.holderRegistry(Registries.BIOME)

        val CODEC: Codec<BiomePointsDataComponent> =
            RecordCodecBuilder.create { instance ->
                instance.group(
                    Biome.CODEC
                        .fieldOf("biome")
                        .forGetter(BiomePointsDataComponent::biome),
                    Codec.INT
                        .fieldOf("points")
                        .forGetter(BiomePointsDataComponent::points)
                ).apply(instance, ::BiomePointsDataComponent)
            }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, BiomePointsDataComponent> =
            StreamCodec.composite(
                BIOME_STREAM_CODEC, BiomePointsDataComponent::biome,
                ByteBufCodecs.VAR_INT, BiomePointsDataComponent::points,
                ::BiomePointsDataComponent
            )
    }

    fun withMorePoints(amount: Int): BiomePointsDataComponent {
        return copy(points = points + amount)
    }

    fun withLessPoints(amount: Int): BiomePointsDataComponent {
        return copy(points = points - amount)
    }

}