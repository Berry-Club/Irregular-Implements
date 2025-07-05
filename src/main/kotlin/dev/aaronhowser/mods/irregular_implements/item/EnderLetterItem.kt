package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.EnderLetterContentsDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.ender_letter.EnderLetterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class EnderLetterItem(properties: Properties) : Item(properties), MenuProvider {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)
		if (!usedStack.has(ModDataComponents.ENDER_LETTER_CONTENTS)) {
			usedStack.set(ModDataComponents.ENDER_LETTER_CONTENTS, EnderLetterContentsDataComponent())
		}

		player.openMenu(this)

		return InteractionResultHolder.success(usedStack)
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return EnderLetterMenu(containerId, playerInventory)
	}

	override fun getDisplayName(): Component = this.defaultInstance.hoverName

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}