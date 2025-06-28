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

	fun getPair(index: Int): Pair<ItemStack, ItemStack> {
		val locationFilter = locationFilters.getOrNull(index) ?: ItemStack.EMPTY
		val displayStack = displayStacks.getOrNull(index) ?: ItemStack.EMPTY

		return Pair(locationFilter, displayStack)
	}

	companion object {

		fun sanitizeList(entries: List<ItemStack>): NonNullList<ItemStack> {
			val sanitizedEntries = NonNullList.withSize(9, ItemStack.EMPTY)

			for (index in 0 until 9) {
				val entry = entries.getOrNull(index) ?: continue
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