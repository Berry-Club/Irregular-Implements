package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getComponent
import net.minecraft.ChatFormatting
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.tags.TagKey
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemLore
import kotlin.jvm.optionals.getOrNull
import kotlin.random.Random

sealed interface FilterEntry {

	fun getDisplayStack(registries: HolderLookup.Provider): ItemStack
	fun test(stack: ItemStack): Boolean
	fun getType(): Type

	companion object {
		val CODEC: Codec<FilterEntry> = Type.CODEC.dispatch(
			FilterEntry::getType,
			Type::codec
		)

		fun FilterEntry?.isNullOrEmpty() = this == null || this == Empty
	}

	enum class Type(
		val id: String,
		val codec: MapCodec<out FilterEntry>
	) : StringRepresentable {
		EMPTY("empty", Empty.CODEC),
		TAG("tag", Tag.CODEC),
		ITEM("item", Item.CODEC);

		override fun getSerializedName(): String {
			return this.id
		}

		companion object {
			val CODEC: Codec<Type> = StringRepresentable.fromEnum(Type::values)
		}
	}

	data object Empty : FilterEntry {
		override fun getDisplayStack(registries: HolderLookup.Provider): ItemStack {
			return ItemStack.EMPTY
		}

		override fun test(stack: ItemStack): Boolean {
			return false
		}

		override fun getType(): Type {
			return Type.EMPTY
		}

		val CODEC: MapCodec<Empty> = MapCodec.unit(Empty)
	}

	data class Tag(
		val tagKey: TagKey<net.minecraft.world.item.Item>,
		val backupStack: ItemStack
	) : FilterEntry {

		override fun getType(): Type {
			return Type.TAG
		}

		private val displayStacks: MutableMap<net.minecraft.world.item.Item, ItemStack> = mutableMapOf()
		private var displayStack: ItemStack? = null

		private var timeLastUpdated = 0L

		override fun getDisplayStack(registries: HolderLookup.Provider): ItemStack {
			val time = System.currentTimeMillis() / 1000
			if (displayStack == null || time > this.timeLastUpdated) {
				this.timeLastUpdated = time

				val matchingItems = registries.lookupOrThrow(Registries.ITEM)
					.get(this.tagKey)
					.getOrNull()
					?.toList()
					?: return ItemStack.EMPTY

				val randomIndex = random.nextInt(matchingItems.size)
				val randomItem = matchingItems[randomIndex].value()

				this.displayStack = this.displayStacks.computeIfAbsent(randomItem) {
					val tagKeyComponent = this.tagKey.getComponent()

					val stack = randomItem.defaultInstance
					stack.set(
						DataComponents.ITEM_NAME,
						ModTooltipLang.ITEM_TAG
							.toComponent(tagKeyComponent)
					)

					stack
				}
			}

			return this.displayStack ?: ItemStack.EMPTY
		}

		fun getNextTag(): TagKey<net.minecraft.world.item.Item> {
			val stackTags = this.backupStack.tags.toList()
			val currentTagIndex = stackTags.indexOf(this.tagKey)

			val nextTagIndex = (currentTagIndex + 1) % stackTags.size
			return stackTags[nextTagIndex]
		}

		override fun test(stack: ItemStack): Boolean {
			return stack.`is`(this.tagKey)
		}

		fun getAsSpecificItemEntry(): Item {
			return Item(
				backupStack,
				requireSameComponents = false
			)
		}

		companion object {
			private val random = Random(123L)

			val CODEC: MapCodec<Tag> =
				RecordCodecBuilder.mapCodec { instance ->
					instance.group(
						TagKey.codec(Registries.ITEM)
							.fieldOf("tag")
							.forGetter(Tag::tagKey),
						ItemStack.CODEC
							.fieldOf("backup_stack")
							.forGetter(Tag::backupStack)
					).apply(instance, ::Tag)
				}

			val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, Tag> =
				StreamCodec.composite(
					OtherUtil.tagKeyStreamCodec(Registries.ITEM), Tag::tagKey,
					ItemStack.STREAM_CODEC, Tag::backupStack,
					::Tag
				)
		}
	}

	data class Item(
		val stack: ItemStack,
		val requireSameComponents: Boolean
	) : FilterEntry {

		override fun getType(): Type {
			return Type.ITEM
		}

		private val displayStack: ItemStack = this.stack.copy()

		init {
			if (this.requireSameComponents) {
				val component = ModTooltipLang.ITEM_FILTER_REQUIRES_SAME_COMPONENTS
					.toComponent().withStyle(ChatFormatting.RED)

				this.displayStack.set(
					DataComponents.LORE,
					ItemLore(listOf(component))
				)
			}
		}

		override fun getDisplayStack(registries: HolderLookup.Provider): ItemStack {
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
			if (other !is Item) return false

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
			val CODEC: MapCodec<Item> =
				RecordCodecBuilder.mapCodec { instance ->
					instance.group(
						ItemStack.CODEC
							.fieldOf("stack")
							.forGetter(Item::stack),
						Codec.BOOL
							.optionalFieldOf("require_same_components", false)
							.forGetter(Item::requireSameComponents)
					).apply(instance, ::Item)
				}

			val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, Item> =
				StreamCodec.composite(
					ItemStack.STREAM_CODEC, Item::stack,
					ByteBufCodecs.BOOL, Item::requireSameComponents,
					::Item
				)
		}
	}
}