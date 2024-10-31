package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.RainShieldChunks
import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.tick.LevelTickEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID
)
object OtherEvents {

    @SubscribeEvent
    fun onChat(event: ServerChatEvent) {
        val isRaining = event.player.level().isRainingAt(event.player.blockPosition())
        event.player.sendSystemMessage(Component.literal("Is raining: $isRaining"))
    }

    @SubscribeEvent
    fun beforeLevelTick(event: LevelTickEvent.Pre) {
        val level = event.level
        if (level is RainShieldChunks) {
            level.`irregular_implements$clearRainShieldChunks`()
        }
    }

}