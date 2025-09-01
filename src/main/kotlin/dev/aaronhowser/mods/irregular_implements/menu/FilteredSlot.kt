package dev.aaronhowser.mods.irregular_implements.menu

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class FilteredSlot(
	container: Container,
	slotIndex: Int,
	x: Int,
	y: Int,
	val insertPredicate: (ItemStack) -> Boolean = { true }
) : Slot(container, slotIndex, x, y) {

	override fun mayPlace(stack: ItemStack): Boolean = insertPredicate(stack)

}