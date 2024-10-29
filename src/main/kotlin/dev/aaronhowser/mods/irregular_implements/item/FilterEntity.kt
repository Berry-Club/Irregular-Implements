package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class FilterEntity : Item(
    Properties()
        .stacksTo(1)
) {

    override fun interactLivingEntity(
        stack: ItemStack,
        player: Player,
        interactionTarget: LivingEntity,
        usedHand: InteractionHand
    ): InteractionResult {
        if (player.cooldowns.isOnCooldown(this)) {
            return InteractionResult.PASS
        }

        val usedStack = player.getItemInHand(usedHand)
        usedStack.set(
            ModDataComponents.ENTITY_TYPE.get(),
            interactionTarget.type
        )

        player.cooldowns.addCooldown(this, 1)

        return InteractionResult.SUCCESS
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)

        if (player.cooldowns.isOnCooldown(this)) {
            return InteractionResultHolder.pass(usedStack)
        }

        usedStack.set(
            ModDataComponents.ENTITY_TYPE.get(),
            player.type
        )

        player.cooldowns.addCooldown(this, 1)

        return InteractionResultHolder.success(usedStack)
    }

}