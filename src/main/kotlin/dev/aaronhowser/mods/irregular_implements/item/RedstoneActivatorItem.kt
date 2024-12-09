package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class RedstoneActivatorItem : Item(
    Properties()
        .stacksTo(1)
        .component(ModDataComponents.DURATION, 20)
) {

    companion object {
        fun cycleDuration(stack: ItemStack) {
            val currentDuration = stack.get(ModDataComponents.DURATION) ?: return
            val newDuration = when (currentDuration) {
                20 -> 100
                100 -> 2
                else -> 20
            }

            stack.set(ModDataComponents.DURATION, newDuration)
        }
    }

    override fun use(
        level: Level,
        player: Player,
        usedHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)
        if (level.isClientSide) return InteractionResultHolder.pass(usedStack)
        if (!player.isSecondaryUseActive) return InteractionResultHolder.pass(usedStack)

        cycleDuration(usedStack)

        val newDuration = usedStack.get(ModDataComponents.DURATION) ?: return InteractionResultHolder.fail(usedStack)
        val component = newDuration.toString()
            .toComponent()
            .withStyle(ChatFormatting.RED)

        player.displayClientMessage(component, true)

        return InteractionResultHolder.success(usedStack)
    }

//    override fun useOn(context: UseOnContext): InteractionResult {
//
//    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        val duration = stack.get(ModDataComponents.DURATION) ?: return

        val component = "Duration: $duration"
            .toComponent()
            .withStyle(ChatFormatting.GRAY)

        tooltipComponents.add(component)
    }

}