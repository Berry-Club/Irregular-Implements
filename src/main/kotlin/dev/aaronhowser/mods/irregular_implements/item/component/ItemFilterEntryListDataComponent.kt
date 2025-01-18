package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import kotlin.random.Random

data class ItemFilterEntryListDataComponent(
    val entries: Set<FilterEntry>
) {

    constructor(vararg entries: FilterEntry) : this(entries.toSet())

    fun test(testedStack: ItemStack): Boolean {
        return this.entries.any { filter ->
            filter.test(testedStack)
        }
    }

    sealed interface FilterEntry {

        fun getDisplayStack(): ItemStack
        fun test(stack: ItemStack): Boolean

        data class ItemTag(
            val tagKey: TagKey<Item>
        ) : FilterEntry {

            val matchingItems = BuiltInRegistries.ITEM.getTag(tagKey).get().toList()

            override fun getDisplayStack(): ItemStack {
                val randomIndex = random.nextInt(matchingItems.size)
                val randomItem = matchingItems[randomIndex]

                return randomItem.value().defaultInstance
            }

            override fun test(stack: ItemStack): Boolean {
                return stack.`is`(tagKey)
            }

            companion object {
                private val random = Random(123L)

                val CODEC: Codec<ItemTag> =
                    TagKey.codec(Registries.ITEM).xmap(::ItemTag, ItemTag::tagKey)

                val STREAM_CODEC: StreamCodec<ByteBuf, ItemTag> =
                    OtherUtil.tagKeyStreamCodec(Registries.ITEM)
                        .map(::ItemTag, ItemTag::tagKey)
            }
        }

        data class SpecificItem(
            val stack: ItemStack,
            val requireSameComponents: Boolean
        ) : FilterEntry {

            override fun getDisplayStack(): ItemStack {
                return stack.copy()
            }

            override fun test(stack: ItemStack): Boolean {
                return if (this.requireSameComponents) {
                    ItemStack.isSameItemSameComponents(this.stack, stack)
                } else {
                    ItemStack.isSameItem(this.stack, stack)
                }
            }

            companion object {
                val CODEC: Codec<SpecificItem> =
                    RecordCodecBuilder.create { instance ->
                        instance.group(
                            ItemStack.CODEC
                                .fieldOf("stack")
                                .forGetter(SpecificItem::stack),
                            Codec.BOOL
                                .fieldOf("require_same_components")
                                .forGetter(SpecificItem::requireSameComponents)
                        ).apply(instance, ::SpecificItem)
                    }

                val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, SpecificItem> =
                    StreamCodec.composite(
                        ItemStack.STREAM_CODEC, SpecificItem::stack,
                        ByteBufCodecs.BOOL, SpecificItem::requireSameComponents,
                        ::SpecificItem
                    )
            }
        }
    }

    companion object {
        val CODEC: Codec<ItemFilterEntryListDataComponent> =
            Codec.either(FilterEntry.SpecificItem.CODEC, FilterEntry.ItemTag.CODEC)
                .listOf()
                .xmap(::fromList, this::toList)

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemFilterEntryListDataComponent> =
            ByteBufCodecs.either(FilterEntry.SpecificItem.STREAM_CODEC, FilterEntry.ItemTag.STREAM_CODEC)
                .apply(ByteBufCodecs.list())
                .map(::fromList, this::toList)

        private fun fromList(list: List<Either<FilterEntry.SpecificItem, FilterEntry.ItemTag>>): ItemFilterEntryListDataComponent {
            return ItemFilterEntryListDataComponent(
                list
                    .map { either ->
                        either.map(
                            { left: FilterEntry.SpecificItem -> left },
                            { right: FilterEntry.ItemTag -> right }
                        )
                    }
                    .toSet()
            )
        }

        private fun toList(component: ItemFilterEntryListDataComponent): List<Either<FilterEntry.SpecificItem, FilterEntry.ItemTag>> {
            return component.entries.map { entry ->
                when (entry) {
                    is FilterEntry.SpecificItem -> Either.left(entry)
                    is FilterEntry.ItemTag -> Either.right(entry)
                }
            }
        }
    }

}