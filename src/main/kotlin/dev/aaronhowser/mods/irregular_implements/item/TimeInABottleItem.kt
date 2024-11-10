package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.entity.TimeAcceleratorEntity
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB

// Code largely inspired by https://github.com/RealMangorage/time-in-a-bottle/blob/1.21/common/src/main/java/org/mangorage/tiab/common/items/TiabItem.java
// Which is MIT which means I don't have to live in guilt
// It's not going to stop me, though
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

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        if (level.isClientSide) return InteractionResult.PASS

        val clickedPos = context.clickedPos
        val clickedState = level.getBlockState(clickedPos)
        val clickedBlockEntity = level.getBlockEntity(clickedPos)

        if (clickedState.`is`(ModBlockTagsProvider.CANNOT_ACCELERATE)) return InteractionResult.FAIL
        if (!clickedState.isRandomlyTicking && clickedBlockEntity == null) return InteractionResult.FAIL

        val usedStack = context.itemInHand
        val player = context.player

        val existingAccelerator = level.getEntitiesOfClass(
            TimeAcceleratorEntity::class.java,
            AABB(clickedPos)
        ).firstOrNull()

        return InteractionResult.SUCCESS
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

    override fun shouldCauseReequipAnimation(oldStack: ItemStack, newStack: ItemStack, slotChanged: Boolean): Boolean {
        return slotChanged
    }


}