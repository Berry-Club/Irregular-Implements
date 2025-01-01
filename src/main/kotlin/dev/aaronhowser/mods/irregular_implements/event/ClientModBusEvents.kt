package dev.aaronhowser.mods.irregular_implements.event

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.block_entity.BiomeSensorBlockEntity
import dev.aaronhowser.mods.irregular_implements.client.render.LavaProtectionOverlayRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.RedstoneToolRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.block.DiaphanousBlockEntityRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.block.SpectreEnergyInjectorBlockEntityRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.entity.ArtificialEndPortalRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.entity.IlluminatorEntityRenderer
import dev.aaronhowser.mods.irregular_implements.client.render.item.DiaphanousBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.item.SpectreIlluminatorBEWLR
import dev.aaronhowser.mods.irregular_implements.item.*
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.renderer.BiomeColors
import net.minecraft.client.renderer.entity.DisplayRenderer.BlockDisplayRenderer
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.GrassColor
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.event.ModelEvent
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
import net.neoforged.neoforge.client.gui.VanillaGuiLayers

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    bus = EventBusSubscriber.Bus.MOD,
    value = [Dist.CLIENT]
)
object ClientModBusEvents {

    private fun getItemColorFromDye(dyeColor: DyeColor): ItemColor {
        return ItemColor { _, _ -> dyeColor.textureDiffuseColor }
    }

    private fun getLocalFoliageColor(stack: ItemStack, int: Int): Int {
        val localPlayer = ClientUtil.localPlayer
        val localFoliageColor = localPlayer?.level()
            ?.getBiome(localPlayer.blockPosition())
            ?.value()
            ?.foliageColor

        return localFoliageColor ?: GrassColor.getDefaultColor()
    }

    @SubscribeEvent
    fun registerItemColors(event: RegisterColorHandlersEvent.Item) {
        for (dyeColor in DyeColor.entries) {
            val seedItem = GrassSeedItem.getFromColor(dyeColor).get()
            val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor).get()

            val colorFunction = getItemColorFromDye(dyeColor)

            event.register(colorFunction, seedItem)
            event.register(colorFunction, coloredGrassBlock)
        }

        event.register(getItemColorFromDye(DyeColor.LIME), ModItems.GRASS_SEEDS)

        event.register(
            ::getLocalFoliageColor,
            ModBlocks.BIOME_GLASS.get(),
            ModBlocks.BIOME_STONE.get(),
            ModBlocks.BIOME_COBBLESTONE.get(),
            ModBlocks.BIOME_STONE_BRICKS.get(),
            ModBlocks.BIOME_STONE_BRICKS_CHISELED.get(),
            ModBlocks.BIOME_STONE_BRICKS_CRACKED.get()
        )

        event.register(
            BiomeCapsuleItem::getItemColor,
            ModItems.BIOME_CAPSULE.get()
        )

        event.register(
            DiviningRodItem::getItemColor,
            ModItems.DIVINING_ROD.get()
        )

    }

    //TODO: Make Rainbow Lamp use this instead of unique textures

    @SubscribeEvent
    fun registerBlockColors(event: RegisterColorHandlersEvent.Block) {
        for (dyeColor in DyeColor.entries) {
            val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor).get()

            event.register(
                { _, _, _, tintIndex ->
                    if (tintIndex == 0) dyeColor.textureDiffuseColor else 0xFFFFFF
                },
                coloredGrassBlock
            )
        }

        event.register(
            { _, level, pos, _ ->
                if (level == null || pos == null) {
                    GrassColor.getDefaultColor()
                } else {
                    BiomeColors.getAverageGrassColor(level, pos)
                }
            },
            ModBlocks.BIOME_GLASS.get(),
            ModBlocks.BIOME_STONE.get(),
            ModBlocks.BIOME_COBBLESTONE.get(),
            ModBlocks.BIOME_STONE_BRICKS.get(),
            ModBlocks.BIOME_STONE_BRICKS_CHISELED.get(),
            ModBlocks.BIOME_STONE_BRICKS_CRACKED.get()
        )
    }

    @SubscribeEvent
    fun onModelRegistry(event: ModelEvent.RegisterAdditional) {

        ItemProperties.register(
            ModItems.EMERALD_COMPASS.get(),
            EmeraldCompassItem.ANGLE,
            EmeraldCompassItem::getAngleFloat
        )

        ItemProperties.register(
            ModItems.REDSTONE_ACTIVATOR.get(),
            RedstoneActivatorItem.DURATION,
            RedstoneActivatorItem::getDurationFloat
        )

    }

    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent) {
        EntityRenderers.register(ModEntityTypes.INDICATOR_DISPLAY.get(), ::BlockDisplayRenderer)
        EntityRenderers.register(ModEntityTypes.ILLUMINATOR.get(), ::IlluminatorEntityRenderer)
    }

    @SubscribeEvent
    fun registerGuiLayers(event: RegisterGuiLayersEvent) {
        event.registerAbove(
            VanillaGuiLayers.AIR_LEVEL,
            LavaProtectionOverlayRenderer.LAYER_NAME,
            LavaProtectionOverlayRenderer::tryRender
        )

        event.registerAbove(
            VanillaGuiLayers.CROSSHAIR,
            RedstoneToolRenderer.LAYER_NAME,
            RedstoneToolRenderer::tryRenderWireStrength
        )

        event.registerAbove(
            VanillaGuiLayers.CROSSHAIR,
            BiomeSensorBlockEntity.LAYER_NAME,
            BiomeSensorBlockEntity::tryRenderBiomeName
        )
    }

    @SubscribeEvent
    fun registerEntityRenderer(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(
            ModBlockEntities.DIAPHANOUS_BLOCK.get(),
            ::DiaphanousBlockEntityRenderer
        )

        event.registerBlockEntityRenderer(
            ModBlockEntities.SPECTRE_ENERGY_INJECTOR.get(),
            ::SpectreEnergyInjectorBlockEntityRenderer
        )

        event.registerEntityRenderer(
            ModEntityTypes.ARTIFICIAL_END_PORTAL.get(),
            ::ArtificialEndPortalRenderer
        )
    }

    @SubscribeEvent
    fun registerClientExtensions(event: RegisterClientExtensionsEvent) {
        event.registerItem(
            DiaphanousBEWLR.clientItemExtensions,
            ModItems.DIAPHANOUS_BLOCK.get()
        )

        event.registerItem(
            SpectreIlluminatorBEWLR.clientItemExtensions,
            ModItems.SPECTRE_ILLUMINATOR.get()
        )
    }

}