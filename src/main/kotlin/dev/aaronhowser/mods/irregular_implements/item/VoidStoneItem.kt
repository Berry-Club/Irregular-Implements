package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.VoidStoneMenu
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.level.Level

class VoidStoneItem : Item(
	Properties()
		.stacksTo(1)
), MenuProvider {

	override fun overrideOtherStackedOnMe(
		thisStack: ItemStack,
		other: ItemStack,
		slot: Slot,
		action: ClickAction,
		player: Player,
		access: SlotAccess
	): Boolean {
		if (action != ClickAction.SECONDARY
			|| !slot.allowModification(player)
			|| other.isEmpty
		) return false

		thisStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(listOf(other.copy())))

		other.count = 0

		player.level().playSound(
			if (player.level().isClientSide) player else null,
			player.blockPosition(),
			SoundEvents.ENDERMAN_TELEPORT,
			SoundSource.PLAYERS,
			1f,
			0.3f
		)

		return true
	}

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		player.openMenu(this)

		val usedStack = player.getItemInHand(usedHand)
		return InteractionResultHolder.success(usedStack)
	}

	// Menu stuff

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return VoidStoneMenu(containerId, playerInventory)
	}

	override fun getDisplayName(): Component {
		return this.descriptionId.toComponent()
	}

}