package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.CompressedSlimeBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactButtonBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactLeverBlock
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import net.minecraft.world.InteractionHand
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent

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

        CompressedSlimeBlock.compressSlimeBlock(event)

        if (event.hand == InteractionHand.MAIN_HAND) {
            ContactLeverBlock.handleClickBlock(level, pos)
            ContactButtonBlock.handleClickBlock(level, pos)
        }
    }

}