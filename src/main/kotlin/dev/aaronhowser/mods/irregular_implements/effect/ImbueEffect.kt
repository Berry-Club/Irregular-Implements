package dev.aaronhowser.mods.irregular_implements.effect

import dev.aaronhowser.mods.irregular_implements.registries.ModEffects
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent

class ImbueEffect : MobEffect(
    MobEffectCategory.BENEFICIAL,
    0x00FFFFFF  // TODO: Set color
) {

    // You can only have one Imbue at a time
    override fun onEffectStarted(livingEntity: LivingEntity, amplifier: Int) {
        val currentPotions = livingEntity.activeEffects.toList()

        for (effect in currentPotions) {
            if (effect.effect.value() is ImbueEffect && effect.effect.value() != this) {
                livingEntity.removeEffect(effect.effect)
            }
        }
    }

    companion object {
        fun handleAttacks(event: LivingDamageEvent.Post) {
            val damageSource = event.source

            if (!damageSource.isDirect) return
            if (!damageSource.`is`(DamageTypes.MOB_ATTACK) && !damageSource.`is`(DamageTypes.PLAYER_ATTACK)) return
            val attacker = event.source.entity as? LivingEntity ?: return

            val attackerImbue = attacker.activeEffects.firstOrNull { it.effect.value() is ImbueEffect }?.effect?.value() ?: return

            when (attackerImbue) {
                ModEffects.FIRE_IMBUE.get() -> event.entity.igniteForSeconds(5f)

                //TODO: Check duration and amplifier
                ModEffects.POISON_IMBUE.get() -> event.entity.addEffect(MobEffectInstance(MobEffects.POISON, 20 * 5, 0))
                ModEffects.WITHER_IMBUE.get() -> event.entity.addEffect(MobEffectInstance(MobEffects.WITHER, 20 * 5, 0))

                ModEffects.COLLAPSE_IMBUE.get() -> TODO("Do something if they have Collapse Imbue")
            }
        }
    }

}