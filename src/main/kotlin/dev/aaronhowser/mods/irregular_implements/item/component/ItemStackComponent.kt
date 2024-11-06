package dev.aaronhowser.mods.irregular_implements.item.component

import com.mojang.serialization.Codec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

data class ItemStackComponent(
    val itemStack: ItemStack
) {

    companion object {
        val CODEC: Codec<ItemStackComponent> =
            ItemStack.CODEC.xmap(::ItemStackComponent, ItemStackComponent::itemStack)

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ItemStackComponent> =
            ItemStack.STREAM_CODEC.map(::ItemStackComponent, ItemStackComponent::itemStack)
    }

}