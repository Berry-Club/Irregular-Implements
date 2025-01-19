package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

typealias EitherFilter = Either<FilterEntry.SpecificItem, FilterEntry.ItemTag>

data class ItemFilterDataComponent(
    val entries: Map<Int, FilterEntry>,
    val isBlacklist: Boolean
) {

    constructor() : this(emptyMap(), false)

    fun test(testedStack: ItemStack): Boolean {
        val passes = this.entries.any { it.value.test(testedStack) }
        return passes != this.isBlacklist
    }

    fun canAddFilter(stackToAdd: ItemStack): Boolean {
        return this.entries.size < 9 && this.entries
            .none {
                val filterEntry = it.value

                filterEntry is FilterEntry.SpecificItem
                        && ItemStack.isSameItemSameComponents(filterEntry.stack, stackToAdd)
            }
    }

    companion object {

        val CODEC: Codec<ItemFilterDataComponent> =
            RecordCodecBuilder.create { instance ->
                instance.group(
                    Codec.unboundedMap(
                        Codec.STRING,
                        Codec.either(
                            FilterEntry.SpecificItem.CODEC,
                            FilterEntry.ItemTag.CODEC
                        )
                    )
                        .fieldOf("entries")
                        .forGetter(::fromComponent),
                    Codec.BOOL
                        .optionalFieldOf("is_blacklist", false)
                        .forGetter(ItemFilterDataComponent::isBlacklist)
                ).apply(instance, ::toComponent)
            }

        private fun fromComponent(
            component: ItemFilterDataComponent
        ): Map<String, EitherFilter> {
            val newMap: MutableMap<String, EitherFilter> = mutableMapOf()

            for ((int, filter) in component.entries) {
                val key = int.toString()

                val either: EitherFilter = when (filter) {
                    is FilterEntry.SpecificItem -> Either.left(filter)
                    is FilterEntry.ItemTag -> Either.right(filter)
                    else -> error("Invalid filter entry type: $filter")
                }

                newMap[key] = either
            }

            return newMap
        }

        private fun toComponent(
            entries: Map<String, EitherFilter>,
            isBlacklist: Boolean
        ): ItemFilterDataComponent {
            val newMap: MutableMap<Int, FilterEntry> = mutableMapOf()

            for ((stringKey, either) in entries) {
                val intKey = stringKey.toInt()

                val filter = either.map(
                    { left: FilterEntry.SpecificItem -> left },
                    { right: FilterEntry.ItemTag -> right }
                )

                newMap[intKey] = filter
            }

            return ItemFilterDataComponent(
                newMap,
                isBlacklist
            )
        }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemFilterDataComponent> =
            StreamCodec.composite(
                ByteBufCodecs.map(
                    ::HashMap,
                    ByteBufCodecs.STRING_UTF8,
                    ByteBufCodecs.either(
                        FilterEntry.SpecificItem.STREAM_CODEC,
                        FilterEntry.ItemTag.STREAM_CODEC
                    )
                ),
                ::fromComponent,
                ByteBufCodecs.BOOL,
                ItemFilterDataComponent::isBlacklist,
                ::toComponent
            )

    }

}