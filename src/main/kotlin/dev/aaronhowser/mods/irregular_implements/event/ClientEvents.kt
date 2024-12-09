package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.ModArmorItems
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    value = [Dist.CLIENT]
)
object ClientEvents {

    @SubscribeEvent
    fun tooltipEvent(event: ItemTooltipEvent) {
        ModArmorItems.tooltip(event)
    }

    @SubscribeEvent
    fun onPlayerTurn(event: CalculatePlayerTurnEvent) {
        val player = ClientUtil.localPlayer ?: return

        //TODO: Add a config to disable this part (or just remove it entirely)
        if (player.hasEffect(ModEffects.COLLAPSE_IMBUE)) {
            event.mouseSensitivity *= -1.75
        }
    }

}