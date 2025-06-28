package dev.aaronhowser.mods.irregular_implements.menu.redstone_remote

import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack
import java.util.*
import java.util.function.Supplier

class RedstoneRemoteFilterSlot(
	private val redstoneRemoteStack: Supplier<ItemStack>,
	private val pairIndex: Int,
	x: Int,
	y: Int
) : NonInteractiveResultSlot(SimpleContainer(0), 0, x, y) {

	private val stackComponent: RedstoneRemoteDataComponent?
		get() = redstoneRemoteStack.get().get(ModDataComponents.REDSTONE_REMOTE)

	val filterInThisSlot: ItemStack?
		get() = stackComponent?.getLocation(pairIndex)

	override fun mayPickup(player: Player): Boolean = true
	override fun tryRemove(amount: Int, decrement: Int, player: Player): Optional<ItemStack> {
		val item = getItem()
		if (item.isEmpty || !updateComponent(ItemStack.EMPTY)) {
			return Optional.empty()
		}

		return Optional.of(item.copy())
	}

	override fun safeInsert(stack: ItemStack): ItemStack {
		if (stack.isEmpty
			|| !stack.has(ModDataComponents.LOCATION)
			|| !filterInThisSlot?.isEmpty.isTrue
		) return stack

		if (!updateComponent(stack)) return stack

		return ItemStack.EMPTY
	}

	private fun updateComponent(newFilter: ItemStack): Boolean {
		val oldComponent = stackComponent ?: return false
		val newComponent = oldComponent.copyWithNewFilter(newFilter, pairIndex)
		redstoneRemoteStack.get().set(ModDataComponents.REDSTONE_REMOTE, newComponent)
		return true
	}

	override fun getItem(): ItemStack = filterInThisSlot ?: ItemStack.EMPTY
	override fun remove(amount: Int): ItemStack = ItemStack.EMPTY
	override fun set(stack: ItemStack) {}
	override fun isHighlightable(): Boolean = true
	override fun getSlotIndex(): Int = 0

}