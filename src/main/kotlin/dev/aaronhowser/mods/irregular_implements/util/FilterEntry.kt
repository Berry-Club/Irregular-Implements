package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import net.minecraft.client.resources.language.I18n
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.tags.TagKey
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemLore
import kotlin.random.Random

sealed interface FilterEntry {

    enum class Type(val codec: MapCodec<out FilterEntry>) : StringRepresentable {
        EMPTY(Empty.CODEC),
        ITEM_TAG(ItemTag.CODEC),
        SPECIFIC_ITEM(SpecificItem.CODEC);

        override fun getSerializedName(): String {
            return this.name.lowercase()
        }

        companion object {
            val CODEC: StringRepresentable.EnumCodec<Type> = StringRepresentable.fromEnum { Type.entries.toTypedArray() }
        }
    }

    companion object {
        val CODEC = Type.CODEC.dispatch(FilterEntry::type, Type::codec)
    }

    fun getDisplayStack(): ItemStack
    fun test(stack: ItemStack): Boolean
    val type: Type

    data object Empty : FilterEntry {
        override fun getDisplayStack(): ItemStack {
            return ItemStack.EMPTY
        }

        override fun test(stack: ItemStack): Boolean {
            return false
        }

        override val type: Type = Type.EMPTY

        val CODEC: MapCodec<Empty> = MapCodec.unit(Empty)
    }

    data class ItemTag(
        val tagKey: TagKey<Item>,
        val backupStack: ItemStack
    ) : FilterEntry {

        private val matchingItems = BuiltInRegistries.ITEM.getTag(this.tagKey).get().toList()

        private var timeLastUpdated = 0L
        private var displayStack: ItemStack? = null

        override val type: Type = Type.ITEM_TAG

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

            val CODEC: MapCodec<ItemTag> =
                RecordCodecBuilder.mapCodec { instance ->
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

        override val type: Type = Type.SPECIFIC_ITEM

        private val displayStack: ItemStack = this.stack.copy()

        init {
            if (this.requireSameComponents) {
                this.displayStack.set(
                    DataComponents.LORE,
                    ItemLore(listOf(Component.literal("Requires same components")))
                )
            }
        }

        override fun getDisplayStack(): ItemStack {
            return this.displayStack
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
            val CODEC: MapCodec<SpecificItem> =
                RecordCodecBuilder.mapCodec { instance ->
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