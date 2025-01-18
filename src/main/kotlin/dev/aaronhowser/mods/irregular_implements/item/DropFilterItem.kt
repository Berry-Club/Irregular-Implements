package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.neoforged.neoforge.common.util.TriState
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent

class DropFilterItem : Item(
    Properties()
        .stacksTo(1)
        .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
) {

    companion object {

        fun setFilter(stack: ItemStack) {
            val filter = ModItems.ITEM_FILTER.toStack()
            ItemFilterItem.setTestingFilter(filter)

            stack.set(
                DataComponents.CONTAINER,
                ItemContainerContents.fromItems(listOf(filter))
            )
        }

        fun beforePickupItem(event: ItemEntityPickupEvent.Pre) {
            if (event.canPickup().isFalse) return

            val itemEntity = event.itemEntity
            if (itemEntity.hasPickUpDelay()) return

            val player = event.player

            val voidingFilterStacks = player.inventory.items
                .filter { it.`is`(ModItems.VOIDING_DROP_FILTER) }
            val filterStacks = player.inventory.items
                .filter { it.`is`(ModItems.ITEM_FILTER) }

            if (voidingFilterStacks.isEmpty() && filterStacks.isEmpty()) return

            val stack = itemEntity.item

            for (voidingFilterStack in voidingFilterStacks) {
                val container = voidingFilterStack.get(DataComponents.CONTAINER) ?: continue
                val filter = container
                    .getStackInSlot(0)
                    .get(ModDataComponents.ITEM_FILTER_ENTRIES)
                    ?: continue

                if (filter.test(stack)) {
                    player.take(itemEntity, 0)
                    stack.count = 0
                    return
                }
            }

            for (filterStack in filterStacks) {
                val container = filterStack.get(DataComponents.CONTAINER) ?: continue
                val filter = container
                    .getStackInSlot(0)
                    .get(ModDataComponents.ITEM_FILTER_ENTRIES)
                    ?: continue

                if (filter.test(stack)) {
                    event.setCanPickup(TriState.FALSE)
                    return
                }
            }

        }
    }

}