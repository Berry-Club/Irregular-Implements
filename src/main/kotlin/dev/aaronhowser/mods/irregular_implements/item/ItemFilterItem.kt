package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.item_filter.ItemFilterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.ChatFormatting
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
import net.minecraft.world.level.Level
import java.util.function.Supplier

class ItemFilterItem(properties: Properties) : Item(properties), MenuProvider {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		player.openMenu(this)

		val usedStack = player.getItemInHand(usedHand)
		return InteractionResultHolder.success(usedStack)
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val itemComponent = stack.get(ModDataComponents.ITEM_FILTER) ?: return

		if (itemComponent.isBlacklist) {
			val component = ModTooltipLang.BLACKLIST
				.toComponent().withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE)

			tooltipComponents.add(component)
		}

		for (filterEntry in itemComponent.entries) {
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
		return ItemFilterMenu(containerId, playerInventory)
	}

	override fun getDisplayName(): Component {
		return this.defaultInstance.hoverName
	}


	companion object {

		val DEFAULT_PROPERTIES: Supplier<Properties> = Supplier {
			Properties()
				.stacksTo(1)
				.component(ModDataComponents.ITEM_FILTER, ItemFilterDataComponent())
		}
	}

}