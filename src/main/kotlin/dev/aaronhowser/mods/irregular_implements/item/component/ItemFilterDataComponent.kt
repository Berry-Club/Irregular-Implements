package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.resources.language.I18n
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
import net.minecraft.world.item.component.ItemLore
import kotlin.random.Random

data class ItemFilterDataComponent(
    val entries: Map<Int, FilterEntry>,
    val isBlacklist: Boolean
) {

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

    sealed interface FilterEntry {

        fun getDisplayStack(): ItemStack
        fun test(stack: ItemStack): Boolean

        data class ItemTag(
            val tagKey: TagKey<Item>,
            val backupStack: ItemStack
        ) : FilterEntry {

            private val matchingItems = BuiltInRegistries.ITEM.getTag(this.tagKey).get().toList()

            private var timeLastUpdated = 0L
            private var displayStack: ItemStack? = null

            override fun getDisplayStack(): ItemStack {

                val time = System.currentTimeMillis() / 1000
                if (displayStack == null || time > this.timeLastUpdated) {
                    this.timeLastUpdated = time

                    val randomIndex = random.nextInt(this.matchingItems.size)
                    val randomItem = this.matchingItems[randomIndex].value()

                    val tagLocation = this.tagKey.location
                    val possibleLangKey = StringBuilder()
                        .append("tag.item.")
                        .append(tagLocation.namespace)
                        .append(".")
                        .append(tagLocation.path)
                        .toString()

                    val tagKeyComponent = if (I18n.exists(possibleLangKey)) {
                        Component.translatable(possibleLangKey)
                    } else {
                        Component.literal(tagLocation.toString())
                    }

                    this.displayStack = randomItem.defaultInstance.apply {
                        set(
                            DataComponents.ITEM_NAME,
                            ModLanguageProvider.Tooltips.ITEM_TAG
                                .toComponent(tagKeyComponent)
                        )
                    }
                }

                return this.displayStack ?: ItemStack.EMPTY
            }

            override fun test(stack: ItemStack): Boolean {
                return stack.`is`(this.tagKey)
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
                val displayStack = this.stack.copy()

                if (this.requireSameComponents) {
                    displayStack.set(
                        DataComponents.LORE,
                        ItemLore(listOf(Component.literal("Requires same components")))
                    )
                }

                return displayStack
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

        val CODEC: Codec<ItemFilterDataComponent> =
            RecordCodecBuilder.create { instance ->
                instance.group(
                    Codec.unboundedMap(
                        Codec.INT,
                        Codec.either(
                            FilterEntry.SpecificItem.CODEC,
                            FilterEntry.ItemTag.CODEC
                        )
                    )
                        .fieldOf("entries")
                        .forGetter { component ->
                            component.entries.mapValues { (_, entry) ->
                                when (entry) {
                                    is FilterEntry.SpecificItem -> Either.left(entry)
                                    is FilterEntry.ItemTag -> Either.right(entry)
                                }
                            }
                        },
                    Codec.BOOL
                        .optionalFieldOf("is_blacklist", false)
                        .forGetter(ItemFilterDataComponent::isBlacklist)
                ).apply(instance, ::toComponent)
            }

        private fun toComponent(entries: Map<Int, Either<FilterEntry.SpecificItem, FilterEntry.ItemTag>>, isBlacklist: Boolean): ItemFilterDataComponent {
            val newMap: Map<Int, FilterEntry> = entries.mapValues { (_, entry) ->
                when (entry.left().isPresent) {
                    true -> entry.left().get()
                    false -> entry.right().get()
                }
            }

            return ItemFilterDataComponent(
                newMap,
                isBlacklist
            )
        }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemFilterDataComponent> =
            StreamCodec.composite(
                ByteBufCodecs.map(
                    ByteBufCodecs.VAR_INT,
                    ByteBufCodecs.either(
                        FilterEntry.SpecificItem.STREAM_CODEC,
                        FilterEntry.ItemTag.STREAM_CODEC
                    )
                ),
                ItemFilterDataComponent::entries,
                ByteBufCodecs.BOOL,
                ItemFilterDataComponent::isBlacklist,
                ::ItemFilterDataComponent
            )


    }

}