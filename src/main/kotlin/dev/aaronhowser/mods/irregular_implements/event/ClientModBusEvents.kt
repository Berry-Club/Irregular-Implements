package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.client.Minecraft
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.block.BlockColors
import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.GrassColor
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

    private fun getItemColorFromDye(dyeColor: DyeColor): ItemColor {
        return ItemColor { _, _ -> dyeColor.textureDiffuseColor }
    }

    @SubscribeEvent
    fun registerItemColors(event: RegisterColorHandlersEvent.Item) {
        for (dyeColor in DyeColor.entries) {
            val seedItem = GrassSeedItem.getFromColor(dyeColor).get()
            val runeDustItem = ModItems.getRuneDust(dyeColor).get()
            val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor).get()

            val colorFunction = getItemColorFromDye(dyeColor)

            event.register(colorFunction, seedItem)
            event.register(colorFunction, runeDustItem)
            event.register(colorFunction, coloredGrassBlock)
        }

        event.register(getItemColorFromDye(DyeColor.LIME), ModItems.GRASS_SEEDS)
    }

    private val blockColors: BlockColors by lazy { Minecraft.getInstance().blockColors }

    private fun getBlockColorFromDye(dyeColor: DyeColor): BlockColor {
        return BlockColor { _, _, _, _ -> dyeColor.textureDiffuseColor }
    }

    @SubscribeEvent
    fun registerBlockColors(event: RegisterColorHandlersEvent.Block) {
        for (dyeColor in DyeColor.entries) {
            val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor).get()

            event.register(
                getBlockColorFromDye(dyeColor),
                coloredGrassBlock
            )
        }
    }

}