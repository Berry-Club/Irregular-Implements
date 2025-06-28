package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

data class ItemFilterDataComponent(
	val entries: NonNullList<FilterEntry>,
	val isBlacklist: Boolean
) {

	constructor() : this(listOf(), false)
	constructor(filterEntries: List<FilterEntry>, isBlacklist: Boolean = false) : this(sanitizeEntries(filterEntries), isBlacklist)

	fun test(testedStack: ItemStack): Boolean {
		val passes = this.entries.any { it.test(testedStack) }
		return passes != this.isBlacklist
	}

	companion object {

		fun sanitizeEntries(entries: List<FilterEntry>): NonNullList<FilterEntry> {
			val sanitizedEntries = NonNullList.withSize<FilterEntry>(9, FilterEntry.Empty)

			for (index in 0 until 9) {
				val entry = entries.getOrNull(index) ?: continue
				sanitizedEntries[index] = entry
			}

			return sanitizedEntries
		}

		val CODEC: Codec<ItemFilterDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					NonNullList.codecOf(FilterEntry.CODEC)
						.fieldOf("entries")
						.forGetter(::trimNonNullList),
					Codec.BOOL
						.optionalFieldOf("is_blacklist", false)
						.forGetter(ItemFilterDataComponent::isBlacklist)
				).apply(instance, ::ItemFilterDataComponent)
			}

		private fun trimNonNullList(itemFilterDataComponent: ItemFilterDataComponent): NonNullList<FilterEntry> {
			val array = itemFilterDataComponent.entries.toTypedArray()
			val lastNonEmpty = array.indexOfLast { it !is FilterEntry.Empty }

			val trimmedArray = array.take(lastNonEmpty + 1).toTypedArray()

			return NonNullList.of(FilterEntry.Empty, *trimmedArray)
		}

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemFilterDataComponent> =
			StreamCodec.composite(
				ByteBufCodecs.fromCodec(NonNullList.codecOf(FilterEntry.CODEC)),    //TODO: Stop using fromCodec
				ItemFilterDataComponent::entries,
				ByteBufCodecs.BOOL,
				ItemFilterDataComponent::isBlacklist,
				::ItemFilterDataComponent
			)

	}

}