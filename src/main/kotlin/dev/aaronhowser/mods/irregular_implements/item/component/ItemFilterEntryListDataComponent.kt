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
    val entries: List<Either<SpecificItemEntry, TagEntry>>
) {

    fun test(testedStack: ItemStack): Boolean {
        for (entry in this.entries) {
            var passes = false

            entry.ifLeft { specificItem ->
                passes = if (specificItem.requireSameComponents) {
                    ItemStack.isSameItemSameComponents(specificItem.stack, testedStack)
                } else {
                    ItemStack.isSameItem(specificItem.stack, testedStack)
                }
            }.ifRight { tag ->
                passes = testedStack.`is`(tag.tagKey)
            }

            if (passes) return true
        }

        return false
    }

    data class TagEntry(
        val tagKey: TagKey<Item>
    ) {

        val matchingItems = BuiltInRegistries.ITEM.getTag(tagKey).get().toList()

        companion object {
            val CODEC: Codec<TagEntry> =
                TagKey.codec(Registries.ITEM).xmap(::TagEntry, TagEntry::tagKey)

            val STREAM_CODEC: StreamCodec<ByteBuf, TagEntry> =
                OtherUtil.tagKeyStreamCodec(Registries.ITEM)
                    .map(::TagEntry, TagEntry::tagKey)
        }
    }

    data class SpecificItemEntry(
        val stack: ItemStack,
        val requireSameComponents: Boolean
    ) {
        companion object {
            val CODEC: Codec<SpecificItemEntry> =
                RecordCodecBuilder.create { instance ->
                    instance.group(
                        ItemStack.CODEC
                            .fieldOf("stack")
                            .forGetter(SpecificItemEntry::stack),
                        Codec.BOOL
                            .fieldOf("require_same_components")
                            .forGetter(SpecificItemEntry::requireSameComponents)
                    ).apply(instance, ::SpecificItemEntry)
                }

            val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, SpecificItemEntry> =
                StreamCodec.composite(
                    ItemStack.STREAM_CODEC, SpecificItemEntry::stack,
                    ByteBufCodecs.BOOL, SpecificItemEntry::requireSameComponents,
                    ::SpecificItemEntry
                )
        }
    }

    companion object {

        private val random = Random(123L)

        fun getDisplayStack(entry: Either<SpecificItemEntry, TagEntry>): ItemStack {
            var displayStack: ItemStack? = null

            entry.ifLeft { specificItem ->
                displayStack = specificItem.stack
            }

            entry.ifRight { tag ->
                val randomIndex = random.nextInt(tag.matchingItems.size)
                val randomItem = tag.matchingItems[randomIndex]

                displayStack = randomItem.value().defaultInstance
            }

            return displayStack ?: ItemStack.EMPTY
        }

        val CODEC: Codec<ItemFilterEntryListDataComponent> =
            Codec.either(SpecificItemEntry.CODEC, TagEntry.CODEC)
                .listOf()
                .xmap(::ItemFilterEntryListDataComponent, ItemFilterEntryListDataComponent::entries)

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemFilterEntryListDataComponent> =
            ByteBufCodecs.either(SpecificItemEntry.STREAM_CODEC, TagEntry.STREAM_CODEC)
                .apply(ByteBufCodecs.list())
                .map(::ItemFilterEntryListDataComponent, ItemFilterEntryListDataComponent::entries)
    }

}