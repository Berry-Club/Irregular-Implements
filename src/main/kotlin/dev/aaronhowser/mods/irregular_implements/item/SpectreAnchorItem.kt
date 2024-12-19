package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.attachment.DeathKeptItems
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModAttachmentTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.ChatFormatting
import net.minecraft.util.Unit
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.GameRules
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import top.theillusivec4.curios.api.CuriosApi

class SpectreAnchorItem : Item(
    Properties()
        .stacksTo(1)
        .component(ModDataComponents.ANCHORED, Unit.INSTANCE)
) {

    companion object {

        fun saveAnchoredItems(player: Player) {

            val level = player.level()
            if (level.isClientSide || level.gameRules.getBoolean(GameRules.RULE_KEEPINVENTORY) || level.levelData.isHardcore) return

            val anchoredItems = player.inventory.items.filter { it.has(ModDataComponents.ANCHORED) }.toMutableList()
            for (item in anchoredItems) {
                player.inventory.removeItem(item)
            }

            CuriosApi.getCuriosInventory(player).ifPresent { inventory ->
                for (slot in 0 until inventory.equippedCurios.slots) {
                    val stack = inventory.equippedCurios.getStackInSlot(slot)
                    if (stack.has(ModDataComponents.ANCHORED)) {
                        anchoredItems.add(stack)
                        inventory.equippedCurios.setStackInSlot(slot, ItemStack.EMPTY)
                    }
                }
            }

            player.setData(ModAttachmentTypes.DEATH_KEPT_ITEMS, DeathKeptItems(anchoredItems))
        }

        fun returnItems(player: Player) {
            val items = player.getData(ModAttachmentTypes.DEATH_KEPT_ITEMS.get()).stacks
            if (items.isEmpty()) return

            for (item in items) {
                if (!item.`is`(ModItems.SPECTRE_ANCHOR)) {
                    item.remove(ModDataComponents.ANCHORED)
                }

                if (!player.inventory.add(item)) {
                    val itemEntity = ItemEntity(player.level(), player.x, player.y, player.z, item)
                    player.level().addFreshEntity(itemEntity)
                }
            }

            player.setData(ModAttachmentTypes.DEATH_KEPT_ITEMS, DeathKeptItems(emptyList()))
        }

        fun tooltip(event: ItemTooltipEvent) {
            val stack = event.itemStack
            if (!stack.has(ModDataComponents.ANCHORED)) return

            event.toolTip.add(
                ModLanguageProvider.Tooltips.ANCHORED
                    .toComponent()
                    .withStyle(ChatFormatting.DARK_AQUA)
            )
        }

    }

}