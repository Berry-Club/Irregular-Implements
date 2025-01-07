package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.LocationItemComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class FilterLocationItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val usedStack = context.itemInHand
        usedStack.set(
            ModDataComponents.LOCATION.get(),
            LocationItemComponent(context.level, context.clickedPos)
        )

        return InteractionResult.SUCCESS
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)

        if (level.isClientSide || !player.isSecondaryUseActive) return InteractionResultHolder.pass(usedStack)

        usedStack.remove(ModDataComponents.LOCATION.get())

        return InteractionResultHolder.success(usedStack)
    }

}