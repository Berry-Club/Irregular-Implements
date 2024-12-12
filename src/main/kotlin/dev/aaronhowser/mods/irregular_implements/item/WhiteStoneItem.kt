package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.registry.ModSounds
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import java.awt.Color

class WhiteStoneItem : Item(
    Properties()
        .rarity(Rarity.UNCOMMON)
        .stacksTo(1)
        .component(ModDataComponents.CHARGE, 0)
) {

    companion object {

        private const val MAX_CHARGE = 20 * 100

        fun isChargedWhiteStone(itemStack: ItemStack): Boolean {
            return itemStack.`is`(ModItems.WHITE_STONE.get()) && itemStack.get(ModDataComponents.CHARGE.get()) == MAX_CHARGE
        }

        fun tryPreventDeath(event: LivingDeathEvent) {
            if (event.isCanceled) return

            val entity = event.entity

            val whiteStone = if (entity is Player) {
                entity.inventory.items.find { isChargedWhiteStone(it) } ?: return
            } else {
                entity.handSlots.find { isChargedWhiteStone(it) } ?: return
            }

            event.isCanceled = true
            whiteStone.set(ModDataComponents.CHARGE, 0)

            entity.level().playSound(
                null,
                entity.blockPosition(),
                ModSounds.WHITE_STONE_ACTIVATE.get(),
                entity.soundSource,
            )

            entity.addEffect(MobEffectInstance(MobEffects.REGENERATION, 10 * 20))
            entity.addEffect(MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10 * 20))
            entity.addEffect(MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10 * 20, 1))

            entity.health = entity.maxHealth
        }
    }

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (level.isClientSide
            || level.moonPhase != 0
            || level.dayTime !in 14000..23000
            || isChargedWhiteStone(stack)
            || !level.canSeeSky(entity.blockPosition())
        ) return

        val currentCharge = stack.get(ModDataComponents.CHARGE.get()) ?: 0
        val newCharge = currentCharge + 1

        stack.set(ModDataComponents.CHARGE, newCharge)

        if (newCharge == MAX_CHARGE) {
            level.playSound(
                null,
                entity.blockPosition(),
                SoundEvents.ZOMBIE_VILLAGER_CONVERTED,
                entity.soundSource,
            )
        }
    }

    override fun isFoil(stack: ItemStack): Boolean {
        return isChargedWhiteStone(stack)
    }

    override fun getBarColor(stack: ItemStack): Int {
        return Color.WHITE.rgb
    }

    override fun isBarVisible(stack: ItemStack): Boolean {
        val charge = stack.get(ModDataComponents.CHARGE.get())
        return charge != null && charge > 0 && charge < MAX_CHARGE
    }

    override fun getBarWidth(stack: ItemStack): Int {
        val charge = stack.get(ModDataComponents.CHARGE.get()) ?: return 0

        return (charge / MAX_CHARGE.toFloat() * 13).toInt()
    }

    override fun shouldCauseReequipAnimation(oldStack: ItemStack, newStack: ItemStack, slotChanged: Boolean): Boolean {
        return slotChanged && super.shouldCauseReequipAnimation(oldStack, newStack, true)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        val charge = stack.get(ModDataComponents.CHARGE.get()) ?: 0
        val percentCharge = charge / MAX_CHARGE.toFloat() * 100

        tooltipComponents.add(
            Component
                .literal("Charge: $percentCharge%")
        )
    }

}