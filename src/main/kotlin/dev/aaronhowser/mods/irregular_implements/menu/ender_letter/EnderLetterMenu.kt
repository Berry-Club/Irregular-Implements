package dev.aaronhowser.mods.irregular_implements.menu.ender_letter

import dev.aaronhowser.mods.irregular_implements.item.component.EnderLetterContentsDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.HeldItemContainerMenu
import dev.aaronhowser.mods.irregular_implements.menu.MenuWithStrings
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientScreenString
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModMenuTypes
import dev.aaronhowser.mods.irregular_implements.util.ServerScheduler
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.SlotItemHandler
import java.util.*
import kotlin.jvm.optionals.getOrNull

class EnderLetterMenu(
	containerId: Int,
	playerInventory: Inventory
) : HeldItemContainerMenu(
	ModItems.ENDER_LETTER,
	ModMenuTypes.ENDER_LETTER.get(),
	containerId,
	playerInventory
), MenuWithStrings {

	private val itemHandler: IItemHandler? = getHeldItemStack().getCapability(Capabilities.ItemHandler.ITEM)

	init {
		addSlots()
		addPlayerInventorySlots(51)

		val player = playerInventory.player
		if (player is ServerPlayer) {
			val recipient = getHeldItemStack()
				.get(ModDataComponents.ENDER_LETTER_CONTENTS)
				?.recipient
				?.getOrNull()

			if (recipient != null) {
				ServerScheduler.scheduleTaskInTicks(1) {
					ModPacketHandler.messagePlayer(
						player,
						UpdateClientScreenString(RECIPIENT_STRING_ID, recipient)
					)
				}
			}
		}
	}

	override fun addSlots() {
		if (itemHandler == null) return

		val y = 18

		for (i in 0 until EnderLetterContentsDataComponent.INVENTORY_SIZE) {
			val x = 8 + i * 18
			val slot = SlotItemHandler(itemHandler, i, x, y)
			this.addSlot(slot)
		}
	}

	override fun quickMoveStack(player: Player, index: Int): ItemStack {
		return ItemStack.EMPTY
	}

	override fun stillValid(player: Player): Boolean {
		return player.getItemInHand(hand).`is`(ModItems.ENDER_LETTER)
	}

	private var recipientName: String = ""

	override fun receiveString(stringId: Int, stringReceived: String) {
		when (stringId) {
			RECIPIENT_STRING_ID -> setNewRecipient(stringReceived)
		}
	}

	fun setNewRecipient(recipient: String): Boolean {
		if (recipient == recipientName) return false
		this.recipientName = recipient

		val oldComponent = getHeldItemStack().get(ModDataComponents.ENDER_LETTER_CONTENTS) ?: return false
		val newComponent = oldComponent.copy(recipient = Optional.of(recipient))

		getHeldItemStack().set(ModDataComponents.ENDER_LETTER_CONTENTS, newComponent)
		return true
	}

	companion object {
		const val RECIPIENT_STRING_ID = 0
	}
}