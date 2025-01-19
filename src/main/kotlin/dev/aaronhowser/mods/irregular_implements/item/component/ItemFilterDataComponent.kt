package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

data class ItemFilterDataComponent(
    val entries: NonNullList<FilterEntry>,
    val isBlacklist: Boolean
) {

    constructor(vararg filterEntry: FilterEntry) : this(sanitizeEntries(filterEntry), false)

    fun test(testedStack: ItemStack): Boolean {
        val passes = this.entries.any { it.test(testedStack) }
        return passes != this.isBlacklist
    }

    fun canAddFilter(stackToAdd: ItemStack): Boolean {
        return this.entries.size < 9 && this.entries
            .none {
                it is FilterEntry.SpecificItem
                        && ItemStack.isSameItemSameComponents(it.stack, stackToAdd)
            }
    }

    companion object {

        fun sanitizeEntries(entries: Array<out FilterEntry>): NonNullList<FilterEntry> {
            //FIXME: Crashes when I fill it out with Emptys for some reason??? Codec die??
            val sanitizedEntries = NonNullList.withSize<FilterEntry>(
                9,
                FilterEntry.SpecificItem(Items.OAK_PLANKS.defaultInstance, true)
            )

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
                        .forGetter(ItemFilterDataComponent::entries),
                    Codec.BOOL
                        .optionalFieldOf("is_blacklist", false)
                        .forGetter(ItemFilterDataComponent::isBlacklist)
                ).apply(instance, ::ItemFilterDataComponent)
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