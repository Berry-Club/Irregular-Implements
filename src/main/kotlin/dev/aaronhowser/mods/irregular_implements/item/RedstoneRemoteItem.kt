package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteEditMenu
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteUseMenu
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
import java.util.function.Supplier

class RedstoneRemoteItem(properties: Properties) : Item(properties), MenuProvider {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		player.openMenu(this)

		val usedStack = player.getItemInHand(usedHand)
		return InteractionResultHolder.success(usedStack)
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return if (player.isSecondaryUseActive) {
			RedstoneRemoteEditMenu(containerId, playerInventory)
		} else {
			RedstoneRemoteUseMenu(containerId, playerInventory)
		}
	}

	override fun getDisplayName(): Component = this.defaultInstance.hoverName

	companion object {
		val DEFAULT_PROPERTIES = Supplier {
			Properties()
				.stacksTo(1)
				.component(ModDataComponents.REDSTONE_REMOTE, RedstoneRemoteDataComponent())
		}
	}

}