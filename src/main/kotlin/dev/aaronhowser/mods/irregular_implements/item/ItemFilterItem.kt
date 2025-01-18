package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterEntryListDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.component.DataComponents
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.Unbreakable

class ItemFilterItem : Item(
    Properties()
        .stacksTo(1)
) {

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

}