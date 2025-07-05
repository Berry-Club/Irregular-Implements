package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponentType
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.ItemStackHandler
import java.util.*

data class EnderLetterContentsDataComponent(
	private val stacks: NonNullList<ItemStack>,
	val sender: Optional<String>,
	val recipient: Optional<String>
) : ItemInventoryItemHandler.InventoryDataComponent {

	constructor(
		stacks: NonNullList<ItemStack>,
		sender: String?,
		recipient: String?
	) : this(
		stacks,
		Optional.ofNullable(sender),
		Optional.ofNullable(recipient)
	)

	override fun getType(): DataComponentType<*> {
		TODO("Not yet implemented")
	}

	override fun getInventory(): NonNullList<ItemStack> {
		TODO("Not yet implemented")
	}

	override fun setInventory(stack: ItemStack, inventory: NonNullList<ItemStack>) {
		TODO("Not yet implemented")
	}

	companion object {
		fun getCapability(stack: ItemStack, any: Any?): ItemStackHandler {
			return object : ItemInventoryItemHandler<EnderLetterContentsDataComponent>(stack, ModDataComponents.ENDER_LETTER_CONTENTS.get()) {
				override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
					if (slot < 9) return !stack.has(ModDataComponents.ENDER_LETTER_CONTENTS)
					return true
				}
			}
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