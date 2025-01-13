package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class FilterEntityItem : Item(
    Properties()
        .stacksTo(1)
) {

    companion object {
        fun getEntityType(stack: ItemStack): EntityType<*>? {
            return stack.get(ModDataComponents.ENTITY_TYPE)
        }

        fun setEntityType(stack: ItemStack, entityType: EntityType<*>) {
            stack.set(ModDataComponents.ENTITY_TYPE, entityType)
        }
    }

    override fun interactLivingEntity(
        stack: ItemStack,
        player: Player,
        interactionTarget: LivingEntity,
        usedHand: InteractionHand
    ): InteractionResult {
        val usedStack = player.getItemInHand(usedHand)
        setEntityType(usedStack, interactionTarget.type)
        return InteractionResult.SUCCESS
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)
        setEntityType(usedStack, player.type)
        return InteractionResultHolder.success(usedStack)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        val entityName = stack.get(ModDataComponents.ENTITY_TYPE)?.description

        if (entityName != null) {
            val component = ModLanguageProvider.Tooltips.ENTITY_FILTER_ENTITY
                .toGrayComponent(entityName)

            tooltipComponents.add(component)
        }
    }

}