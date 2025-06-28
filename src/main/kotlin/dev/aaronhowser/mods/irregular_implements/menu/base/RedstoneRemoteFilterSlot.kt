package dev.aaronhowser.mods.irregular_implements.menu.base

import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.HolderLookup
import net.minecraft.world.SimpleContainer
import net.minecraft.world.inventory.NonInteractiveResultSlot
import net.minecraft.world.item.ItemStack
import java.util.function.Supplier

class RedstoneRemoteFilterSlot(
	private val redstoneRemoteStack: Supplier<ItemStack>,
	private val lookupProvider: HolderLookup.Provider,
	x: Int,
	y: Int
) : NonInteractiveResultSlot(SimpleContainer(0), 0, x, y) {

	private val stackComponent: RedstoneRemoteDataComponent?
		get() = redstoneRemoteStack.get().get(ModDataComponents.REDSTONE_REMOTE)

	val filterInThisSlot: ItemStack?
		get() = stackComponent?.getPair(this.index)?.first

	override fun getItem(): ItemStack {
		return filterInThisSlot ?: ItemStack.EMPTY
	}

	override fun remove(amount: Int): ItemStack = ItemStack.EMPTY
	override fun set(stack: ItemStack) {}
	override fun isHighlightable(): Boolean = true
	override fun getSlotIndex(): Int = 0

}