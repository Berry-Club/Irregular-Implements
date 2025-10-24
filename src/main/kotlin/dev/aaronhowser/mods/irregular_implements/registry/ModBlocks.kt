package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.*
import dev.aaronhowser.mods.irregular_implements.block.plate.*
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getDyeName
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModBlocks {

	val BLOCK_REGISTRY: DeferredRegister<Block> =
		DeferredRegister.create(ForgeRegistries.BLOCKS, IrregularImplements.ID)

	val FERTILIZED_DIRT: RegistryObject<FertilizedDirtBlock> =
		registerBlock("fertilized_dirt", ::FertilizedDirtBlock)
	val IMBUING_STATION: RegistryObject<ImbuingStationBlock> =
		registerBlock("imbuing_station", ::ImbuingStationBlock)
	val ENDER_MAILBOX: RegistryObject<EnderMailboxBlock> =
		registerBlock("ender_mailbox", ::EnderMailboxBlock)
	val POTION_VAPORIZER = basicBlock("potion_vaporizer")
	val RAIN_SHIELD: RegistryObject<RainShieldBlock> =
		registerBlock("rain_shield", ::RainShieldBlock)
	val COMPRESSED_SLIME_BLOCK: RegistryObject<CompressedSlimeBlock> =
		registerBlock("compressed_slime_block", ::CompressedSlimeBlock)
	val BIOME_RADAR: RegistryObject<BiomeRadarBlock> =
		registerBlock("biome_radar", ::BiomeRadarBlock)
	val INVENTORY_REROUTER = basicBlock("inventory_rerouter")
	val SLIME_CUBE: RegistryObject<SlimeCubeBlock> =
		registerBlock("slime_cube", ::SlimeCubeBlock)
	val PEACE_CANDLE: RegistryObject<PeaceCandleBlock> =
		registerBlock("peace_candle", ::PeaceCandleBlock)

	// Plants
	val NATURE_CORE: RegistryObject<NatureCoreBlock> =
		registerBlock("nature_core", ::NatureCoreBlock)
	val SAKANADE_SPORES: RegistryObject<SakanadeBlock> =
		registerBlock("sakanade_spores", ::SakanadeBlock)
	val PITCHER_PLANT: RegistryObject<PitcherPlantBlock> =
		registerBlock("pitcher_plant", ::PitcherPlantBlock)
	val LOTUS: RegistryObject<LotusBlock> =
		registerBlockWithoutItem("lotus", ::LotusBlock)
	val BEAN_SPROUT: RegistryObject<BeanSproutBlock> =
		registerBlockWithoutItem("bean_sprout", ::BeanSproutBlock)   //TODO: Naturally generating
	val BEAN_POD: RegistryObject<Block> =
		registerBlock("bean_pod") { Block(Properties.copy(Blocks.POTATOES).strength(0.5f)) }
	val GLOWING_MUSHROOM: RegistryObject<GlowingMushroomBlock> =
		registerBlock("glowing_mushroom", ::GlowingMushroomBlock)

	// Loot blocks
	val NATURE_CHEST: RegistryObject<SpecialChestBlock> =
		registerBlock("nature_chest") { SpecialChestBlock.Type.NATURE.block }
	val WATER_CHEST: RegistryObject<SpecialChestBlock> =
		registerBlock("water_chest") { SpecialChestBlock.Type.WATER.block }

	// Collectors
	val ITEM_COLLECTOR: RegistryObject<ItemCollectorBlock> =
		registerBlock("item_collector") { ItemCollectorBlock(isAdvanced = false) }
	val ADVANCED_ITEM_COLLECTOR: RegistryObject<ItemCollectorBlock> =
		registerBlock("advanced_item_collector") { ItemCollectorBlock(isAdvanced = true) }

	// Redstone blocks
	val ANALOG_EMITTER: RegistryObject<AnalogEmitterBlock> =
		registerBlock("analog_emitter", ::AnalogEmitterBlock)
	val CONTACT_BUTTON: RegistryObject<ContactButtonBlock> =
		registerBlock("contact_button", ::ContactButtonBlock)
	val CONTACT_LEVER: RegistryObject<ContactLeverBlock> =
		registerBlock("contact_lever", ::ContactLeverBlock)
	val IRON_DROPPER: RegistryObject<IronDropperBlock> =
		registerBlock("iron_dropper", ::IronDropperBlock)
	val IGNITER: RegistryObject<IgniterBlock> =
		registerBlock("igniter", ::IgniterBlock)
	val INVENTORY_TESTER: RegistryObject<InventoryTesterBlock> =
		registerBlock("inventory_tester", ::InventoryTesterBlock)
	val BLOCK_DESTABILIZER: RegistryObject<BlockDestabilizerBlock> =
		registerBlock("block_destabilizer", ::BlockDestabilizerBlock)
	val BLOCK_BREAKER: RegistryObject<BlockBreakerBlock> =
		registerBlock("block_breaker", ::BlockBreakerBlock)
	val REDSTONE_OBSERVER: RegistryObject<RedstoneObserverBlock> =
		registerBlock("redstone_observer", ::RedstoneObserverBlock)
	val SIDED_BLOCK_OF_REDSTONE =
		registerBlock("sided_redstone", ::SidedRedstoneBlock)
	val ADVANCED_REDSTONE_REPEATER = basicBlock("advanced_redstone_repeater")
	val ADVANCED_REDSTONE_TORCH: RegistryObject<AdvancedRedstoneTorchBlock> =
		registerBlockWithoutItem("advanced_redstone_torch", ::AdvancedRedstoneTorchBlock)
	val ADVANCED_REDSTONE_WALL_TORCH: RegistryObject<AdvancedRedstoneWallTorchBlock> =
		registerBlockWithoutItem("advanced_redstone_wall_torch", ::AdvancedRedstoneWallTorchBlock)

	// Sticks
	val BLOCK_OF_STICKS: RegistryObject<BlockOfSticksBlock> =
		registerBlock("block_of_sticks") { BlockOfSticksBlock(returning = false) }
	val RETURNING_BLOCK_OF_STICKS: RegistryObject<BlockOfSticksBlock> =
		registerBlock("returning_block_of_sticks") { BlockOfSticksBlock(returning = true) }

	// No Item
	val BLAZE_FIRE: RegistryObject<BlazeFireBlock> =
		registerBlockWithoutItem("blaze_fire", ::BlazeFireBlock)
	val LESSER_BEAN_STALK: RegistryObject<BeanStalkBlock> =
		registerBlockWithoutItem("lesser_bean_stalk") { BeanStalkBlock(isStrong = false) }
	val BEAN_STALK: RegistryObject<BeanStalkBlock> =
		registerBlockWithoutItem("bean_stalk") { BeanStalkBlock(isStrong = true) }
	val DIAPHANOUS_BLOCK: RegistryObject<DiaphanousBlock> =
		registerBlockWithoutItem("diaphanous_block", ::DiaphanousBlock)

	@JvmField
	val CUSTOM_CRAFTING_TABLE: RegistryObject<CustomCraftingTableBlock> =
		registerBlockWithoutItem("custom_crafting_table", ::CustomCraftingTableBlock)

	// Glass
	val TRIGGER_GLASS: RegistryObject<TriggerGlassBlock> =
		registerBlock("trigger_glass", ::TriggerGlassBlock)
	val LAPIS_GLASS: RegistryObject<PermeableGlassBlock> =
		registerBlock("lapis_glass") { PermeableGlassBlock.LAPIS }
	val QUARTZ_GLASS: RegistryObject<PermeableGlassBlock> =
		registerBlock("quartz_glass") { PermeableGlassBlock.QUARTZ }

	// Lamp
	val RAINBOW_LAMP =
		registerBlock("rainbow_lamp", ::RainbowLampBlock)    //TODO: animate the item color

	// Ender Bridge
	val ENDER_BRIDGE: RegistryObject<EnderBridgeBlock> =
		registerBlock("ender_bridge") { EnderBridgeBlock(distancePerTick = 1) }
	val PRISMARINE_ENDER_BRIDGE: RegistryObject<EnderBridgeBlock> =
		registerBlock("prismarine_ender_bridge") { EnderBridgeBlock(distancePerTick = 2) }
	val ENDER_ANCHOR: RegistryObject<Block> =
		basicCopiedBlock("ender_anchor", Blocks.OBSIDIAN)

	// Lubricant
	val SUPER_LUBRICANT_ICE: RegistryObject<GlassBlock> =
		basicGlassBlock("super_lubricant_ice")
	val SUPER_LUBRICANT_STONE: RegistryObject<Block> =
		basicStoneBlock("super_lubricant_stone")

	// Not above 1.7
	val ENERGY_DISTRIBUTOR: RegistryObject<EnergyDistributorBlock> =
		registerBlock("energy_distributor", ::EnergyDistributorBlock)
	val ENDER_ENERGY_DISTRIBUTOR: RegistryObject<EnderEnergyDistributorBlock> =
		registerBlock("ender_energy_distributor", ::EnderEnergyDistributorBlock)

	// Not above 1.6.4
	val SHOCK_ABSORBER: RegistryObject<ShockAbsorberBlock> =
		registerBlock("shock_absorber", ::ShockAbsorberBlock)
	val AUTO_PLACER: RegistryObject<AutoPlacerBlock> =
		registerBlock("auto_placer", ::AutoPlacerBlock)

	val BLOCK_TELEPORTER: RegistryObject<BlockTeleporterBlock> =
		registerBlock("block_teleporter", ::BlockTeleporterBlock)
	val BLOCK_DETECTOR: RegistryObject<BlockDetectorBlock> =
		registerBlock("block_detector", ::BlockDetectorBlock)
	val MOON_PHASE_DETECTOR: RegistryObject<MoonPhaseDetectorBlock> =
		registerBlock("moon_phase_detector", ::MoonPhaseDetectorBlock)

	// Detectors
	val ONLINE_DETECTOR: RegistryObject<OnlineDetectorBlock> =
		registerBlock("online_detector", ::OnlineDetectorBlock)
	val CHAT_DETECTOR: RegistryObject<ChatDetectorBlock> =
		registerBlock("chat_detector", ::ChatDetectorBlock)
	val GLOBAL_CHAT_DETECTOR: RegistryObject<GlobalChatDetectorBlock> =
		registerBlock("global_chat_detector", ::GlobalChatDetectorBlock)
	val ENTITY_DETECTOR: RegistryObject<EntityDetectorBlock> =
		registerBlock("entity_detector", ::EntityDetectorBlock)

	// Interfaces
	val PLAYER_INTERFACE: RegistryObject<PlayerInterfaceBlock> =
		registerBlock("player_interface", ::PlayerInterfaceBlock)
	val NOTIFICATION_INTERFACE: RegistryObject<NotificationInterfaceBlock> =
		registerBlock("notification_interface", ::NotificationInterfaceBlock)
	val BASIC_REDSTONE_INTERFACE: RegistryObject<RedstoneInterfaceBlock> =
		registerBlock("basic_redstone_interface") { RedstoneInterfaceBlock(isAdvanced = false) }
	val ADVANCED_REDSTONE_INTERFACE: RegistryObject<RedstoneInterfaceBlock> =
		registerBlock("advanced_redstone_interface") { RedstoneInterfaceBlock(isAdvanced = true) }

	// Spectre blocks
	val SPECTRE_BLOCK: RegistryObject<Block> =
		blockWithProperties(
			"spectre_block",
			Properties.of()
				.strength(-1.0f, 3600000.0f)
				.isValidSpawn(Blocks::never)
		)
	val SPECTRE_CORE: RegistryObject<Block> =
		registerBlock("spectre_core", ::SpectreCoreBlock)
	val SPECTRE_LENS: RegistryObject<SpectreLensBlock> =
		registerBlock("spectre_lens", ::SpectreLensBlock)
	val SPECTRE_ENERGY_INJECTOR: RegistryObject<SpectreEnergyInjectorBlock> =
		registerBlock("spectre_energy_injector", ::SpectreEnergyInjectorBlock)
	val SPECTRE_COIL_BASIC: RegistryObject<SpectreCoilBlock> =
		registerBlock("spectre_coil_basic") { SpectreCoilBlock.BASIC }
	val SPECTRE_COIL_REDSTONE: RegistryObject<SpectreCoilBlock> =
		registerBlock("spectre_coil_redstone") { SpectreCoilBlock.REDSTONE }
	val SPECTRE_COIL_ENDER: RegistryObject<SpectreCoilBlock> =
		registerBlock("spectre_coil_ender") { SpectreCoilBlock.ENDER }
	val SPECTRE_COIL_NUMBER: RegistryObject<SpectreCoilBlock> =
		registerBlock("spectre_coil_number") { SpectreCoilBlock.NUMBER }
	val SPECTRE_COIL_GENESIS: RegistryObject<SpectreCoilBlock> =
		registerBlock("spectre_coil_genesis") { SpectreCoilBlock.GENESIS }
	val SPECTRE_SAPLING: RegistryObject<SaplingBlock> =
		registerBlock("spectre_sapling") { SpectreTreeBlocks.SPECTRE_SAPLING }
	val SPECTRE_LOG: RegistryObject<FlammableRotatedPillarBlock> =
		registerBlock("spectre_log") { SpectreTreeBlocks.SPECTRE_LOG }
	val SPECTRE_WOOD: RegistryObject<Block> =
		registerBlock("spectre_wood") { SpectreTreeBlocks.SPECTRE_WOOD }
	val STRIPPED_SPECTRE_LOG: RegistryObject<FlammableRotatedPillarBlock> =
		registerBlock("stripped_spectre_log") { SpectreTreeBlocks.STRIPPED_SPECTRE_LOG }
	val SPECTRE_LEAVES: RegistryObject<LeavesBlock> =
		registerBlock("spectre_leaves") { SpectreTreeBlocks.SPECTRE_LEAVES }
	val SPECTRE_PLANKS: RegistryObject<Block> =
		registerBlock("spectre_planks") { SpectreTreeBlocks.SPECTRE_PLANKS }

	// Biome blocks
	val BIOME_COBBLESTONE = basicStoneBlock("biome_cobblestone")
	val BIOME_STONE = basicStoneBlock("biome_stone")
	val BIOME_STONE_BRICKS = basicStoneBlock("biome_stone_bricks")
	val BIOME_STONE_BRICKS_CRACKED = basicStoneBlock("biome_stone_bricks_cracked")
	val BIOME_STONE_BRICKS_CHISELED = basicStoneBlock("biome_stone_bricks_chiseled")
	val BIOME_GLASS = basicGlassBlock("biome_glass")    //FIXME: not skipping rendering on adjacent biome glass

	// Plates
	val PROCESSING_PLATE = basicBlock("processing_plate")
	val REDIRECTOR_PLATE: RegistryObject<RedirectorPlateBlock> =
		registerBlock("redirector_plate", ::RedirectorPlateBlock)
	val FILTERED_REDIRECTOR_PLATE = basicBlock("filtered_redirector_plate")
	val REDSTONE_PLATE = basicBlock("redstone_plate")
	val CORRECTOR_PLATE: RegistryObject<CorrectorPlateBlock> =
		registerBlock("corrector_plate", ::CorrectorPlateBlock)
	val ITEM_SEALER_PLATE: RegistryObject<ItemSealerPlate> =
		registerBlock("item_sealer_plate", ::ItemSealerPlate)
	val ITEM_REJUVENATOR_PLATE: RegistryObject<ItemRejuvenatorPlate> =
		registerBlock("item_rejuvenator_plate", ::ItemRejuvenatorPlate)
	val ACCELERATOR_PLATE: RegistryObject<AcceleratorPlateBlock> =
		registerBlock("accelerator_plate", ::AcceleratorPlateBlock)
	val DIRECTIONAL_ACCELERATOR_PLATE: RegistryObject<DirectionalAcceleratorPlateBlock> =
		registerBlock("directional_accelerator_plate", ::DirectionalAcceleratorPlateBlock)
	val BOUNCY_PLATE: RegistryObject<BouncyPlateBlock> =
		registerBlock("bouncy_plate", ::BouncyPlateBlock)
	val COLLECTION_PLATE: RegistryObject<CollectionPlateBlock> =
		registerBlock("collection_plate", ::CollectionPlateBlock)
	val EXTRACTION_PLATE = basicBlock("extraction_plate")

	// Platform
	val OAK_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("oak_platform") { PlatformBlock.OAK }
	val SPRUCE_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("spruce_platform") { PlatformBlock.SPRUCE }
	val BIRCH_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("birch_platform") { PlatformBlock.BIRCH }
	val JUNGLE_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("jungle_platform") { PlatformBlock.JUNGLE }
	val ACACIA_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("acacia_platform") { PlatformBlock.ACACIA }
	val DARK_OAK_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("dark_oak_platform") { PlatformBlock.DARK_OAK }
	val CRIMSON_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("crimson_platform") { PlatformBlock.CRIMSON }
	val WARPED_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("warped_platform") { PlatformBlock.WARPED }
	val MANGROVE_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("mangrove_platform") { PlatformBlock.MANGROVE }
	val BAMBOO_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("bamboo_platform") { PlatformBlock.BAMBOO }
	val CHERRY_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("cherry_platform") { PlatformBlock.CHERRY }
	val SUPER_LUBRICANT_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("super_lubricant_platform") { PlatformBlock.SUPER_LUBE }
	val FILTERED_SUPER_LUBRICANT_PLATFORM: RegistryObject<PlatformBlock> =
		registerBlock("filtered_super_lubricant_platform") { PlatformBlock.FILTERED_SUPER_LUBE }

	// Colored blocks
	val COLORED_GRASS_WHITE: RegistryObject<GrassBlock> = coloredGrass(DyeColor.WHITE)
	val COLORED_GRASS_ORANGE: RegistryObject<GrassBlock> = coloredGrass(DyeColor.ORANGE)
	val COLORED_GRASS_MAGENTA: RegistryObject<GrassBlock> = coloredGrass(DyeColor.MAGENTA)
	val COLORED_GRASS_LIGHT_BLUE: RegistryObject<GrassBlock> = coloredGrass(DyeColor.LIGHT_BLUE)
	val COLORED_GRASS_YELLOW: RegistryObject<GrassBlock> = coloredGrass(DyeColor.YELLOW)
	val COLORED_GRASS_LIME: RegistryObject<GrassBlock> = coloredGrass(DyeColor.LIME)
	val COLORED_GRASS_PINK: RegistryObject<GrassBlock> = coloredGrass(DyeColor.PINK)
	val COLORED_GRASS_GRAY: RegistryObject<GrassBlock> = coloredGrass(DyeColor.GRAY)
	val COLORED_GRASS_LIGHT_GRAY: RegistryObject<GrassBlock> = coloredGrass(DyeColor.LIGHT_GRAY)
	val COLORED_GRASS_CYAN: RegistryObject<GrassBlock> = coloredGrass(DyeColor.CYAN)
	val COLORED_GRASS_PURPLE: RegistryObject<GrassBlock> = coloredGrass(DyeColor.PURPLE)
	val COLORED_GRASS_BLUE: RegistryObject<GrassBlock> = coloredGrass(DyeColor.BLUE)
	val COLORED_GRASS_BROWN: RegistryObject<GrassBlock> = coloredGrass(DyeColor.BROWN)
	val COLORED_GRASS_GREEN: RegistryObject<GrassBlock> = coloredGrass(DyeColor.GREEN)
	val COLORED_GRASS_RED: RegistryObject<GrassBlock> = coloredGrass(DyeColor.RED)
	val COLORED_GRASS_BLACK: RegistryObject<GrassBlock> = coloredGrass(DyeColor.BLACK)

	private fun coloredGrass(dyeColor: DyeColor): RegistryObject<GrassBlock> =
		registerBlock("colored_grass_${dyeColor.getDyeName()}") {
			GrassBlock(Properties.copy(Blocks.GRASS_BLOCK).mapColor(dyeColor))
		}

	val LUMINOUS_BLOCK_WHITE: RegistryObject<Block> = basicStoneBlock("luminous_block_white")
	val LUMINOUS_BLOCK_ORANGE: RegistryObject<Block> = basicStoneBlock("luminous_block_orange")
	val LUMINOUS_BLOCK_MAGENTA: RegistryObject<Block> = basicStoneBlock("luminous_block_magenta")
	val LUMINOUS_BLOCK_LIGHT_BLUE: RegistryObject<Block> = basicStoneBlock("luminous_block_light_blue")
	val LUMINOUS_BLOCK_YELLOW: RegistryObject<Block> = basicStoneBlock("luminous_block_yellow")
	val LUMINOUS_BLOCK_LIME: RegistryObject<Block> = basicStoneBlock("luminous_block_lime")
	val LUMINOUS_BLOCK_PINK: RegistryObject<Block> = basicStoneBlock("luminous_block_pink")
	val LUMINOUS_BLOCK_GRAY: RegistryObject<Block> = basicStoneBlock("luminous_block_gray")
	val LUMINOUS_BLOCK_LIGHT_GRAY: RegistryObject<Block> = basicStoneBlock("luminous_block_light_gray")
	val LUMINOUS_BLOCK_CYAN: RegistryObject<Block> = basicStoneBlock("luminous_block_cyan")
	val LUMINOUS_BLOCK_PURPLE: RegistryObject<Block> = basicStoneBlock("luminous_block_purple")
	val LUMINOUS_BLOCK_BLUE: RegistryObject<Block> = basicStoneBlock("luminous_block_blue")
	val LUMINOUS_BLOCK_BROWN: RegistryObject<Block> = basicStoneBlock("luminous_block_brown")
	val LUMINOUS_BLOCK_GREEN: RegistryObject<Block> = basicStoneBlock("luminous_block_green")
	val LUMINOUS_BLOCK_RED: RegistryObject<Block> = basicStoneBlock("luminous_block_red")
	val LUMINOUS_BLOCK_BLACK: RegistryObject<Block> = basicStoneBlock("luminous_block_black")

	val TRANSLUCENT_LUMINOUS_BLOCK_WHITE: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_white")
	val TRANSLUCENT_LUMINOUS_BLOCK_ORANGE: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_orange")
	val TRANSLUCENT_LUMINOUS_BLOCK_MAGENTA: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_magenta")
	val TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_BLUE: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_light_blue")
	val TRANSLUCENT_LUMINOUS_BLOCK_YELLOW: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_yellow")
	val TRANSLUCENT_LUMINOUS_BLOCK_LIME: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_lime")
	val TRANSLUCENT_LUMINOUS_BLOCK_PINK: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_pink")
	val TRANSLUCENT_LUMINOUS_BLOCK_GRAY: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_gray")
	val TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_GRAY: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_light_gray")
	val TRANSLUCENT_LUMINOUS_BLOCK_CYAN: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_cyan")
	val TRANSLUCENT_LUMINOUS_BLOCK_PURPLE: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_purple")
	val TRANSLUCENT_LUMINOUS_BLOCK_BLUE: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_blue")
	val TRANSLUCENT_LUMINOUS_BLOCK_BROWN: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_brown")
	val TRANSLUCENT_LUMINOUS_BLOCK_GREEN: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_green")
	val TRANSLUCENT_LUMINOUS_BLOCK_RED: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_red")
	val TRANSLUCENT_LUMINOUS_BLOCK_BLACK: RegistryObject<GlassBlock> = basicGlassBlock("translucent_luminous_block_black")

	val STAINED_BRICKS_WHITE: RegistryObject<Block> = coloredStone("stained_bricks_white", DyeColor.WHITE)
	val STAINED_BRICKS_ORANGE: RegistryObject<Block> = coloredStone("stained_bricks_orange", DyeColor.ORANGE)
	val STAINED_BRICKS_MAGENTA: RegistryObject<Block> = coloredStone("stained_bricks_magenta", DyeColor.MAGENTA)
	val STAINED_BRICKS_LIGHT_BLUE: RegistryObject<Block> = coloredStone("stained_bricks_light_blue", DyeColor.LIGHT_BLUE)
	val STAINED_BRICKS_YELLOW: RegistryObject<Block> = coloredStone("stained_bricks_yellow", DyeColor.YELLOW)
	val STAINED_BRICKS_LIME: RegistryObject<Block> = coloredStone("stained_bricks_lime", DyeColor.LIME)
	val STAINED_BRICKS_PINK: RegistryObject<Block> = coloredStone("stained_bricks_pink", DyeColor.PINK)
	val STAINED_BRICKS_GRAY: RegistryObject<Block> = coloredStone("stained_bricks_gray", DyeColor.GRAY)
	val STAINED_BRICKS_LIGHT_GRAY: RegistryObject<Block> = coloredStone("stained_bricks_light_gray", DyeColor.LIGHT_GRAY)
	val STAINED_BRICKS_CYAN: RegistryObject<Block> = coloredStone("stained_bricks_cyan", DyeColor.CYAN)
	val STAINED_BRICKS_PURPLE: RegistryObject<Block> = coloredStone("stained_bricks_purple", DyeColor.PURPLE)
	val STAINED_BRICKS_BLUE: RegistryObject<Block> = coloredStone("stained_bricks_blue", DyeColor.BLUE)
	val STAINED_BRICKS_BROWN: RegistryObject<Block> = coloredStone("stained_bricks_brown", DyeColor.BROWN)
	val STAINED_BRICKS_GREEN: RegistryObject<Block> = coloredStone("stained_bricks_green", DyeColor.GREEN)
	val STAINED_BRICKS_RED: RegistryObject<Block> = coloredStone("stained_bricks_red", DyeColor.RED)
	val STAINED_BRICKS_BLACK: RegistryObject<Block> = coloredStone("stained_bricks_black", DyeColor.BLACK)

	val LUMINOUS_STAINED_BRICKS_WHITE: RegistryObject<Block> = coloredStone("luminous_stained_bricks_white", DyeColor.WHITE)
	val LUMINOUS_STAINED_BRICKS_ORANGE: RegistryObject<Block> = coloredStone("luminous_stained_bricks_orange", DyeColor.ORANGE)
	val LUMINOUS_STAINED_BRICKS_MAGENTA: RegistryObject<Block> = coloredStone("luminous_stained_bricks_magenta", DyeColor.MAGENTA)
	val LUMINOUS_STAINED_BRICKS_LIGHT_BLUE: RegistryObject<Block> = coloredStone("luminous_stained_bricks_light_blue", DyeColor.LIGHT_BLUE)
	val LUMINOUS_STAINED_BRICKS_YELLOW: RegistryObject<Block> = coloredStone("luminous_stained_bricks_yellow", DyeColor.YELLOW)
	val LUMINOUS_STAINED_BRICKS_LIME: RegistryObject<Block> = coloredStone("luminous_stained_bricks_lime", DyeColor.LIME)
	val LUMINOUS_STAINED_BRICKS_PINK: RegistryObject<Block> = coloredStone("luminous_stained_bricks_pink", DyeColor.PINK)
	val LUMINOUS_STAINED_BRICKS_GRAY: RegistryObject<Block> = coloredStone("luminous_stained_bricks_gray", DyeColor.GRAY)
	val LUMINOUS_STAINED_BRICKS_LIGHT_GRAY: RegistryObject<Block> = coloredStone("luminous_stained_bricks_light_gray", DyeColor.LIGHT_GRAY)
	val LUMINOUS_STAINED_BRICKS_CYAN: RegistryObject<Block> = coloredStone("luminous_stained_bricks_cyan", DyeColor.CYAN)
	val LUMINOUS_STAINED_BRICKS_PURPLE: RegistryObject<Block> = coloredStone("luminous_stained_bricks_purple", DyeColor.PURPLE)
	val LUMINOUS_STAINED_BRICKS_BLUE: RegistryObject<Block> = coloredStone("luminous_stained_bricks_blue", DyeColor.BLUE)
	val LUMINOUS_STAINED_BRICKS_BROWN: RegistryObject<Block> = coloredStone("luminous_stained_bricks_brown", DyeColor.BROWN)
	val LUMINOUS_STAINED_BRICKS_GREEN: RegistryObject<Block> = coloredStone("luminous_stained_bricks_green", DyeColor.GREEN)
	val LUMINOUS_STAINED_BRICKS_RED: RegistryObject<Block> = coloredStone("luminous_stained_bricks_red", DyeColor.RED)
	val LUMINOUS_STAINED_BRICKS_BLACK: RegistryObject<Block> = coloredStone("luminous_stained_bricks_black", DyeColor.BLACK)

	// Internal
	val FLOO_BRICK: RegistryObject<FlooBrickBlock> =
		registerBlockWithoutItem("floo_brick", ::FlooBrickBlock)
	val ANCIENT_BRICK = basicBlock("ancient_brick")

	// Removed blocks:
	// - Light Redirector: Just too hard to implement lmao	//TODO Actually would this be so hard, now?
	// - Dyeing Machine: Too hard and nobody would use
	// - Quartz and Lapis Lamps: WAY more difficult to do now than in 1.12
	// - Sound Dampener: Just use Super Sound Muffler instead
	// - Sound Box: I just don't wanna
	// - Fluid Display: I don't wanna (rendering is scary :( )

	private fun blockWithProperties(name: String, properties: Properties) =
		registerBlock(name) { Block(properties) }

	private fun basicBlock(name: String) =
		blockWithProperties(name, Properties.of())

	private fun basicGlassBlock(name: String) =
		registerBlock(name) { GlassBlock(Properties.copy(Blocks.GLASS)) }

	private fun basicCopiedBlock(name: String, blockToCopy: Block) =
		blockWithProperties(name, Properties.copy(blockToCopy))

	private fun basicStoneBlock(name: String) =
		basicCopiedBlock(name, Blocks.STONE)

	private fun coloredStone(name: String, color: DyeColor) =
		blockWithProperties(name, Properties.copy(Blocks.STONE).mapColor(color))

	private fun <T : Block> registerBlock(
		name: String,
		supplier: () -> T
	): RegistryObject<T> {
		val block = BLOCK_REGISTRY.register(name, supplier)

		ModItems.ITEM_REGISTRY.register(name) { BlockItem(block.get(), Item.Properties()) }

		return block
	}

	private fun <T : Block> registerBlockWithoutItem(
		name: String,
		supplier: () -> T
	): RegistryObject<T> {
		return BLOCK_REGISTRY.register(name, supplier)
	}

	@Suppress("REDUNDANT_ELSE_IN_WHEN")
	fun getColoredGrass(dyeColor: DyeColor): RegistryObject<GrassBlock>? {
		return when (dyeColor) {
			DyeColor.WHITE -> COLORED_GRASS_WHITE
			DyeColor.ORANGE -> COLORED_GRASS_ORANGE
			DyeColor.MAGENTA -> COLORED_GRASS_MAGENTA
			DyeColor.LIGHT_BLUE -> COLORED_GRASS_LIGHT_BLUE
			DyeColor.YELLOW -> COLORED_GRASS_YELLOW
			DyeColor.LIME -> COLORED_GRASS_LIME
			DyeColor.PINK -> COLORED_GRASS_PINK
			DyeColor.GRAY -> COLORED_GRASS_GRAY
			DyeColor.LIGHT_GRAY -> COLORED_GRASS_LIGHT_GRAY
			DyeColor.CYAN -> COLORED_GRASS_CYAN
			DyeColor.PURPLE -> COLORED_GRASS_PURPLE
			DyeColor.BLUE -> COLORED_GRASS_BLUE
			DyeColor.BROWN -> COLORED_GRASS_BROWN
			DyeColor.GREEN -> COLORED_GRASS_GREEN
			DyeColor.RED -> COLORED_GRASS_RED
			DyeColor.BLACK -> COLORED_GRASS_BLACK
			else -> null
		}
	}

	@Suppress("REDUNDANT_ELSE_IN_WHEN")
	fun getLuminousBlock(dyeColor: DyeColor): RegistryObject<Block>? {
		return when (dyeColor) {
			DyeColor.WHITE -> LUMINOUS_BLOCK_WHITE
			DyeColor.ORANGE -> LUMINOUS_BLOCK_ORANGE
			DyeColor.MAGENTA -> LUMINOUS_BLOCK_MAGENTA
			DyeColor.LIGHT_BLUE -> LUMINOUS_BLOCK_LIGHT_BLUE
			DyeColor.YELLOW -> LUMINOUS_BLOCK_YELLOW
			DyeColor.LIME -> LUMINOUS_BLOCK_LIME
			DyeColor.PINK -> LUMINOUS_BLOCK_PINK
			DyeColor.GRAY -> LUMINOUS_BLOCK_GRAY
			DyeColor.LIGHT_GRAY -> LUMINOUS_BLOCK_LIGHT_GRAY
			DyeColor.CYAN -> LUMINOUS_BLOCK_CYAN
			DyeColor.PURPLE -> LUMINOUS_BLOCK_PURPLE
			DyeColor.BLUE -> LUMINOUS_BLOCK_BLUE
			DyeColor.BROWN -> LUMINOUS_BLOCK_BROWN

			DyeColor.GREEN -> LUMINOUS_BLOCK_GREEN
			DyeColor.RED -> LUMINOUS_BLOCK_RED
			DyeColor.BLACK -> LUMINOUS_BLOCK_BLACK
			else -> null
		}
	}

	@Suppress("REDUNDANT_ELSE_IN_WHEN")
	fun getLuminousBlockTranslucent(dyeColor: DyeColor): RegistryObject<GlassBlock>? {
		return when (dyeColor) {
			DyeColor.WHITE -> TRANSLUCENT_LUMINOUS_BLOCK_WHITE
			DyeColor.ORANGE -> TRANSLUCENT_LUMINOUS_BLOCK_ORANGE
			DyeColor.MAGENTA -> TRANSLUCENT_LUMINOUS_BLOCK_MAGENTA
			DyeColor.LIGHT_BLUE -> TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_BLUE
			DyeColor.YELLOW -> TRANSLUCENT_LUMINOUS_BLOCK_YELLOW
			DyeColor.LIME -> TRANSLUCENT_LUMINOUS_BLOCK_LIME
			DyeColor.PINK -> TRANSLUCENT_LUMINOUS_BLOCK_PINK
			DyeColor.GRAY -> TRANSLUCENT_LUMINOUS_BLOCK_GRAY
			DyeColor.LIGHT_GRAY -> TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_GRAY
			DyeColor.CYAN -> TRANSLUCENT_LUMINOUS_BLOCK_CYAN
			DyeColor.PURPLE -> TRANSLUCENT_LUMINOUS_BLOCK_PURPLE
			DyeColor.BLUE -> TRANSLUCENT_LUMINOUS_BLOCK_BLUE
			DyeColor.BROWN -> TRANSLUCENT_LUMINOUS_BLOCK_BROWN
			DyeColor.GREEN -> TRANSLUCENT_LUMINOUS_BLOCK_GREEN
			DyeColor.RED -> TRANSLUCENT_LUMINOUS_BLOCK_RED
			DyeColor.BLACK -> TRANSLUCENT_LUMINOUS_BLOCK_BLACK
			else -> null
		}
	}

	@Suppress("REDUNDANT_ELSE_IN_WHEN")
	fun getStainedBrick(dyeColor: DyeColor): RegistryObject<Block>? {
		return when (dyeColor) {
			DyeColor.WHITE -> STAINED_BRICKS_WHITE
			DyeColor.ORANGE -> STAINED_BRICKS_ORANGE
			DyeColor.MAGENTA -> STAINED_BRICKS_MAGENTA
			DyeColor.LIGHT_BLUE -> STAINED_BRICKS_LIGHT_BLUE
			DyeColor.YELLOW -> STAINED_BRICKS_YELLOW
			DyeColor.LIME -> STAINED_BRICKS_LIME
			DyeColor.PINK -> STAINED_BRICKS_PINK
			DyeColor.GRAY -> STAINED_BRICKS_GRAY
			DyeColor.LIGHT_GRAY -> STAINED_BRICKS_LIGHT_GRAY
			DyeColor.CYAN -> STAINED_BRICKS_CYAN
			DyeColor.PURPLE -> STAINED_BRICKS_PURPLE
			DyeColor.BLUE -> STAINED_BRICKS_BLUE
			DyeColor.BROWN -> STAINED_BRICKS_BROWN
			DyeColor.GREEN -> STAINED_BRICKS_GREEN
			DyeColor.RED -> STAINED_BRICKS_RED
			DyeColor.BLACK -> STAINED_BRICKS_BLACK
			else -> null
		}
	}

	@Suppress("REDUNDANT_ELSE_IN_WHEN")
	fun getStainedBrickLuminous(dyeColor: DyeColor): RegistryObject<Block>? {
		return when (dyeColor) {
			DyeColor.WHITE -> LUMINOUS_STAINED_BRICKS_WHITE
			DyeColor.ORANGE -> LUMINOUS_STAINED_BRICKS_ORANGE
			DyeColor.MAGENTA -> LUMINOUS_STAINED_BRICKS_MAGENTA
			DyeColor.LIGHT_BLUE -> LUMINOUS_STAINED_BRICKS_LIGHT_BLUE
			DyeColor.YELLOW -> LUMINOUS_STAINED_BRICKS_YELLOW
			DyeColor.LIME -> LUMINOUS_STAINED_BRICKS_LIME
			DyeColor.PINK -> LUMINOUS_STAINED_BRICKS_PINK
			DyeColor.GRAY -> LUMINOUS_STAINED_BRICKS_GRAY
			DyeColor.LIGHT_GRAY -> LUMINOUS_STAINED_BRICKS_LIGHT_GRAY
			DyeColor.CYAN -> LUMINOUS_STAINED_BRICKS_CYAN
			DyeColor.PURPLE -> LUMINOUS_STAINED_BRICKS_PURPLE
			DyeColor.BLUE -> LUMINOUS_STAINED_BRICKS_BLUE
			DyeColor.BROWN -> LUMINOUS_STAINED_BRICKS_BROWN
			DyeColor.GREEN -> LUMINOUS_STAINED_BRICKS_GREEN
			DyeColor.RED -> LUMINOUS_STAINED_BRICKS_RED
			DyeColor.BLACK -> LUMINOUS_STAINED_BRICKS_BLACK
			else -> null
		}
	}

}