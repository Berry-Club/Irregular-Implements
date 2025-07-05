package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.ItemStackHandler

data class RedstoneRemoteDataComponent(
	private val stacks: NonNullList<ItemStack>
) : ItemInventoryItemHandler.InventoryDataComponent {

	constructor() : this(NonNullList.withSize(HORIZONTAL_SLOT_COUNT * 2, ItemStack.EMPTY))


	override fun getInventory(): NonNullList<ItemStack> = stacks
	override fun setInventory(stack: ItemStack, inventory: NonNullList<ItemStack>) {
		stack.set(ModDataComponents.REDSTONE_REMOTE, RedstoneRemoteDataComponent(inventory))
	}

	fun getLocation(index: Int): ItemStack = stacks.getOrNull(index) ?: ItemStack.EMPTY
	fun getDisplay(index: Int): ItemStack = stacks.getOrNull(index + 9) ?: ItemStack.EMPTY
	fun getPair(index: Int): Pair<ItemStack, ItemStack> = getLocation(index) to getDisplay(index)

	companion object {
		const val HORIZONTAL_SLOT_COUNT: Int = 8

		fun getCapability(stack: ItemStack, any: Any?): ItemStackHandler {
			return object : ItemInventoryItemHandler<RedstoneRemoteDataComponent>(stack, ModDataComponents.REDSTONE_REMOTE.get()) {
				override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
					if (slot <= HORIZONTAL_SLOT_COUNT) return stack.has(ModDataComponents.LOCATION)
					return true
				}
			}
		}

		val CODEC: Codec<RedstoneRemoteDataComponent> =
			RecordCodecBuilder.create { instance ->
				instance.group(
					NonNullList.codecOf(ItemStack.OPTIONAL_CODEC)
						.fieldOf("stacks")
						.forGetter(RedstoneRemoteDataComponent::stacks),
				).apply(instance, ::RedstoneRemoteDataComponent)
			}

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, RedstoneRemoteDataComponent> =
			StreamCodec.composite(
				OtherUtil.STACK_LIST_STREAM_CODEC, RedstoneRemoteDataComponent::stacks,
				::RedstoneRemoteDataComponent
			)

	}

}