package dev.aaronhowser.mods.irregular_implements.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity

// You can only have one Imbue at a time
class ImbueEffect : MobEffect(
    MobEffectCategory.BENEFICIAL,
    0x00FFFFFF  // invisible
) {

    override fun onEffectStarted(livingEntity: LivingEntity, amplifier: Int) {
        val currentPotions = livingEntity.activeEffects.toList()

        for (effect in currentPotions) {
            if (effect.effect.value() is ImbueEffect && effect.effect.value() != this) {
                livingEntity.removeEffect(effect.effect)
            }
        }
    }

}