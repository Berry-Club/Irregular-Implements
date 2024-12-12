package dev.aaronhowser.mods.irregular_implements.effect

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects
import net.minecraft.tags.DamageTypeTags
import net.minecraft.util.Mth
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent

class ImbueEffect(
    color: Int
) : MobEffect(
    MobEffectCategory.BENEFICIAL,
    color
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
        fun handleAttackImbues(event: LivingDamageEvent.Post) {
            val damageSource = event.source

            if (!damageSource.isDirect) return
            if (!damageSource.`is`(DamageTypes.MOB_ATTACK) && !damageSource.`is`(DamageTypes.PLAYER_ATTACK)) return
            val attacker = event.source.entity as? LivingEntity ?: return

            val attackerImbue = attacker.activeEffects.firstOrNull { it.effect.value() is ImbueEffect }?.effect?.value() ?: return

            when (attackerImbue) {
                ModEffects.FIRE_IMBUE.get() -> event.entity.igniteForSeconds(10f)

                ModEffects.POISON_IMBUE.get() -> event.entity.addEffect(MobEffectInstance(MobEffects.POISON, 20 * 10, 1))
                ModEffects.WITHER_IMBUE.get() -> event.entity.addEffect(MobEffectInstance(MobEffects.WITHER, 20 * 10, 1))

                ModEffects.COLLAPSE_IMBUE.get() -> TODO("Do something if they have Collapse Imbue")
            }
        }

        fun handleDamageImbue(event: LivingIncomingDamageEvent) {
            if (event.isCanceled) return

            val entity = event.entity
            val damageSource = event.source

            if (damageSource.`is`(DamageTypeTags.BYPASSES_EFFECTS)
                || damageSource.`is`(DamageTypeTags.BYPASSES_RESISTANCE)
                || damageSource.`is`(DamageTypeTags.BYPASSES_INVULNERABILITY)
            ) return

            if (entity.hasEffect(ModEffects.SPECTRE_IMBUE)
                && entity.random.nextFloat() <= ServerConfig.SPECTRE_IMBUE_CHANCE.get()
            ) {
                event.isCanceled = true
                entity.hurtMarked = true    //TODO: Is this correctly marking them as invulnerable?
            }
        }

        fun handleXpImbue(event: LivingExperienceDropEvent) {
            val attacker = event.attackingPlayer ?: return

            if (attacker.hasEffect(ModEffects.EXPERIENCE_IMBUE)) {
                event.droppedExperience = Mth.ceil(event.droppedExperience * 1.5f)
            }
        }
    }

}