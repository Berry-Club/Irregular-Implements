package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.world.SimpleContainer
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack
import java.util.function.Supplier

class RedstoneRemoteFilterSlot(
	private val redstoneRemoteStack: Supplier<ItemStack>,
	x: Int,
	y: Int
) : NonInteractiveResultSlot(SimpleContainer(0), 0, x, y) {

	private val stackComponent: RedstoneRemoteDataComponent?
		get() = redstoneRemoteStack.get().get(ModDataComponents.REDSTONE_REMOTE)

	val filterInThisSlot: ItemStack?
		get() = stackComponent?.getLocation(index)

	override fun safeInsert(stack: ItemStack): ItemStack {
		if (stack.isEmpty
			|| !stack.has(ModDataComponents.LOCATION)
			|| !filterInThisSlot?.isEmpty.isTrue
		) return stack

		val oldComponent = stackComponent ?: return stack

		val locations = oldComponent.locationFilters
		locations[this.index] = stack

		val newComponent = RedstoneRemoteDataComponent(
			locationFilters = locations,
			displayStacks = oldComponent.displayStacks,
		)

		redstoneRemoteStack.get().set(ModDataComponents.REDSTONE_REMOTE, newComponent)

		return ItemStack.EMPTY
	}

	override fun getItem(): ItemStack = filterInThisSlot ?: ItemStack.EMPTY
	override fun remove(amount: Int): ItemStack = ItemStack.EMPTY
	override fun set(stack: ItemStack) {}
	override fun isHighlightable(): Boolean = true
	override fun getSlotIndex(): Int = 0

}