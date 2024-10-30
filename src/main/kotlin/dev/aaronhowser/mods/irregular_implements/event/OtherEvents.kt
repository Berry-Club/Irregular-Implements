package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object OtherEvents {

    @SubscribeEvent
    fun onChat(event: ServerChatEvent) {
        event.player.sendSystemMessage(Component.literal("Is raining: ${event.player.level().isRainingAt(event.player.blockPosition())}"))
    }

    @SubscribeEvent
    fun afterServerTick(event: ServerTickEvent.Post) {

        // Runs on client too
        RainShieldBlockEntity.rainCache.clear()

    }

}