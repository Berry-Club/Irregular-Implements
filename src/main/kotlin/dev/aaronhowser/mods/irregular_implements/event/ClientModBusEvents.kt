package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.renderer.CustomCraftingTableBER
import dev.aaronhowser.mods.irregular_implements.block.renderer.CustomCraftingTableClientExtensions
import dev.aaronhowser.mods.irregular_implements.block.renderer.DiaphanousBER
import dev.aaronhowser.mods.irregular_implements.block.renderer.DiaphanousBlockClientExtensions
import dev.aaronhowser.mods.irregular_implements.item.GrassSeedItem
import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registries.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    bus = EventBusSubscriber.Bus.MOD,
    value = [Dist.CLIENT]
)
object ClientModBusEvents {

    private fun getItemColor(dyeColor: DyeColor): (ItemStack, Int) -> Int {
        return { _, _ -> dyeColor.textureDiffuseColor }
    }

    private fun getBlockFunction(dyeColor: DyeColor): (BlockState, BlockAndTintGetter?, BlockPos?, Int) -> Int {
        return { _, _, _, _ -> dyeColor.textureDiffuseColor }
    }

    @SubscribeEvent
    fun registerItemColors(event: RegisterColorHandlersEvent.Item) {
        for (dyeColor in DyeColor.entries) {
            val seedItem = GrassSeedItem.getFromColor(dyeColor).get()
            val runeDustItem = ModItems.getRuneDust(dyeColor).get()
            val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor).get()

            val colorFunction = getItemColor(dyeColor)

            event.register(colorFunction, seedItem)
            event.register(colorFunction, runeDustItem)
            event.register(colorFunction, coloredGrassBlock)
        }

        event.register(getItemColor(DyeColor.LIME), ModItems.GRASS_SEEDS)
    }

    @SubscribeEvent
    fun registerBlockColors(event: RegisterColorHandlersEvent.Block) {
        for (dyeColor in DyeColor.entries) {
            val colorFunction = getBlockFunction(dyeColor)

            //FIXME
            val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor).get()

            event.register(colorFunction, coloredGrassBlock)
        }
    }

    @SubscribeEvent
    fun registerEntityRenderer(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(
            ModBlockEntities.CUSTOM_CRAFTING_TABLE.get(),
            ::CustomCraftingTableBER
        )

        event.registerBlockEntityRenderer(
            ModBlockEntities.DIAPHANOUS_BLOCK.get(),
            ::DiaphanousBER
        )

    }

    @SubscribeEvent
    fun registerClientExtensions(event: RegisterClientExtensionsEvent) {
        event.registerItem(
            CustomCraftingTableClientExtensions(),
            ModBlocks.CUSTOM_CRAFTING_TABLE.get().asItem()
        )

        event.registerItem(
            DiaphanousBlockClientExtensions(),
            ModBlocks.DIAPHANOUS_BLOCK.get().asItem()
        )

    }

}