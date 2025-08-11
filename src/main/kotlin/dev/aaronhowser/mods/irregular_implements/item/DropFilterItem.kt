package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.drop_filter.DropFilterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.util.TriState
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent

//TODO: Test if this still works
class DropFilterItem(properties: Properties) : Item(properties), MenuProvider {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		player.openMenu(this)

		val usedStack = player.getItemInHand(usedHand)
		return InteractionResultHolder.sidedSuccess(usedStack, level.isClientSide)
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {

		val container = stack.get(DataComponents.CONTAINER)
		val firstStack = container?.nonEmptyItems()?.firstOrNull()
		val filter = firstStack?.get(ModDataComponents.ITEM_FILTER) ?: return

		for (filterEntry in filter.entries) {
			if (filterEntry is FilterEntry.Empty) continue

			val lookup = context.registries() ?: continue
			val itemName = filterEntry.getDisplayStack(lookup).hoverName
			val component = ModTooltipLang.LIST_POINT
				.toGrayComponent(itemName)

			tooltipComponents.add(component)
		}
	}

	// Menu stuff

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return DropFilterMenu(containerId, playerInventory)
	}

	override fun getDisplayName(): Component {
		return this.defaultInstance.hoverName
	}

	companion object {

		val DEFAULT_PROPERTIES: Properties =
			Properties()
				.stacksTo(1)
				.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)

		fun stackIsDropFilter(stack: ItemStack): Boolean {
			return stack.`is`(ModItems.DROP_FILTER) || stack.`is`(ModItems.VOIDING_DROP_FILTER)
		}

		fun beforePickupItem(event: ItemEntityPickupEvent.Pre) {
			if (event.canPickup().isFalse) return

			val itemEntity = event.itemEntity
			if (itemEntity.hasPickUpDelay()) return

			val player = event.player

			val voidingFilterStacks = player.inventory.items
				.filter { it.`is`(ModItems.VOIDING_DROP_FILTER) }
			val filterStacks = player.inventory.items
				.filter { it.`is`(ModItems.DROP_FILTER) }

			if (voidingFilterStacks.isEmpty() && filterStacks.isEmpty()) return

			val stack = itemEntity.item

			for (voidingFilterStack in voidingFilterStacks) {
				val container = voidingFilterStack.get(DataComponents.CONTAINER) ?: continue

				val storedFilterStack = container
					.nonEmptyItems()
					.firstOrNull() ?: continue

				val filter = storedFilterStack
					.get(ModDataComponents.ITEM_FILTER)
					?: continue

				if (filter.test(stack)) {
					player.take(itemEntity, 0)
					stack.count = 0
					return
				}
			}

			for (filterStack in filterStacks) {
				val container = filterStack.get(DataComponents.CONTAINER) ?: continue

				val storedFilterStack = container
					.nonEmptyItems()
					.firstOrNull() ?: continue

				val filter = storedFilterStack
					.get(ModDataComponents.ITEM_FILTER)
					?: continue

				if (filter.test(stack)) {
					event.setCanPickup(TriState.FALSE)
					return
				}
			}

		}
	}

}