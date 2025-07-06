package dev.aaronhowser.mods.irregular_implements.handler.ender_letter

import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import kotlin.math.pow

class EnderMailboxContainer(
	val player: ServerPlayer,
	val blockPos: BlockPos
) : Container {

	val inventory: EnderMailboxInventory = EnderLetterHandler
		.get(player.serverLevel())
		.getOrCreateInventory(player)

	override fun getContainerSize(): Int = inventory.slots
	override fun isEmpty(): Boolean = inventory.enderLetters.all(ItemStack::isEmpty)
	override fun getItem(slot: Int): ItemStack = inventory.getStackInSlot(slot)
	override fun removeItem(slot: Int, amount: Int): ItemStack = inventory.extractItem(slot, amount, false)
	override fun removeItemNoUpdate(slot: Int): ItemStack = inventory.extractItem(slot, 1, false)

	override fun setItem(slot: Int, stack: ItemStack) {
		if (inventory.isItemValid(slot, stack)) {
			inventory.insertItem(slot, stack, false)
		}
	}

	override fun setChanged() {
		inventory.enderLetterHandler.setDirty()
	}

	override fun stillValid(player: Player): Boolean {
		val distanceSqr = player.distanceToSqr(blockPos.center)
		val playerReachSqr = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE).pow(2)
		if (distanceSqr > playerReachSqr) return false

		return player.level().getBlockState(blockPos).`is`(ModBlocks.ENDER_MAILBOX)
	}

	override fun clearContent() {
		inventory.enderLetters.replaceAll { ItemStack.EMPTY }
		inventory.enderLetterHandler.setDirty()
	}
}