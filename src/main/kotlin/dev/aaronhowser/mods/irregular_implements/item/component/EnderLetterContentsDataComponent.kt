package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.NonNullList
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.ItemStackHandler
import java.util.*

data class EnderLetterContentsDataComponent(
	val stacks: NonNullList<ItemStack>,
	val sender: Optional<String>,
	val recipient: Optional<String>
) : ItemInventoryItemHandler.InventoryDataComponent {

	constructor() : this(
		NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY),
		Optional.empty(),
		Optional.empty()
	)

	val hasBeenReceived = sender.isPresent

	override fun getInventory(): NonNullList<ItemStack> = stacks

	override fun setInventory(stack: ItemStack, inventory: NonNullList<ItemStack>) {
		val newComponent = this.copy(stacks = inventory)

		if (hasBeenReceived && inventory.all(ItemStack::isEmpty)) {
			stack.shrink(1)
		} else {
			stack.set(ModDataComponents.ENDER_LETTER_CONTENTS, newComponent)
		}
	}

	companion object {
		const val INVENTORY_SIZE: Int = 9

		fun getCapability(stack: ItemStack, any: Any?): ItemStackHandler {
			return ItemInventoryItemHandler(stack, ModDataComponents.ENDER_LETTER_CONTENTS.get())
		}

		val CODEC: Codec<EnderLetterContentsDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					NonNullList.codecOf(ItemStack.OPTIONAL_CODEC)
						.fieldOf("stacks")
						.forGetter(EnderLetterContentsDataComponent::stacks),
					Codec.STRING
						.optionalFieldOf("sender")
						.forGetter(EnderLetterContentsDataComponent::sender),
					Codec.STRING
						.optionalFieldOf("recipient")
						.forGetter(EnderLetterContentsDataComponent::recipient)
				).apply(instance, ::EnderLetterContentsDataComponent)
			}

		val STREAM_CODEC: StreamCodec<ByteBuf?, EnderLetterContentsDataComponent?> =
			StreamCodec.composite(
				OtherUtil.STACK_LIST_STREAM_CODEC, EnderLetterContentsDataComponent::stacks,
				ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs::optional), EnderLetterContentsDataComponent::sender,
				ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs::optional), EnderLetterContentsDataComponent::recipient,
				::EnderLetterContentsDataComponent
			)

	}

}