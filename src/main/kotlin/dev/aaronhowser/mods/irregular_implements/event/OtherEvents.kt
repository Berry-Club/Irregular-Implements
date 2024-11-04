package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModEntityTypeTagsProvider
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object OtherEvents {

    @SubscribeEvent
    fun onInteractEntity(event: PlayerInteractEvent.EntityInteract) {
        checkShouldCancel(event)
    }

    private fun checkShouldCancel(event: PlayerInteractEvent.EntityInteract) {
        val entity = event.target
        if (!entity.type.`is`(ModEntityTypeTagsProvider.ALLOWS_PREVENTING_INTERACTION)) return

        val mainHandStack = event.entity.getItemInHand(InteractionHand.MAIN_HAND)
        val offHandStack = event.entity.getItemInHand(InteractionHand.OFF_HAND)

        if (
            mainHandStack.`is`(ModItemTagsProvider.PREVENTS_SOME_MOB_INTERACTION)
            || offHandStack.`is`(ModItemTagsProvider.PREVENTS_SOME_MOB_INTERACTION)
        ) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onChat(event: ServerChatEvent) {
        val isRaining = event.player.level().isRainingAt(event.player.blockPosition())
        event.player.sendSystemMessage(Component.literal("Is raining: $isRaining"))
    }

}