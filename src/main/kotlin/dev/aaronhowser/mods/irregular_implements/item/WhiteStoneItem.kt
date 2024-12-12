package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent

class WhiteStoneItem : Item(
    Properties()
        .rarity(Rarity.UNCOMMON)
        .stacksTo(1)
        .component(ModDataComponents.ENABLED, false)
) {

    companion object {
        fun tryPreventDeath(event: LivingDeathEvent) {
            if (event.isCanceled) return

            val entity = event.entity

            val whiteStone = if (entity is Player) {
                entity.inventory.items.find {
                    it.item is WhiteStoneItem
                            && it.get(ModDataComponents.ENABLED.get()).isTrue
                } ?: return
            } else {
                entity.handSlots.find {
                    it.item is WhiteStoneItem
                            && it.get(ModDataComponents.ENABLED.get()).isTrue
                } ?: return
            }

            event.isCanceled = true
            whiteStone.set(ModDataComponents.ENABLED, false)

            entity.level().playSound(
                null,
                entity.blockPosition(),
                SoundEvents.ZOMBIE_VILLAGER_CURE,
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
            || level.gameTime % 20 != 0L
            || stack.get(ModDataComponents.ENABLED.get()).isTrue
            || level.moonPhase != 0
            || level.dayTime !in 17000..19000
            || !level.canSeeSky(entity.blockPosition())
        ) return

        level.playSound(
            null,
            entity.blockPosition(),
            SoundEvents.ZOMBIE_VILLAGER_CONVERTED,
            entity.soundSource,
        )

        stack.set(ModDataComponents.ENABLED, true)
    }

    override fun isFoil(stack: ItemStack): Boolean {
        return stack.get(ModDataComponents.ENABLED.get()).isTrue
    }

}