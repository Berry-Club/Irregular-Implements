package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.menu.RedstoneRemoteUseMenu
import dev.aaronhowser.mods.irregular_implements.menu.RedstoneRemoteEditMenu
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.Item

class RedstoneRemoteItem(properties: Properties) : Item(properties), MenuProvider {

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return if (player.isSecondaryUseActive) {
			RedstoneRemoteEditMenu(containerId, playerInventory)
		} else {
			RedstoneRemoteUseMenu(containerId, playerInventory)
		}
	}

	override fun getDisplayName(): Component = this.defaultInstance.hoverName

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)
	}

}