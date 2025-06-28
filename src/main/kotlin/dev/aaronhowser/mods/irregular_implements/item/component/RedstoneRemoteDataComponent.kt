package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

data class RedstoneRemoteDataComponent(
	val locationFilters: NonNullList<ItemStack>,
	val displayStacks: NonNullList<ItemStack>
) {

	constructor() : this(emptyMap())

	constructor(map: Map<ItemStack, ItemStack>) : this(
		locationFilters = sanitizeList(map.keys.toList()),
		displayStacks = sanitizeList(map.values.toList())
	)

	fun getPair(index: Int): Pair<ItemStack, ItemStack> {
		return Pair(getLocation(index), getDisplay(index))
	}

	fun getLocation(index: Int): ItemStack {
		return locationFilters.getOrNull(index) ?: ItemStack.EMPTY
	}

	fun getDisplay(index: Int): ItemStack {
		return displayStacks.getOrNull(index) ?: ItemStack.EMPTY
	}

	companion object {

		fun sanitizeList(list: List<ItemStack>): NonNullList<ItemStack> {
			val sanitizedEntries = NonNullList.withSize(9, ItemStack.EMPTY)

			for (index in 0 until 9) {
				val entry = list.getOrNull(index) ?: continue
				sanitizedEntries[index] = entry
			}

			return sanitizedEntries
		}

		val CODEC: Codec<RedstoneRemoteDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					NonNullList.codecOf(ItemStack.CODEC)
						.fieldOf("location_filters")
						.forGetter(::trimLocationFilters),
					NonNullList.codecOf(ItemStack.CODEC)
						.fieldOf("display_stacks")
						.forGetter(::trimDisplayStacks)
				).apply(instance, ::RedstoneRemoteDataComponent)
			}

		private fun trimLocationFilters(
			redstoneRemoteDataComponent: RedstoneRemoteDataComponent
		): NonNullList<ItemStack> {
			val array = redstoneRemoteDataComponent.locationFilters.toTypedArray()
			val lastNonEmpty = array.indexOfLast { !it.isEmpty }

			val trimmedArray = array.take(lastNonEmpty + 1).toTypedArray()

			return NonNullList.of(ItemStack.EMPTY, *trimmedArray)
		}

		private fun trimDisplayStacks(
			redstoneRemoteDataComponent: RedstoneRemoteDataComponent
		): NonNullList<ItemStack> {
			val array = redstoneRemoteDataComponent.displayStacks.toTypedArray()
			val lastNonEmpty = array.indexOfLast { !it.isEmpty }

			val trimmedArray = array.take(lastNonEmpty + 1).toTypedArray()

			return NonNullList.of(ItemStack.EMPTY, *trimmedArray)
		}

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, RedstoneRemoteDataComponent> =
			StreamCodec.composite(
				ByteBufCodecs.fromCodec(NonNullList.codecOf(ItemStack.CODEC)), RedstoneRemoteDataComponent::locationFilters,
				ByteBufCodecs.fromCodec(NonNullList.codecOf(ItemStack.CODEC)), RedstoneRemoteDataComponent::displayStacks,
				::RedstoneRemoteDataComponent
			)

	}

}