package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.world.SimpleContainer
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack
import java.util.function.Supplier

class RedstoneRemoteDisplaySlot(
	private val redstoneRemoteStack: Supplier<ItemStack>,
	private val pairIndex: Int,
	x: Int,
	y: Int
) : NonInteractiveResultSlot(SimpleContainer(0), 0, x, y) {

	private val stackComponent: RedstoneRemoteDataComponent?
		get() = redstoneRemoteStack.get().get(ModDataComponents.REDSTONE_REMOTE)

	val displayStackInThisSlot: ItemStack?
		get() = stackComponent?.getDisplay(pairIndex)

	override fun safeInsert(stack: ItemStack): ItemStack {
		if (stack.isEmpty) return stack

		val oldComponent = stackComponent ?: return stack

		val displays = oldComponent.displayStacks
		displays[pairIndex] = stack.copyWithCount(1)

		for (i in displays.indices) {
			displays[i] = displays[i].copy()
		}

		val newComponent = RedstoneRemoteDataComponent(
			locationFilters = oldComponent.locationFilters,
			displayStacks = displays,
		)

		redstoneRemoteStack.get().set(ModDataComponents.REDSTONE_REMOTE, newComponent)

		return ItemStack.EMPTY
	}

	override fun getItem(): ItemStack = displayStackInThisSlot ?: ItemStack.EMPTY
	override fun remove(amount: Int): ItemStack = ItemStack.EMPTY
	override fun set(stack: ItemStack) {}
	override fun isHighlightable(): Boolean = true
	override fun getSlotIndex(): Int = 0

}