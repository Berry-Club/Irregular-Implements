package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class TimeInABottleItem : Item(
    Properties()
        .stacksTo(1)
        .component(ModDataComponents.STORED_TIME, 0)
) {

    companion object {
        fun modifyStoredTime(stack: ItemStack, change: Int) {
            val old = stack.get(ModDataComponents.STORED_TIME) ?: 0
            val new = maxOf(0, old + change)
            stack.set(ModDataComponents.STORED_TIME, new)
        }
    }

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        modifyStoredTime(stack, 1)
    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val storedTicks = stack.get(ModDataComponents.STORED_TIME) ?: 0

        val timeDisplay = if (tooltipFlag.hasShiftDown() || storedTicks < 20) {
            "${storedTicks}t"
        } else if (storedTicks < (20 * 60)) {
            "${storedTicks / 20}s"
        } else {
            val minutes = storedTicks / (20 * 60)
            val seconds = (storedTicks % (20 * 60)) / 20

            "${minutes}m ${seconds}s"
        }

        val component = Component.literal(timeDisplay)

        tooltipComponents.add(component)
    }


}