package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

data class ItemFilterEntryListDataComponent(
    val entries: List<Either<SpecificItem, TagKey<Item>>>
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
            }.ifRight { tagKey ->
                passes = testedStack.`is`(tagKey)
            }

            if (passes) return true
        }

        return false
    }

    data class SpecificItem(
        val stack: ItemStack,
        val requireSameComponents: Boolean
    ) {
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

    companion object {
        val CODEC: Codec<ItemFilterEntryListDataComponent> =
            Codec.either(SpecificItem.CODEC, TagKey.codec(Registries.ITEM))
                .listOf()
                .xmap(::ItemFilterEntryListDataComponent, ItemFilterEntryListDataComponent::entries)

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemFilterEntryListDataComponent> =
            ByteBufCodecs.either(SpecificItem.STREAM_CODEC, OtherUtil.tagKeyStreamCodec(Registries.ITEM))
                .apply(ByteBufCodecs.list())
                .map(::ItemFilterEntryListDataComponent, ItemFilterEntryListDataComponent::entries)
    }

}