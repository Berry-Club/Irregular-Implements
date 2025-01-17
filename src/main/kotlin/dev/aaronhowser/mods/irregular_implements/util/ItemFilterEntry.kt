package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

sealed interface ItemFilterEntry {

    fun test(stack: ItemStack): Boolean

    val codec: Codec<out ItemFilterEntry>

    data class Tag(
        val tagKey: TagKey<Item>
    ) : ItemFilterEntry {
        override fun test(stack: ItemStack): Boolean {
            return stack.`is`(tagKey)
        }

        override val codec: Codec<out ItemFilterEntry> = CODEC

        companion object {
            val CODEC: Codec<Tag> =
                TagKey.codec(Registries.ITEM).xmap(::Tag, Tag::tagKey)
        }
    }

    data class Stack(
        val stack: ItemStack,
        val requireSameComponents: Boolean
    ) : ItemFilterEntry {

        override fun test(stack: ItemStack): Boolean {
            if (this.requireSameComponents) {
                return ItemStack.isSameItemSameComponents(this.stack, stack)
            }

            return ItemStack.isSameItem(this.stack, stack)
        }

        override val codec: Codec<out ItemFilterEntry> = CODEC

        fun toggleRequireSameComponents(): Stack {
            return copy(requireSameComponents = !this.requireSameComponents)
        }

        companion object {
            val CODEC: Codec<Stack> =
                RecordCodecBuilder.create { instance ->
                    instance.group(
                        ItemStack.CODEC.fieldOf("stack").forGetter(Stack::stack),
                        Codec.BOOL.fieldOf("require_same_components").forGetter(Stack::requireSameComponents)
                    ).apply(instance, ::Stack)
                }
        }
    }

}