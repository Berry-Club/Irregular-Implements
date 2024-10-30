package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.RainShieldBlockEntity
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    value = [Dist.CLIENT]
)
object ClientEvents {

    @SubscribeEvent
    fun afterClientTick(event: ClientTickEvent.Post) {

        // Runs on server too
        RainShieldBlockEntity.rainCache.clear()

    }

}