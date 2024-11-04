package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object OtherEvents {

    @SubscribeEvent
    fun afterEntityDamaged(event: LivingDamageEvent.Post) {
        ImbueEffect.handleAttackImbues(event)
    }

    @SubscribeEvent
    fun entityXpDrop(event: LivingExperienceDropEvent) {
        ImbueEffect.handleXpImbue(event)
    }

}