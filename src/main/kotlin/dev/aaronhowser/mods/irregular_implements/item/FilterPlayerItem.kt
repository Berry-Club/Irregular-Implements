package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.component.SpecificEntityItemComponent
import dev.aaronhowser.mods.irregular_implements.registries.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class FilterPlayerItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun interactLivingEntity(
        stack: ItemStack,
        player: Player,
        interactionTarget: LivingEntity,
        usedHand: InteractionHand
    ): InteractionResult {
        if (interactionTarget !is Player) return InteractionResult.PASS
        if (player.cooldowns.isOnCooldown(this)) return InteractionResult.PASS

        val usedStack = player.getItemInHand(usedHand)
        usedStack.set(
            ModDataComponents.PLAYER.get(),
            SpecificEntityItemComponent(interactionTarget)
        )

        player.cooldowns.addCooldown(this, 1)

        return InteractionResult.SUCCESS
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)
        if (player.isClientSide) return InteractionResultHolder.pass(usedStack)

        if (player.cooldowns.isOnCooldown(this)) return InteractionResultHolder.pass(usedStack)

        usedStack.set(
            ModDataComponents.PLAYER.get(),
            SpecificEntityItemComponent(player)
        )

        player.cooldowns.addCooldown(this, 1)

        return InteractionResultHolder.success(usedStack)
    }

    override fun getName(stack: ItemStack): Component {

        val playerName = stack.get(ModDataComponents.PLAYER.get())?.name
        return if (playerName != null) {
            ModLanguageProvider.Items.PLAYER_FILTER_SET
                .toComponent(playerName)
        } else {
            ModLanguageProvider.Items.PLAYER_FILTER_UNSET
                .toComponent()
        }
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        OtherUtil.moreInfoTooltip(
            tooltipComponents,
            tooltipFlag,
            ModLanguageProvider.Tooltips.PLAYER_FILTER_CONTROLS
                .toComponent()
                .withStyle(ChatFormatting.GRAY)
        )

        val playerName = stack.get(ModDataComponents.PLAYER.get())?.name
        if (playerName != null) {
            tooltipComponents.add(
                ModLanguageProvider.Tooltips.PLAYER_FILTER_PLAYER
                    .toComponent(playerName)
                    .withStyle(ChatFormatting.GRAY)
            )
        }

    }

}