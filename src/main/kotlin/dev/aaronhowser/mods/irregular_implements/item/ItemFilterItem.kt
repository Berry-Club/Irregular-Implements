package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterEntryListDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.ItemFilterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
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
import net.minecraft.world.item.component.Unbreakable
import net.minecraft.world.level.Level

class ItemFilterItem : Item(
    Properties()
        .stacksTo(1)
), MenuProvider {

    companion object {

        fun setTestingFilter(stack: ItemStack) {
            val planksFilter = ItemFilterEntryListDataComponent.FilterEntry.ItemTag(ItemTags.PLANKS)
            val stickFilter = ItemFilterEntryListDataComponent.FilterEntry.SpecificItem(Items.STICK.defaultInstance, requireSameComponents = false)

            val unbreakableDiamond = Items.DIAMOND.defaultInstance
            unbreakableDiamond.set(DataComponents.UNBREAKABLE, Unbreakable(true))
            val diamondFilter = ItemFilterEntryListDataComponent.FilterEntry.SpecificItem(unbreakableDiamond, requireSameComponents = true)

            val component = ItemFilterEntryListDataComponent(
                planksFilter,
                stickFilter,
                diamondFilter
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

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        player.openMenu(this)

        val usedStack = player.getItemInHand(usedHand)
        return InteractionResultHolder.success(usedStack)
    }

    // Menu stuff

    override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
        return ItemFilterMenu(containerId, playerInventory)
    }

    override fun getDisplayName(): Component {
        return this.defaultInstance.displayName
    }

}