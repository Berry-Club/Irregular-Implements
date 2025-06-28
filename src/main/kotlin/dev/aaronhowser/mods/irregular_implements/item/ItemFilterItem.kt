package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.ItemFilterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.tags.ItemTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.Unbreakable
import net.minecraft.world.level.Level

class ItemFilterItem(properties: Properties) : Item(properties), MenuProvider {

	override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
		player.openMenu(this)

		val usedStack = player.getItemInHand(usedHand)
		return InteractionResultHolder.success(usedStack)
	}

	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val itemComponent = stack.get(ModDataComponents.ITEM_FILTER_ENTRIES) ?: return

		if (itemComponent.isBlacklist) {
			val component = ModLanguageProvider.Tooltips.BLACKLIST
				.toComponent().withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE)

			tooltipComponents.add(component)
		}

		for (filterEntry in itemComponent.entries) {
			if (filterEntry is FilterEntry.Empty) continue

			val lookup = context.registries() ?: continue
			val itemName = filterEntry.getDisplayStack(lookup).hoverName
			val component = ModLanguageProvider.Tooltips.LIST_POINT
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

		val DEFAULT_PROPERTIES: Properties =
			Properties()
				.stacksTo(1)
				.component(ModDataComponents.ITEM_FILTER_ENTRIES, ItemFilterDataComponent())

		fun setTestingFilter(stack: ItemStack) {
			val planksFilter = FilterEntry.Tag(ItemTags.PLANKS, Items.OAK_PLANKS.defaultInstance)
			val stickFilter = FilterEntry.Item(Items.STICK.defaultInstance, requireSameComponents = false)

			val unbreakableDiamond = Items.DIAMOND.defaultInstance
			unbreakableDiamond.set(DataComponents.UNBREAKABLE, Unbreakable(true))
			val diamondFilter = FilterEntry.Item(unbreakableDiamond, requireSameComponents = true)

			val component = ItemFilterDataComponent(
				listOf(
					planksFilter,
					stickFilter,
					diamondFilter
				)
			)

			stack.set(ModDataComponents.ITEM_FILTER_ENTRIES, component)
		}

		fun testFilter(stack: ItemStack) {
			val filter = stack.get(ModDataComponents.ITEM_FILTER_ENTRIES) ?: return

			val oakPlanks = Items.OAK_PLANKS.defaultInstance
			val birchPlanks = Items.BIRCH_PLANKS.defaultInstance

			val stick = Items.STICK.defaultInstance

			val diamond = Items.DIAMOND.defaultInstance

			val unbreakableDiamond = Items.DIAMOND.defaultInstance
			unbreakableDiamond.set(DataComponents.UNBREAKABLE, Unbreakable(true))

			val oakPass = filter.test(oakPlanks)
			val birchPass = filter.test(birchPlanks)

			val stickPass = filter.test(stick)

			val diamondPass = filter.test(diamond)
			val unbreakableDiamondPass = filter.test(unbreakableDiamond)

			println(
				"""
                Oak Planks: $oakPass
                Birch Planks: $birchPass
                
                Stick: $stickPass
                
                Diamond: $diamondPass
                Unbreakable Diamond: $unbreakableDiamondPass
            """.trimIndent()
			)
		}
	}

}