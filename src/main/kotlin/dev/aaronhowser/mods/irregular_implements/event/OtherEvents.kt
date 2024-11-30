package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.ContactButton
import dev.aaronhowser.mods.irregular_implements.block.ContactLever
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import dev.aaronhowser.mods.irregular_implements.item.FluidWalkingArmorItem
import net.minecraft.world.InteractionHand
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent

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

    @SubscribeEvent
    fun onClickBlock(event: PlayerInteractEvent.RightClickBlock) {
        val level = event.level
        val pos = event.pos

        if (event.hand == InteractionHand.MAIN_HAND) {
            ContactLever.handleClickBlock(level, pos)
            ContactButton.handleClickBlock(level, pos)
        }
    }

    @SubscribeEvent
    fun onLivingTick(event: EntityTickEvent.Post) {
        FluidWalkingArmorItem.moveToSurface(event)
    }

}