package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.RedstoneRemoteDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteEditMenu
import dev.aaronhowser.mods.irregular_implements.menu.redstone_remote.RedstoneRemoteUseMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.MenuConstructor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import java.util.function.Supplier

class RedstoneRemoteItem(properties: Properties) : Item(properties) {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		val usedStack = player.getItemInHand(usedHand)
		if (!level.isClientSide) {
			val menuConstructor = if (player.isSecondaryUseActive) {
				MenuConstructor { containerId, playerInventory, _ ->
					RedstoneRemoteEditMenu(containerId, playerInventory, usedHand)
				}
			} else {
				MenuConstructor { containerId, playerInventory, _ ->
					RedstoneRemoteUseMenu(containerId, playerInventory, usedHand)
				}
			}
			val provider = SimpleMenuProvider(menuConstructor, usedStack.hoverName)
			player.openMenu(provider) { data -> data.writeEnum(usedHand) }
		}

		return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide)
	}

	companion object {
		val DEFAULT_PROPERTIES = Supplier {
			Properties()
				.stacksTo(1)
				.component(ModDataComponents.REDSTONE_REMOTE, RedstoneRemoteDataComponent())
		}
	}

}