package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.dimension.DimensionType

data class LocationItemComponent(
    val dimension: ResourceKey<DimensionType>,
    val blockPos: BlockPos
) {

    companion object {
        val CODEC: Codec<LocationItemComponent> =
            RecordCodecBuilder.create { instance ->
                instance.group(
                    ResourceKey.codec(Registries.DIMENSION_TYPE)
                        .fieldOf("dimension")
                        .forGetter(LocationItemComponent::dimension),
                    BlockPos.CODEC
                        .fieldOf("block_pos")
                        .forGetter(LocationItemComponent::blockPos)
                ).apply(instance, ::LocationItemComponent)
            }

        val STREAM_CODEC: StreamCodec<ByteBuf, LocationItemComponent> =
            StreamCodec.composite(
                ResourceKey.streamCodec(Registries.DIMENSION_TYPE), LocationItemComponent::dimension,
                BlockPos.STREAM_CODEC, LocationItemComponent::blockPos,
                ::LocationItemComponent
            )
    }

}