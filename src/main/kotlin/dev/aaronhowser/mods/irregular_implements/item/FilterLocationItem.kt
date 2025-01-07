package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.item.component.LocationItemComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class FilterLocationItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val usedStack = context.itemInHand
        usedStack.set(
            ModDataComponents.LOCATION,
            LocationItemComponent(context.level, context.clickedPos)
        )

        return InteractionResult.SUCCESS
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)

        if (level.isClientSide || !player.isSecondaryUseActive) return InteractionResultHolder.pass(usedStack)

        usedStack.remove(ModDataComponents.LOCATION)

        return InteractionResultHolder.success(usedStack)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        val location = stack.get(ModDataComponents.LOCATION)

        if (location != null) {
            val x = location.blockPos.x
            val y = location.blockPos.y
            val z = location.blockPos.z

            val dimensionComponent = OtherUtil.getDimensionComponent(location.dimension)

            val component = ModLanguageProvider.Tooltips.LOCATION_COMPONENT
                .toGrayComponent(dimensionComponent, x, y, z)

            tooltipComponents.add(component)
        }
    }

}