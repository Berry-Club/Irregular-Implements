package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.void_stone.VoidStoneMenu
import net.minecraft.core.component.DataComponents
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.MenuConstructor
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.level.Level

class VoidStoneItem(properties: Properties) : Item(properties) {

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
		val usedStack = player.getItemInHand(usedHand)
		if (!level.isClientSide) {
			val menuConstructor = MenuConstructor { containerId, playerInventory, _ ->
				VoidStoneMenu(containerId, playerInventory, usedHand)
			}
			val provider = SimpleMenuProvider(menuConstructor, descriptionId.toComponent())
			player.openMenu(provider) { data -> data.writeEnum(usedHand) }
		}

		return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide)
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}