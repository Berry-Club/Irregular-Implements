package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

data class PortkeyDisguiseDataComponent(
	val stack: ItemStack
) {

	companion object {
		val CODEC: Codec<PortkeyDisguiseDataComponent> =
			ItemStack.CODEC.xmap(::PortkeyDisguiseDataComponent, PortkeyDisguiseDataComponent::stack)

		val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, PortkeyDisguiseDataComponent> =
			ItemStack.STREAM_CODEC.map(::PortkeyDisguiseDataComponent, PortkeyDisguiseDataComponent::stack)
	}

}