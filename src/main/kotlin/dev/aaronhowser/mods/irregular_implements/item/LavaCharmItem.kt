package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModCurioProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

class LavaCharmItem : Item(
    Properties()
        .stacksTo(1)
        .rarity(Rarity.RARE)
        .component(ModDataComponents.COOLDOWN, 0)
        .component(ModDataComponents.CHARGE, MAX_CHARGE)
), ICurioItem {

    companion object {
        const val MAX_CHARGE = 200
        const val MAX_COOLDOWN = 40

        private fun charge(stack: ItemStack) {
            val charge = stack.get(ModDataComponents.CHARGE) ?: 0
            val cooldown = stack.get(ModDataComponents.COOLDOWN) ?: MAX_COOLDOWN

            if (cooldown >= 0) {
                stack.set(ModDataComponents.COOLDOWN, cooldown - 1)
                return
            }

            if (charge < MAX_CHARGE) {
                stack.set(ModDataComponents.CHARGE, charge + 1)
            }
        }

        fun tryBlockLavaDamage(event: LivingIncomingDamageEvent) {
            if (event.isCanceled || !event.source.`is`(DamageTypes.LAVA)) return

            val entity = event.entity

            val lavaCharmStack = getFirstLavaBlockingItem(entity) ?: return

        }

        private fun isChargedLavaCharm(stack: ItemStack): Boolean {
            return stack.`is`(ModItems.LAVA_CHARM) && (stack.get(ModDataComponents.CHARGE) ?: 0) > 0
        }

        private fun getFirstLavaBlockingItem(entity: LivingEntity): ItemStack? {
            var lavaCharmStack: ItemStack? = null

            if (entity is Player) {
                CuriosApi.getCuriosInventory(entity).ifPresent { inventory ->
                    inventory.getStacksHandler(ModCurioProvider.RING_SLOT).ifPresent { ringSlotHandler ->
                        for (i in 0 until ringSlotHandler.slots) {
                            val stack = ringSlotHandler.stacks.getStackInSlot(i)
                            if (isChargedLavaCharm(stack)) {
                                lavaCharmStack = stack
                                break
                            }
                        }
                    }
                }

                if (lavaCharmStack != null) return lavaCharmStack

                lavaCharmStack = entity.inventory.items.firstOrNull { isChargedLavaCharm(it) }
            }

            if (lavaCharmStack != null) return lavaCharmStack

            return entity.handSlots.firstOrNull { isChargedLavaCharm(it) }
        }

    }

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        charge(stack)
    }

    override fun curioTick(slotContext: SlotContext, stack: ItemStack) {
        charge(stack)
    }

}