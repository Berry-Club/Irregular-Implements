package dev.aaronhowser.mods.irregular_implements.attachment

import com.mojang.serialization.Codec
import net.minecraft.world.item.ItemStack

data class DeathKeptItems(
	val stacks: List<ItemStack>
) {

	constructor() : this(emptyList())

	companion object {
		val CODEC: Codec<DeathKeptItems> =
			ItemStack.CODEC.listOf().xmap(::DeathKeptItems, DeathKeptItems::stacks)
	}

}