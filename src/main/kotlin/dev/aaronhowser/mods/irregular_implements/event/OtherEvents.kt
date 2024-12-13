package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.CompressedSlimeBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactButtonBlock
import dev.aaronhowser.mods.irregular_implements.block.ContactLeverBlock
import dev.aaronhowser.mods.irregular_implements.effect.ImbueEffect
import dev.aaronhowser.mods.irregular_implements.item.BlockMoverItem
import dev.aaronhowser.mods.irregular_implements.item.ModArmorItems
import dev.aaronhowser.mods.irregular_implements.item.WhiteStoneItem
import dev.aaronhowser.mods.irregular_implements.util.RedstoneHandlerSavedData
import net.minecraft.world.InteractionHand
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.event.level.BlockEvent
import net.neoforged.neoforge.event.tick.LevelTickEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object OtherEvents {

    @SubscribeEvent
    fun afterEntityDamaged(event: LivingDamageEvent.Post) {
        ImbueEffect.handleAttackImbues(event)
    }

    @SubscribeEvent
    fun onLivingIncomingDamage(event: LivingIncomingDamageEvent) {
        ImbueEffect.handleDamageImbue(event)
        ModArmorItems.tryBlockFireDamage(event)
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
            ContactLeverBlock.handleClickBlock(level, pos)
            ContactButtonBlock.handleClickBlock(level, pos)
        }
    }

    @SubscribeEvent
    fun onBlockToolModification(event: BlockEvent.BlockToolModificationEvent) {
        CompressedSlimeBlock.modifySlimeBlock(event)
    }

    @SubscribeEvent
    fun onLevelTick(event: LevelTickEvent.Post) {
        RedstoneHandlerSavedData.tick(event.level)
    }

    @SubscribeEvent
    fun entityJoinLevel(event: EntityJoinLevelEvent) {
        BlockMoverItem.handleEntityJoinLevel(event)
    }

    @SubscribeEvent
    fun onLivingDeath(event: LivingDeathEvent) {
        WhiteStoneItem.tryPreventDeath(event)
    }

}