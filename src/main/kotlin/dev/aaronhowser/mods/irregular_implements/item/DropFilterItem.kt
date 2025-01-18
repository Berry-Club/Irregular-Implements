package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.neoforged.neoforge.common.util.TriState
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent

class DropFilterItem(
    private val isVoiding: Boolean
) : Item(
    Properties()
        .stacksTo(1)
        .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
) {

    companion object {

        fun onPickupItem(event: ItemEntityPickupEvent.Pre) {
            if (event.canPickup().isFalse) return

            val player = event.player

            val voidingFilters: MutableList<ItemStack> = mutableListOf()
            val regularFilters: MutableList<ItemStack> = mutableListOf()

            for (stack in player.inventory.items) {
                if (stack.`is`(ModItems.VOIDING_DROP_FILTER)) {
                    voidingFilters.add(stack)
                } else if (stack.`is`(ModItems.DROP_FILTER)) {
                    regularFilters.add(stack)
                }
            }

            if (voidingFilters.isEmpty() && regularFilters.isEmpty()) return

            val itemEntity = event.itemEntity
            val stack = itemEntity.item

            for (voidingFilter in voidingFilters) {
                val filter = voidingFilter.get(ModDataComponents.ITEM_FILTER_ENTRIES) ?: continue

                if (filter.test(stack)) {
                    itemEntity.kill()
                    event.setCanPickup(TriState.FALSE)
                    return
                }
            }

            for (regularFilter in regularFilters) {
                val filter = regularFilter.get(ModDataComponents.ITEM_FILTER_ENTRIES) ?: continue

                if (filter.test(stack)) {
                    event.setCanPickup(TriState.FALSE)
                    return
                }
            }

        }
    }

}