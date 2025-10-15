package dev.aaronhowser.mods.irregular_implements.event

import com.mojang.blaze3d.systems.RenderSystem
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.client.SpectreSpecialEffects
import dev.aaronhowser.mods.irregular_implements.client.render.*
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.CustomCraftingTableBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.DiaphanousBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.SpecialChestBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.bewlr.SpectreIlluminatorBEWLR
import dev.aaronhowser.mods.irregular_implements.client.render.block_entity.*
import dev.aaronhowser.mods.irregular_implements.client.render.entity.*
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModDimensions
import dev.aaronhowser.mods.irregular_implements.item.*
import dev.aaronhowser.mods.irregular_implements.particle.FlooFlameParticle
import dev.aaronhowser.mods.irregular_implements.recipe.crafting.ApplyLuminousPowderRecipe
import dev.aaronhowser.mods.irregular_implements.registry.*
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.renderer.BiomeColors
import net.minecraft.client.renderer.blockentity.ChestRenderer
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.client.renderer.entity.NoopRenderer
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.client.renderer.item.CompassItemPropertyFunction
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.core.GlobalPos
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.GrassColor
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.*
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
import net.neoforged.neoforge.client.gui.VanillaGuiLayers
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

@EventBusSubscriber(
	modid = IrregularImplements.ID,
	value = [Dist.CLIENT]
)
object ClientEvents {

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
			val seedItem = GrassSeedItem.getFromColor(dyeColor)?.get()
			val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor)?.get()

			val colorFunction = getItemColorFromDye(dyeColor)

			if (seedItem != null) {
				event.register(colorFunction, seedItem)
			}

			if (coloredGrassBlock != null) {
				event.register(colorFunction, coloredGrassBlock)
			}
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

		event.register(
			BiomeCrystalItem::getItemColor,
			ModItems.BIOME_CRYSTAL.get()
		)

		event.register(
			EnderBucketItem::getItemColor,
			ModItems.ENDER_BUCKET.get(),
			ModItems.REINFORCED_ENDER_BUCKET.get()
		)

	}

	@SubscribeEvent
	fun registerBlockColors(event: RegisterColorHandlersEvent.Block) {
		for (dyeColor in DyeColor.entries) {
			val coloredGrassBlock = ModBlocks.getColoredGrass(dyeColor)?.get()

			if (coloredGrassBlock != null) {
				event.register(
					{ _, _, _, tintIndex ->
						if (tintIndex == 0) dyeColor.textureDiffuseColor else 0xFFFFFF
					},
					coloredGrassBlock
				)
			}
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
			CompassItemPropertyFunction { level, stack, holder ->
				val component = stack.get(ModDataComponents.PLAYER) ?: return@CompassItemPropertyFunction null

				val targetPlayer = level.getPlayerByUUID(component.uuid)

				if (targetPlayer != null) {
					if (targetPlayer === holder) return@CompassItemPropertyFunction null
					return@CompassItemPropertyFunction GlobalPos(targetPlayer.level().dimension(), targetPlayer.blockPosition())
				}

				return@CompassItemPropertyFunction stack.get(ModDataComponents.GLOBAL_POS)
			}
		)

		ItemProperties.register(
			ModItems.GOLDEN_COMPASS.get(),
			GoldenCompassItem.ANGLE,
			CompassItemPropertyFunction { level, stack, holder ->
				return@CompassItemPropertyFunction stack.get(ModDataComponents.GLOBAL_POS)
			}
		)

		ItemProperties.register(
			ModItems.REDSTONE_ACTIVATOR.get(),
			RedstoneActivatorItem.DURATION,
			RedstoneActivatorItem::getDurationFloat
		)

		for (item in listOf(
			ModItems.SPECTRE_CHARGER_BASIC.get(),
			ModItems.SPECTRE_CHARGER_REDSTONE.get(),
			ModItems.SPECTRE_CHARGER_ENDER.get(),
			ModItems.SPECTRE_CHARGER_GENESIS.get()
		)) {
			ItemProperties.register(
				item,
				SpectreChargerItem.IS_ENABLED,
				SpectreChargerItem::getEnabledForPredicate
			)
		}

		ItemProperties.register(
			ModItems.WEATHER_EGG.get(),
			WeatherEggItem.WEATHER,
			WeatherEggItem::getWeatherFloat
		)

		for (bucket in listOf(ModItems.ENDER_BUCKET.get(), ModItems.REINFORCED_ENDER_BUCKET.get())) {
			ItemProperties.register(
				bucket,
				EnderBucketItem.HAS_FLUID,
				EnderBucketItem::getHasFluidPredicate
			)
		}

	}

	@SubscribeEvent
	fun onClientSetup(event: FMLClientSetupEvent) {
		EntityRenderers.register(ModEntityTypes.SPECTRE_ILLUMINATOR.get(), ::SpectreIlluminatorEntityRenderer)
		EntityRenderers.register(ModEntityTypes.PORTKEY_ITEM.get(), ::PortkeyItemEntityRenderer)
		EntityRenderers.register(ModEntityTypes.TEMPORARY_FLOO_FIREPLACE.get(), ::NoopRenderer)
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
			RedstoneToolRenderer.WIRE_STRENGTH_UI_LAYER,
			RedstoneToolRenderer::tryRenderWireStrength
		)

		event.registerAbove(
			VanillaGuiLayers.CROSSHAIR,
			BiomeSensorRenderer.LAYER_NAME,
			BiomeSensorRenderer::tryRenderBiomeName
		)
	}

	@SubscribeEvent
	fun registerEntityRenderer(event: EntityRenderersEvent.RegisterRenderers) {
		event.registerBlockEntityRenderer(ModBlockEntities.DIAPHANOUS_BLOCK.get(), ::DiaphanousBlockEntityRenderer)
		event.registerBlockEntityRenderer(ModBlockEntities.SPECTRE_ENERGY_INJECTOR.get(), ::SpectreEnergyInjectorBlockEntityRenderer)
		event.registerBlockEntityRenderer(ModBlockEntities.CUSTOM_CRAFTING_TABLE.get(), ::CustomCraftingTableBlockEntityRenderer)
		event.registerBlockEntityRenderer(ModBlockEntities.NATURE_CHEST.get(), ::ChestRenderer)
		event.registerBlockEntityRenderer(ModBlockEntities.WATER_CHEST.get(), ::ChestRenderer)
		event.registerBlockEntityRenderer(ModBlockEntities.PLAYER_INTERFACE.get(), ::PlayerInterfaceBlockEntityRenderer)
		event.registerBlockEntityRenderer(ModBlockEntities.BIOME_RADAR.get(), ::BiomeRadarBlockEntityRenderer)

		event.registerEntityRenderer(ModEntityTypes.GOLDEN_EGG.get(), ::ThrownItemRenderer)
		event.registerEntityRenderer(ModEntityTypes.WEATHER_EGG.get(), ::ThrownItemRenderer)
		event.registerEntityRenderer(ModEntityTypes.ARTIFICIAL_END_PORTAL.get(), ::ArtificialEndPortalRenderer)
		event.registerEntityRenderer(ModEntityTypes.GOLDEN_CHICKEN.get(), ::GoldenChickenRenderer)
		event.registerEntityRenderer(ModEntityTypes.WEATHER_CLOUD.get(), ::WeatherCloudRenderer)
		event.registerEntityRenderer(ModEntityTypes.SPIRIT.get(), ::SpiritEntityRenderer)
	}

	@SubscribeEvent
	fun registerClientExtensions(event: RegisterClientExtensionsEvent) {
		event.registerItem(
			DiaphanousBEWLR.ClientItemExtensions,
			ModItems.DIAPHANOUS_BLOCK.get()
		)

		event.registerItem(
			CustomCraftingTableBEWLR.ClientItemExtensions,
			ModItems.CUSTOM_CRAFTING_TABLE.get()
		)

		event.registerItem(
			SpectreIlluminatorBEWLR.ClientItemExtensions,
			ModItems.SPECTRE_ILLUMINATOR.get()
		)

		event.registerItem(
			SpecialChestBEWLR.ClientItemExtensions,
			ModBlocks.NATURE_CHEST.get().asItem(),
			ModBlocks.WATER_CHEST.get().asItem()
		)
	}

	@SubscribeEvent
	fun onRegisterMenuScreens(event: RegisterMenuScreensEvent) {
		ModMenuTypes.registerScreens(event)
	}

	@SubscribeEvent
	fun tooltipEvent(event: ItemTooltipEvent) {
		ModArmorItems.lubricatedTooltip(event)
		SpectreAnchorItem.tooltip(event)
		ApplyLuminousPowderRecipe.tooltip(event)
	}

	private var isAlphaChanged = false

	@SubscribeEvent
	fun beforeRenderLiving(event: RenderLivingEvent.Pre<LivingEntity, HumanoidModel<LivingEntity>>) {
		val entity = event.entity

		if (ModArmorItems.isWearingFullSpectreArmor(entity)) {
			val alpha = Mth.sin(entity.tickCount.toFloat() * 0.025f) * 0.1f + 0.5f

			RenderSystem.enableBlend()
			RenderSystem.defaultBlendFunc()
			RenderSystem.setShaderColor(1f, 1f, 1f, alpha)

			isAlphaChanged = true
		}
	}

	@SubscribeEvent
	fun afterRenderLiving(event: RenderLivingEvent.Post<LivingEntity, HumanoidModel<LivingEntity>>) {
		if (isAlphaChanged) {
			RenderSystem.disableBlend()
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f)

			isAlphaChanged = false
		}
	}

	@SubscribeEvent
	fun registerParticleProviders(event: RegisterParticleProvidersEvent) {
		event.registerSpriteSet(ModParticleTypes.FLOO_FLAME.get(), FlooFlameParticle::Provider)
	}

	@SubscribeEvent
	fun registerDimensionSpecialEffects(event: RegisterDimensionSpecialEffectsEvent) {
		event.register(ModDimensions.SPECTRE_RL, SpectreSpecialEffects())
	}

	@SubscribeEvent
	fun afterClientTick(event: ClientTickEvent.Post) {
		CubeIndicatorRenderer.afterClientTick(event)
		LineIndicatorRenderer.afterClientTick(event)
	}

	@SubscribeEvent
	fun onRenderLevel(event: RenderLevelStageEvent) {
		CubeIndicatorRenderer.onRenderLevel(event)
		LineIndicatorRenderer.onRenderLevel(event)
	}

}