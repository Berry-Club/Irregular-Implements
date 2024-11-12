package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.entity.TimeAcceleratorEntity
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import kotlin.math.abs

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

        fun addTicks(stack: ItemStack, amount: Int) {
            modifyStoredTime(stack, abs(amount))
        }

        fun drainTicks(stack: ItemStack, amount: Int) {
            modifyStoredTime(stack, -abs(amount))
        }

        fun getStoredTicks(stack: ItemStack): Int {
            return stack.get(ModDataComponents.STORED_TIME) ?: 0
        }

        private fun consumeTicks(player: LivingEntity?, itemStack: ItemStack): Boolean {
            if (player?.hasInfiniteMaterials().isTrue) return true

            val storedTicks = getStoredTicks(itemStack)
            val ticksRequired = ServerConfig.TIAB_COST.get()

            if (storedTicks < ticksRequired) return false
            drainTicks(itemStack, ticksRequired)

            return true
        }
    }

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        addTicks(stack, 1)
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

        val useSuccessful = consumeTicks(player, usedStack)
        if (!useSuccessful) return InteractionResult.FAIL

        val existingAccelerator = level.getEntitiesOfClass(
            TimeAcceleratorEntity::class.java,
            AABB(clickedPos)
        ).firstOrNull()

        if (existingAccelerator != null) {
            val currentRate = existingAccelerator.tickRate
            if (currentRate >= ServerConfig.TIAB_MAX_STACKS.get()) return InteractionResult.FAIL

            existingAccelerator.tickRate++
            existingAccelerator.ticksRemaining = ServerConfig.TIAB_TICKS_PER

            return InteractionResult.SUCCESS
        }

        val newAccelerator = TimeAcceleratorEntity(level, clickedPos)
        level.addFreshEntity(newAccelerator)

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
        } else {
            var seconds = storedTicks / 20
            var minutes = seconds / 60
            val hoursTotal = minutes / 60

            val sb = StringBuilder()

            if (hoursTotal > 0) {
                sb.append(hoursTotal).append("h ")
                minutes %= 60
            }

            if (minutes > 0) {
                sb.append(minutes).append("m ")
                seconds %= 60
            }

            sb.append(seconds).append("s")
            sb.toString()
        }

        val component = Component.literal(timeDisplay)

        tooltipComponents.add(component)
    }

    override fun shouldCauseReequipAnimation(oldStack: ItemStack, newStack: ItemStack, slotChanged: Boolean): Boolean {
        return slotChanged
    }

    override fun isFoil(stack: ItemStack): Boolean {
        return true
    }

}