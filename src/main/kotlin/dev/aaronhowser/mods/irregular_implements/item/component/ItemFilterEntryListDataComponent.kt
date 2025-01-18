package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
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
            val tagKey: TagKey<Item>,
            val backupStack: ItemStack
        ) : FilterEntry {

            private val matchingItems = BuiltInRegistries.ITEM.getTag(tagKey).get().toList()

            private var timeLastUpdated = 0L
            private var displayStack: ItemStack? = null

            override fun getDisplayStack(): ItemStack {

                val time = System.currentTimeMillis() / 1000
                if (displayStack == null || time > this.timeLastUpdated) {
                    this.timeLastUpdated = time

                    val randomIndex = random.nextInt(this.matchingItems.size)
                    val randomItem = this.matchingItems[randomIndex]

                    this.displayStack = randomItem.value().defaultInstance.apply {
                        set(
                            DataComponents.ITEM_NAME,
                            Component.literal("Item Tag: ${tagKey.location}")
                        )
                    }
                }

                return this.displayStack ?: ItemStack.EMPTY
            }

            override fun test(stack: ItemStack): Boolean {
                return stack.`is`(tagKey)
            }

            fun getAsSpecificItemEntry(): SpecificItem {
                return SpecificItem(
                    this.backupStack,
                    requireSameComponents = false
                )
            }

            companion object {
                private val random = Random(123L)

                val CODEC: Codec<ItemTag> =
                    RecordCodecBuilder.create { instance ->
                        instance.group(
                            TagKey.codec(Registries.ITEM)
                                .fieldOf("tag_key")
                                .forGetter(ItemTag::tagKey),
                            ItemStack.CODEC
                                .fieldOf("backup_stack")
                                .forGetter(ItemTag::backupStack)
                        ).apply(instance, ::ItemTag)
                    }

                val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemTag> =
                    StreamCodec.composite(
                        OtherUtil.tagKeyStreamCodec(Registries.ITEM), ItemTag::tagKey,
                        ItemStack.STREAM_CODEC, ItemTag::backupStack,
                        ::ItemTag
                    )
            }
        }

        data class SpecificItem(
            val stack: ItemStack,
            val requireSameComponents: Boolean
        ) : FilterEntry {

            override fun getDisplayStack(): ItemStack {
                return this.stack.copy()
            }

            override fun test(stack: ItemStack): Boolean {
                return if (this.requireSameComponents) {
                    ItemStack.isSameItemSameComponents(this.stack, stack)
                } else {
                    ItemStack.isSameItem(this.stack, stack)
                }
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is SpecificItem) return false

                if (!ItemStack.isSameItemSameComponents(this.stack, other.stack)) return false
                if (this.requireSameComponents != other.requireSameComponents) return false

                return true
            }

            override fun hashCode(): Int {
                var result = this.stack.hashCode()
                result = 31 * result + this.requireSameComponents.hashCode()
                return result
            }

            companion object {
                val CODEC: Codec<SpecificItem> =
                    RecordCodecBuilder.create { instance ->
                        instance.group(
                            ItemStack.CODEC
                                .fieldOf("stack")
                                .forGetter(SpecificItem::stack),
                            Codec.BOOL
                                .optionalFieldOf("require_same_components", false)
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