package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import net.minecraft.advancements.critereon.ItemPredicate

data class ItemFilterEntryListDataComponent(
    val entries: List<ItemPredicate>
) {

    companion object {
        val CODEC: Codec<ItemFilterEntryListDataComponent> =
            ItemPredicate.CODEC
                .listOf()
                .xmap(::ItemFilterEntryListDataComponent, ItemFilterEntryListDataComponent::entries)
    }

}