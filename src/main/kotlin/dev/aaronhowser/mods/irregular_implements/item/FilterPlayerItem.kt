package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.item.component.EntityIdentifierItemComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
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
import java.util.*

class FilterPlayerItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {
        fun getPlayerName(stack: ItemStack): Component? {
            return stack.get(ModDataComponents.PLAYER)?.name
        }

        fun getPlayerUuid(stack: ItemStack): UUID? {
            return stack.get(ModDataComponents.PLAYER)?.uuid
        }

        fun setPlayer(stack: ItemStack, player: Player) {
            stack.set(
                ModDataComponents.PLAYER.get(),
                EntityIdentifierItemComponent(player)
            )
        }
    }

    override fun interactLivingEntity(
        stack: ItemStack,
        player: Player,
        interactionTarget: LivingEntity,
        usedHand: InteractionHand
    ): InteractionResult {
        if (interactionTarget !is Player) return InteractionResult.PASS
        if (player.cooldowns.isOnCooldown(this)) return InteractionResult.PASS

        val usedStack = player.getItemInHand(usedHand)
        setPlayer(usedStack, interactionTarget)

        player.cooldowns.addCooldown(this, 1)

        return InteractionResult.SUCCESS
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)
        if (player.isClientSide) return InteractionResultHolder.pass(usedStack)

        if (player.cooldowns.isOnCooldown(this)) return InteractionResultHolder.pass(usedStack)

        setPlayer(usedStack, player)

        player.cooldowns.addCooldown(this, 1)

        return InteractionResultHolder.success(usedStack)
    }

    override fun getName(stack: ItemStack): Component {
        val playerName = getPlayerName(stack)
        return if (playerName != null) {
            ModLanguageProvider.Items.PLAYER_FILTER_SET
                .toComponent(playerName)
        } else {
            ModLanguageProvider.Items.PLAYER_FILTER_UNSET
                .toComponent()
        }
    }

    //TODO: Remove? Put in info recipe?
    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        OtherUtil.moreInfoTooltip(
            tooltipComponents,
            tooltipFlag,
            ModLanguageProvider.Tooltips.PLAYER_FILTER_CONTROLS.toGrayComponent()
        )
    }

}