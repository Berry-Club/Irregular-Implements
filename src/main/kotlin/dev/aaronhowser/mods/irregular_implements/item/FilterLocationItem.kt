package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.LocationItemComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
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
        val player = context.player
        if (player?.cooldowns?.isOnCooldown(this).isTrue) {
            return InteractionResult.PASS
        }

        val usedStack = context.itemInHand
        usedStack.set(
            ModDataComponents.LOCATION.get(),
            LocationItemComponent(context.level, context.clickedPos)
        )

        player?.cooldowns?.addCooldown(this, 1)

        return InteractionResult.SUCCESS
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)

        if (player.cooldowns.isOnCooldown(this)) {
            return InteractionResultHolder.pass(usedStack)
        }

        usedStack.set(
            ModDataComponents.LOCATION.get(),
            LocationItemComponent(level, player.blockPosition())
        )

        player.cooldowns.addCooldown(this, 1)

        return InteractionResultHolder.success(usedStack)
    }

    //TODO: Render a cube at the location of the block position

}