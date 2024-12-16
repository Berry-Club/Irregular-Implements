package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.ItemContainerContents

//FIXME: Outdated info page
class VoidStoneItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun overrideOtherStackedOnMe(
        thisStack: ItemStack,
        other: ItemStack,
        slot: Slot,
        action: ClickAction,
        player: Player,
        access: SlotAccess
    ): Boolean {
        if (action != ClickAction.SECONDARY
            || !slot.allowModification(player)
            || other.isEmpty
        ) return false

        thisStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(listOf(other.copy())))

        other.count = 0

        player.level().playSound(
            if (player.level().isClientSide) player else null,
            player.blockPosition(),
            SoundEvents.ENDERMAN_TELEPORT,
            SoundSource.PLAYERS,
            1f,
            0.3f
        )

        return true
    }

    override fun overrideStackedOnOther(
        thisStack: ItemStack,
        slot: Slot,
        action: ClickAction,
        player: Player
    ): Boolean {
        if (action != ClickAction.SECONDARY
            || !slot.allowModification(player)
            || !thisStack.has(DataComponents.CONTAINER)
        ) return false

        val otherStack = slot.item
        if (!otherStack.isEmpty) return false

        val storedItem = thisStack.get(DataComponents.CONTAINER)?.nonEmptyItems()?.firstOrNull() ?: return false

        slot.set(storedItem.copy())
        thisStack.remove(DataComponents.CONTAINER)

        player.level().playSound(
            if (player.level().isClientSide) player else null,
            player.blockPosition(),
            SoundEvents.ENDERMAN_TELEPORT,
            SoundSource.PLAYERS,
            1f,
            0.7f
        )

        return true
    }

    //FIXME: Too long, maybe move to info page?
    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        tooltipComponents.add(ModLanguageProvider.Tooltips.VOID_STONE_INSERT.toGrayComponent())

        val heldItem = stack.get(DataComponents.CONTAINER)?.nonEmptyItems()?.firstOrNull()
        if (heldItem != null) {
            tooltipComponents.add(ModLanguageProvider.Tooltips.VOID_STONE_HOLDING.toGrayComponent(heldItem.displayName))
            tooltipComponents.add(ModLanguageProvider.Tooltips.VOID_STONE_REMOVE.toGrayComponent(heldItem.displayName))
        }

    }

}