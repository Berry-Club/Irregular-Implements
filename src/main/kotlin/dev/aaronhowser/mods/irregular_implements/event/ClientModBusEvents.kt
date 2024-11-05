package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.client.DyedBlockColor
import dev.aaronhowser.mods.irregular_implements.client.DyedItemColor
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
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
            val itemColor = DyedItemColor.getFromColor(dyeColor)

            val seedItem = GrassSeedItem.getFromColor(dyeColor).get()
            val runeDustItem = ModItems.getRuneDust(dyeColor).get()

            event.register(itemColor, seedItem)
            event.register(itemColor, runeDustItem)
        }
    }

    @SubscribeEvent
    fun registerBlockColors(event: RegisterColorHandlersEvent.Block) {
        for (dyeColor in DyeColor.entries) {
            val blockColor = DyedBlockColor.getFromColor(dyeColor)

            //FIXME
            val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor).get()

            event.register(blockColor, coloredGrassBlock)
        }
    }

}