package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.client.CommonItemColor
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import net.minecraft.world.item.DyeColor
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    bus = EventBusSubscriber.Bus.MOD,
    value = [Dist.CLIENT]
)
object ClientModBusEvents {

    @SubscribeEvent
    fun registerItemColors(event: RegisterColorHandlersEvent.Item) {

        for (dyeColor in DyeColor.entries) {
            val seedItem = GrassSeedItem.getFromColor(dyeColor).get()

            event.register(CommonItemColor.getFromColor(dyeColor), seedItem)
        }

    }

}