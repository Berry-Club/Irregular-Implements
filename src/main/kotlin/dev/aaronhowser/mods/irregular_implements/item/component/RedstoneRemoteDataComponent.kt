package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

data class RedstoneRemoteDataComponent(
	val locationFilters: List<ItemStack>,
	val displayStacks: List<ItemStack>
) {

	constructor() : this(listOf(), listOf())

	fun getPair(index: Int): Pair<ItemStack, ItemStack> {
		val locationFilter = locationFilters.getOrNull(index) ?: ItemStack.EMPTY
		val displayStack = displayStacks.getOrNull(index) ?: ItemStack.EMPTY

		return Pair(locationFilter, displayStack)
	}

	companion object {
		val CODEC: Codec<RedstoneRemoteDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					ItemStack.CODEC
						.listOf()
						.optionalFieldOf("location_filters", listOf())
						.forGetter(RedstoneRemoteDataComponent::locationFilters),
					ItemStack.CODEC
						.listOf()
						.optionalFieldOf("display_stacks", listOf())
						.forGetter(RedstoneRemoteDataComponent::displayStacks)
				).apply(instance, ::RedstoneRemoteDataComponent)
			}

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, RedstoneRemoteDataComponent> =
			StreamCodec.composite(
				ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), RedstoneRemoteDataComponent::locationFilters,
				ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), RedstoneRemoteDataComponent::displayStacks,
				::RedstoneRemoteDataComponent
			)

	}

}