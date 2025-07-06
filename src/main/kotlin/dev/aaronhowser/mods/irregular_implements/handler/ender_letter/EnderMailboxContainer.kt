package dev.aaronhowser.mods.irregular_implements.handler.ender_letter

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class EnderMailboxContainer(
	val player: Player,
	val blockPos: BlockPos
) : Container {

	val inventory: EnderMailboxInventory? = if (player is ServerPlayer) {
		EnderLetterHandler.get(player.serverLevel()).getOrCreateInventory(player.uuid)
	} else {
		null
	}

	override fun getContainerSize(): Int = 9

	override fun isEmpty(): Boolean = inventory?.hasRoom() == false
	override fun getItem(slot: Int): ItemStack = inventory?.getStackInSlot(slot) ?: ItemStack.EMPTY

	override fun removeItem(slot: Int, amount: Int): ItemStack {
		val inv = inventory ?: return ItemStack.EMPTY
		if (slot !in 0 until inv.slots) return ItemStack.EMPTY

		return inv.extractItem(slot, amount, false)
	}

	override fun removeItemNoUpdate(slot: Int): ItemStack {
		val inv = inventory ?: return ItemStack.EMPTY
		if (slot !in 0 until inv.slots) return ItemStack.EMPTY

		val stack = inv.getStackInSlot(slot).copy()
		inv.enderLetters[slot] = ItemStack.EMPTY
		return stack
	}

	override fun setItem(slot: Int, stack: ItemStack) {
		val inv = inventory ?: return
		if (slot !in 0 until inv.slots) return

		inv.enderLetters[slot] = stack.copy()
		setChanged()
	}

	override fun setChanged() {
		inventory?.enderLetterHandler?.setDirty()
	}

	override fun stillValid(player: Player): Boolean {
		return player.level().getBlockState(blockPos).`is`(ModBlocks.ENDER_MAILBOX)
	}

	override fun clearContent() {
		inventory?.enderLetters?.replaceAll { ItemStack.EMPTY }
		setChanged()
	}
}