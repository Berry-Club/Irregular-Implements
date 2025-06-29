package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.ItemStackHandler

data class RedstoneRemoteDataComponent(
	private val stacks: NonNullList<ItemStack>
) {

	val handler: ItemStackHandler = ItemStackHandler(stacks)

	companion object {

		fun getCapability(stack: ItemStack, any: Any?): ItemStackHandler? {
			return stack.get(ModDataComponents.REDSTONE_REMOTE)?.handler
		}

		val CODEC: Codec<RedstoneRemoteDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					NonNullList.codecOf(ItemStack.OPTIONAL_CODEC)
						.fieldOf("stacks")
						.forGetter(::trimStacks),
				).apply(instance, ::RedstoneRemoteDataComponent)
			}

		private fun trimStacks(
			redstoneRemoteDataComponent: RedstoneRemoteDataComponent
		): NonNullList<ItemStack> {
			val array = redstoneRemoteDataComponent.stacks.toTypedArray()
			val lastNonEmpty = array.indexOfLast { !it.isEmpty }

			val trimmedArray = array.take(lastNonEmpty + 1).toTypedArray()

			return NonNullList.of(ItemStack.EMPTY, *trimmedArray)
		}

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, RedstoneRemoteDataComponent> =
			StreamCodec.composite(
				ByteBufCodecs.fromCodec(NonNullList.codecOf(ItemStack.OPTIONAL_CODEC)), RedstoneRemoteDataComponent::stacks,
				::RedstoneRemoteDataComponent
			)

	}

}