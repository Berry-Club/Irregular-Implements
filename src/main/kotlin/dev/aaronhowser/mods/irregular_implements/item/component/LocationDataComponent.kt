package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level

data class LocationDataComponent(
	val dimension: ResourceKey<Level>,
	val blockPos: BlockPos
) {

	constructor(level: Level, blockPos: BlockPos) : this(level.dimension(), blockPos)

	companion object {
		val CODEC: Codec<LocationDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					ResourceKey.codec(Registries.DIMENSION)
						.fieldOf("dimension")
						.forGetter(LocationDataComponent::dimension),
					BlockPos.CODEC
						.fieldOf("block_pos")
						.forGetter(LocationDataComponent::blockPos)
				).apply(instance, ::LocationDataComponent)
			}

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, LocationDataComponent> =
			StreamCodec.composite(
				ResourceKey.streamCodec(Registries.DIMENSION), LocationDataComponent::dimension,
				BlockPos.STREAM_CODEC, LocationDataComponent::blockPos,
				::LocationDataComponent
			)
	}

}