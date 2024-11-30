package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level

data class LocationItemComponent(
    val dimension: ResourceKey<Level>,
    val blockPos: BlockPos,
    val blockName: Component
) {

    constructor(level: Level, blockPos: BlockPos) : this(level.dimension(), blockPos, level.getBlockState(blockPos).block.name)

    companion object {
        val CODEC: Codec<LocationItemComponent> =
            RecordCodecBuilder.create { instance ->
                instance.group(
                    ResourceKey.codec(Registries.DIMENSION)
                        .fieldOf("dimension")
                        .forGetter(LocationItemComponent::dimension),
                    BlockPos.CODEC
                        .fieldOf("block_pos")
                        .forGetter(LocationItemComponent::blockPos),
                    ComponentSerialization.CODEC
                        .fieldOf("block_name")
                        .forGetter(LocationItemComponent::blockName)
                ).apply(instance, ::LocationItemComponent)
            }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, LocationItemComponent> =
            StreamCodec.composite(
                ResourceKey.streamCodec(Registries.DIMENSION), LocationItemComponent::dimension,
                BlockPos.STREAM_CODEC, LocationItemComponent::blockPos,
                ComponentSerialization.STREAM_CODEC, LocationItemComponent::blockName,
                ::LocationItemComponent
            )
    }

}