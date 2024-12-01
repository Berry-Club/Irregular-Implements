package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.*
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.GrassBlock
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister

object ModBlocks {

    val BLOCK_REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(IrregularImplements.ID)

    val FERTILIZED_DIRT: DeferredBlock<FertilizedDirtBlock> =
        registerBlock("fertilized_dirt") { FertilizedDirtBlock() }
    val BEAN_POD = basicBlock("bean_pod")
    val IMBUING_STATION = basicBlock("imbuing_station")
    val FLUID_DISPLAY = basicBlock("fluid_display")
    val ENDER_MAILBOX = basicBlock("ender_mailbox")
    val PITCHER_PLANT: DeferredBlock<PitcherPlantBlock> =
        registerBlock("pitcher_plant") { PitcherPlantBlock() }
    val POTION_VAPORIZER = basicBlock("potion_vaporizer")
    val RAIN_SHIELD =
        registerBlock("rain_shield") { RainShieldBlock() }
    val COMPRESSED_SLIME_BLOCK: DeferredBlock<CompressedSlimeBlock> =
        registerBlock("compressed_slime_block") { CompressedSlimeBlock() }
    val BIOME_RADAR = basicBlock("biome_radar")     // Custom canSurvive and onPlace that automatically does the antenna
    val INVENTORY_REROUTER = basicBlock("inventory_rerouter")
    val SLIME_CUBE = basicBlock("slime_cube")
    val PEACE_CANDLE = basicBlock("peace_candle")

    /** [net.neoforged.neoforge.common.world.StructureModifier] */
    val GLOWING_MUSHROOM = basicBlock("glowing_mushroom")
    val SOUND_BOX = basicBlock("sound_box")
    val SOUND_DAMPENER = basicBlock("sound_dampener")

    // Loot blocks
    val NATURE_CORE = basicBlock("nature_core")
    val NATURE_CHEST = basicBlock("nature_chest")
    val WATER_CHEST = basicBlock("water_chest")

    // Collectors
    val ITEM_COLLECTOR = basicBlock("item_collector")
    val ADVANCED_ITEM_COLLECTOR = basicBlock("advanced_item_collector")

    // Redstone blocks
    val ANALOG_EMITTER: DeferredBlock<AnalogEmitterBlock> =
        registerBlock("analog_emitter") { AnalogEmitterBlock() }
    val CONTACT_BUTTON: DeferredBlock<ContactButtonBlock> =
        registerBlock("contact_button") { ContactButtonBlock() }
    val CONTACT_LEVER: DeferredBlock<ContactLeverBlock> =
        registerBlock("contact_lever") { ContactLeverBlock() }
    val IRON_DROPPER = basicBlock("iron_dropper")
    val IGNITER = basicBlock("igniter")
    val INVENTORY_TESTER = basicBlock("inventory_tester")
    val BLOCK_DESTABILIZER: DeferredBlock<BlockDestabilizerBlock> =
        registerBlock("block_destabilizer") { BlockDestabilizerBlock() }
    val BLOCK_BREAKER = basicBlock("block_breaker")
    val REDSTONE_OBSERVER: DeferredBlock<RedstoneObserverBlock> =
        registerBlock("redstone_observer") { RedstoneObserverBlock() }
    val SIDED_BLOCK_OF_REDSTONE =
        registerBlock("sided_redstone") { SidedRedstoneBlock() }

    // Sticks
    val BLOCK_OF_STICKS: DeferredBlock<BlockOfSticksBlock> =
        registerBlock("block_of_sticks") { BlockOfSticksBlock(returning = false) }
    val RETURNING_BLOCK_OF_STICKS: DeferredBlock<BlockOfSticksBlock> =
        registerBlock("returning_block_of_sticks") { BlockOfSticksBlock(returning = true) }

    // No Item
    val BLAZE_FIRE: DeferredBlock<BlazeFireBlock> =
        registerBlockWithoutItem("blaze_fire") { BlazeFireBlock() }

    // Glass
    val TRIGGER_GLASS: DeferredBlock<TriggerGlassBlock> =
        registerBlock("trigger_glass") { TriggerGlassBlock() }
    val LAPIS_GLASS: DeferredBlock<PermeableGlassBlock> =
        registerBlock("lapis_glass") { PermeableGlassBlock(PermeableGlassBlock.Type.LAPIS) }
    val QUARTZ_GLASS: DeferredBlock<PermeableGlassBlock> =
        registerBlock("quartz_glass") { PermeableGlassBlock(PermeableGlassBlock.Type.QUARTZ) }

    // Lamp
    val RAINBOW_LAMP =
        registerBlock("rainbow_lamp") { RainbowLampBlock() }
    val QUARTZ_LAMP = basicBlock("quartz_lamp")
    val LAPIS_LAMP = basicBlock("lapis_lamp")

    // Ender Bridge
    val ENDER_BRIDGE: DeferredBlock<EnderBridgeBlock> =
        registerBlock("ender_bridge") { EnderBridgeBlock(distancePerTick = 1) }
    val PRISMARINE_ENDER_BRIDGE: DeferredBlock<EnderBridgeBlock> =
        registerBlock("prismarine_ender_bridge") { EnderBridgeBlock(distancePerTick = 2) }
    val ENDER_ANCHOR: DeferredBlock<Block> =
        basicCopiedBlock("ender_anchor", Blocks.OBSIDIAN)

    // Lubricant
    val SUPER_LUBRICANT_ICE: DeferredBlock<Block> =
        basicCopiedBlock("super_lubricant_ice", Blocks.BLUE_ICE)
    val SUPER_LUBRICANT_STONE: DeferredBlock<Block> =
        basicStoneBlock("super_lubricant_stone")
    val SUPER_LUBRICANT_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("super_lubricant_platform") { PlatformBlock.SUPER_LUBE }
    val FILTERED_SUPER_LUBRICANT_PLATFORM = basicBlock("filtered_super_lubricant_platform")

    // Platform
    val OAK_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("oak_platform") { PlatformBlock.OAK }
    val SPRUCE_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("spruce_platform") { PlatformBlock.SPRUCE }
    val BIRCH_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("birch_platform") { PlatformBlock.BIRCH }
    val JUNGLE_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("jungle_platform") { PlatformBlock.JUNGLE }
    val ACACIA_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("acacia_platform") { PlatformBlock.ACACIA }
    val DARK_OAK_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("dark_oak_platform") { PlatformBlock.DARK_OAK }
    val CRIMSON_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("crimson_platform") { PlatformBlock.CRIMSON }
    val WARPED_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("warped_platform") { PlatformBlock.WARPED }
    val MANGROVE_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("mangrove_platform") { PlatformBlock.MANGROVE }
    val BAMBOO_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("bamboo_platform") { PlatformBlock.BAMBOO }
    val CHERRY_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("cherry_platform") { PlatformBlock.CHERRY }

    // Detectors
    val ONLINE_DETECTOR = basicBlock("online_detector")
    val CHAT_DETECTOR = basicBlock("chat_detector")
    val GLOBAL_CHAT_DETECTOR = basicBlock("global_chat_detector")
    val ENTITY_DETECTOR = basicBlock("entity_detector")

    // Interfaces
    val PLAYER_INTERFACE = basicBlock("player_interface")
    val NOTIFICATION_INTERFACE = basicBlock("notification_interface")
    val BASIC_REDSTONE_INTERFACE: DeferredBlock<RedstoneInterfaceBasicBlock> =
        registerBlock("basic_redstone_interface") { RedstoneInterfaceBasicBlock() }
    val ADVANCED_REDSTONE_INTERFACE = basicBlock("advanced_redstone_interface")

    // Spectre blocks
    val SPECTRE_BLOCK = basicBlock("spectre_block")
    val SPECTRE_LENS = basicBlock("spectre_lens")
    val SPECTRE_ENERGY_INJECTOR = basicBlock("spectre_energy_injector")
    val SPECTRE_COIL = basicBlock("spectre_coil")
    val SPECTRE_COIL_REDSTONE = basicBlock("spectre_coil_redstone")
    val SPECTRE_COIL_ENDER = basicBlock("spectre_coil_ender")
    val SPECTRE_COIL_NUMBER = basicBlock("spectre_coil_number")
    val SPECTRE_COIL_GENESIS = basicBlock("spectre_coil_genesis")
    val SPECTRE_PLANKS = basicBlock("spectre_planks")
    val SPECTRE_SAPLING = basicBlock("spectre_sapling")
    val SPECTRE_WOOD = basicBlock("spectre_wood")
    val SPECTRE_LEAVES = basicBlock("spectre_leaves")

    // Biome blocks
    val BIOME_COBBLESTONE = basicStoneBlock("biome_cobblestone")
    val BIOME_STONE = basicStoneBlock("biome_stone")
    val BIOME_STONE_BRICKS = basicStoneBlock("biome_stone_bricks")
    val BIOME_STONE_BRICKS_CRACKED = basicStoneBlock("biome_stone_bricks_cracked")
    val BIOME_STONE_BRICKS_CHISELED = basicStoneBlock("biome_stone_bricks_chiseled")
    val BIOME_GLASS = basicGlassBlock("biome_glass")    //FIXME: not skipping rendering on adjacent biome glass

    // Plates
    val PROCESSING_PLATE = basicBlock("processing_plate")
    val REDIRECTOR_PLATE = basicBlock("redirector_plate")
    val FILTERED_REDIRECTOR_PLATE = basicBlock("filtered_redirector_plate")
    val REDSTONE_PLATE = basicBlock("redstone_plate")
    val CORRECTOR_PLATE = basicBlock("corrector_plate")
    val ITEM_SEALER_PLATE = basicBlock("item_sealer_plate")
    val ITEM_REJUVINATOR_PLATE = basicBlock("item_rejuvinator_plate")
    val ACCELERATOR_PLATE = basicBlock("accelerator_plate")
    val DIRECTIONAL_ACCELERATOR_PLATE = basicBlock("directional_accelerator_plate")
    val BOUNCY_PLATE = basicBlock("bouncy_plate")
    val COLLECTION_PLATE = basicBlock("collection_plate")
    val EXTRACTION_PLATE = basicBlock("extraction_plate")

    // Colored blocks
    val COLORED_GRASS_WHITE: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.WHITE)
    val COLORED_GRASS_ORANGE: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.ORANGE)
    val COLORED_GRASS_MAGENTA: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.MAGENTA)
    val COLORED_GRASS_LIGHT_BLUE: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.LIGHT_BLUE)
    val COLORED_GRASS_YELLOW: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.YELLOW)
    val COLORED_GRASS_LIME: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.LIME)
    val COLORED_GRASS_PINK: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.PINK)
    val COLORED_GRASS_GRAY: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.GRAY)
    val COLORED_GRASS_LIGHT_GRAY: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.LIGHT_GRAY)
    val COLORED_GRASS_CYAN: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.CYAN)
    val COLORED_GRASS_PURPLE: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.PURPLE)
    val COLORED_GRASS_BLUE: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.BLUE)
    val COLORED_GRASS_BROWN: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.BROWN)
    val COLORED_GRASS_GREEN: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.GREEN)
    val COLORED_GRASS_RED: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.RED)
    val COLORED_GRASS_BLACK: DeferredBlock<GrassBlock> = coloredGrass(DyeColor.BLACK)

    private fun coloredGrass(dyeColor: DyeColor): DeferredBlock<GrassBlock> =
        registerBlock("colored_grass_${dyeColor.getName()}") {
            GrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(dyeColor))
        }

    val LUMINOUS_BLOCK_WHITE: DeferredBlock<Block> = basicStoneBlock("luminous_block_white")
    val LUMINOUS_BLOCK_ORANGE: DeferredBlock<Block> = basicStoneBlock("luminous_block_orange")
    val LUMINOUS_BLOCK_MAGENTA: DeferredBlock<Block> = basicStoneBlock("luminous_block_magenta")
    val LUMINOUS_BLOCK_LIGHT_BLUE: DeferredBlock<Block> = basicStoneBlock("luminous_block_light_blue")
    val LUMINOUS_BLOCK_YELLOW: DeferredBlock<Block> = basicStoneBlock("luminous_block_yellow")
    val LUMINOUS_BLOCK_LIME: DeferredBlock<Block> = basicStoneBlock("luminous_block_lime")
    val LUMINOUS_BLOCK_PINK: DeferredBlock<Block> = basicStoneBlock("luminous_block_pink")
    val LUMINOUS_BLOCK_GRAY: DeferredBlock<Block> = basicStoneBlock("luminous_block_gray")
    val LUMINOUS_BLOCK_LIGHT_GRAY: DeferredBlock<Block> = basicStoneBlock("luminous_block_light_gray")
    val LUMINOUS_BLOCK_CYAN: DeferredBlock<Block> = basicStoneBlock("luminous_block_cyan")
    val LUMINOUS_BLOCK_PURPLE: DeferredBlock<Block> = basicStoneBlock("luminous_block_purple")
    val LUMINOUS_BLOCK_BLUE: DeferredBlock<Block> = basicStoneBlock("luminous_block_blue")
    val LUMINOUS_BLOCK_BROWN: DeferredBlock<Block> = basicStoneBlock("luminous_block_brown")
    val LUMINOUS_BLOCK_GREEN: DeferredBlock<Block> = basicStoneBlock("luminous_block_green")
    val LUMINOUS_BLOCK_RED: DeferredBlock<Block> = basicStoneBlock("luminous_block_red")
    val LUMINOUS_BLOCK_BLACK: DeferredBlock<Block> = basicStoneBlock("luminous_block_black")

    val TRANSLUCENT_LUMINOUS_BLOCK_WHITE: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_white")
    val TRANSLUCENT_LUMINOUS_BLOCK_ORANGE: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_orange")
    val TRANSLUCENT_LUMINOUS_BLOCK_MAGENTA: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_magenta")
    val TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_BLUE: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_light_blue")
    val TRANSLUCENT_LUMINOUS_BLOCK_YELLOW: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_yellow")
    val TRANSLUCENT_LUMINOUS_BLOCK_LIME: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_lime")
    val TRANSLUCENT_LUMINOUS_BLOCK_PINK: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_pink")
    val TRANSLUCENT_LUMINOUS_BLOCK_GRAY: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_gray")
    val TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_GRAY: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_light_gray")
    val TRANSLUCENT_LUMINOUS_BLOCK_CYAN: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_cyan")
    val TRANSLUCENT_LUMINOUS_BLOCK_PURPLE: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_purple")
    val TRANSLUCENT_LUMINOUS_BLOCK_BLUE: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_blue")
    val TRANSLUCENT_LUMINOUS_BLOCK_BROWN: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_brown")
    val TRANSLUCENT_LUMINOUS_BLOCK_GREEN: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_green")
    val TRANSLUCENT_LUMINOUS_BLOCK_RED: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_red")
    val TRANSLUCENT_LUMINOUS_BLOCK_BLACK: DeferredBlock<TransparentBlock> = basicGlassBlock("translucent_luminous_block_black")

    val STAINED_BRICKS_WHITE: DeferredBlock<Block> = coloredStone("stained_bricks_white", DyeColor.WHITE)
    val STAINED_BRICKS_ORANGE: DeferredBlock<Block> = coloredStone("stained_bricks_orange", DyeColor.ORANGE)
    val STAINED_BRICKS_MAGENTA: DeferredBlock<Block> = coloredStone("stained_bricks_magenta", DyeColor.MAGENTA)
    val STAINED_BRICKS_LIGHT_BLUE: DeferredBlock<Block> = coloredStone("stained_bricks_light_blue", DyeColor.LIGHT_BLUE)
    val STAINED_BRICKS_YELLOW: DeferredBlock<Block> = coloredStone("stained_bricks_yellow", DyeColor.YELLOW)
    val STAINED_BRICKS_LIME: DeferredBlock<Block> = coloredStone("stained_bricks_lime", DyeColor.LIME)
    val STAINED_BRICKS_PINK: DeferredBlock<Block> = coloredStone("stained_bricks_pink", DyeColor.PINK)
    val STAINED_BRICKS_GRAY: DeferredBlock<Block> = coloredStone("stained_bricks_gray", DyeColor.GRAY)
    val STAINED_BRICKS_LIGHT_GRAY: DeferredBlock<Block> = coloredStone("stained_bricks_light_gray", DyeColor.LIGHT_GRAY)
    val STAINED_BRICKS_CYAN: DeferredBlock<Block> = coloredStone("stained_bricks_cyan", DyeColor.CYAN)
    val STAINED_BRICKS_PURPLE: DeferredBlock<Block> = coloredStone("stained_bricks_purple", DyeColor.PURPLE)
    val STAINED_BRICKS_BLUE: DeferredBlock<Block> = coloredStone("stained_bricks_blue", DyeColor.BLUE)
    val STAINED_BRICKS_BROWN: DeferredBlock<Block> = coloredStone("stained_bricks_brown", DyeColor.BROWN)
    val STAINED_BRICKS_GREEN: DeferredBlock<Block> = coloredStone("stained_bricks_green", DyeColor.GREEN)
    val STAINED_BRICKS_RED: DeferredBlock<Block> = coloredStone("stained_bricks_red", DyeColor.RED)
    val STAINED_BRICKS_BLACK: DeferredBlock<Block> = coloredStone("stained_bricks_black", DyeColor.BLACK)

    val LUMINOUS_STAINED_BRICKS_WHITE: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_white", DyeColor.WHITE)
    val LUMINOUS_STAINED_BRICKS_ORANGE: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_orange", DyeColor.ORANGE)
    val LUMINOUS_STAINED_BRICKS_MAGENTA: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_magenta", DyeColor.MAGENTA)
    val LUMINOUS_STAINED_BRICKS_LIGHT_BLUE: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_light_blue", DyeColor.LIGHT_BLUE)
    val LUMINOUS_STAINED_BRICKS_YELLOW: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_yellow", DyeColor.YELLOW)
    val LUMINOUS_STAINED_BRICKS_LIME: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_lime", DyeColor.LIME)
    val LUMINOUS_STAINED_BRICKS_PINK: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_pink", DyeColor.PINK)
    val LUMINOUS_STAINED_BRICKS_GRAY: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_gray", DyeColor.GRAY)
    val LUMINOUS_STAINED_BRICKS_LIGHT_GRAY: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_light_gray", DyeColor.LIGHT_GRAY)
    val LUMINOUS_STAINED_BRICKS_CYAN: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_cyan", DyeColor.CYAN)
    val LUMINOUS_STAINED_BRICKS_PURPLE: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_purple", DyeColor.PURPLE)
    val LUMINOUS_STAINED_BRICKS_BLUE: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_blue", DyeColor.BLUE)
    val LUMINOUS_STAINED_BRICKS_BROWN: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_brown", DyeColor.BROWN)
    val LUMINOUS_STAINED_BRICKS_GREEN: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_green", DyeColor.GREEN)
    val LUMINOUS_STAINED_BRICKS_RED: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_red", DyeColor.RED)
    val LUMINOUS_STAINED_BRICKS_BLACK: DeferredBlock<Block> = coloredStone("luminous_stained_bricks_black", DyeColor.BLACK)

    // Internal
    val FLOO_BRICK = basicBlock("floo_brick")
    val ANCIENT_BRICK = basicBlock("ancient_brick")

    // Removed blocks:
    // - Light Redirector: Just too hard to implement lmao
    // - Diaphanous Block: Too hard and nobody would use
    // - Custom Crafting Table: Too hard and nobody would use
    // - Dyeing Machine

    private fun blockWithProperties(name: String, properties: BlockBehaviour.Properties) =
        registerBlock(name) { Block(properties) }

    private fun basicBlock(name: String) =
        blockWithProperties(name, BlockBehaviour.Properties.of())

    private fun basicGlassBlock(name: String) =
        registerBlock(name) { TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)) }

    private fun basicCopiedBlock(name: String, blockToCopy: Block) =
        blockWithProperties(name, BlockBehaviour.Properties.ofFullCopy(blockToCopy))

    private fun basicStoneBlock(name: String) =
        basicCopiedBlock(name, Blocks.STONE)

    private fun coloredStone(name: String, color: DyeColor) =
        blockWithProperties(name, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(color))

    private fun <T : Block> registerBlock(
        name: String,
        supplier: () -> T
    ): DeferredBlock<T> {
        val block = BLOCK_REGISTRY.register(name, supplier)
        ModItems.ITEM_REGISTRY.registerSimpleBlockItem(name, block)
        return block
    }

    private fun <T : Block> registerBlockWithoutItem(
        name: String,
        supplier: () -> T
    ): DeferredBlock<T> {
        return BLOCK_REGISTRY.register(name, supplier)
    }

    fun getColoredGrass(dyeColor: DyeColor): DeferredBlock<GrassBlock> {
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
        }
    }

    fun getLuminousBlock(dyeColor: DyeColor): DeferredBlock<Block> {
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
        }
    }

    fun getLuminousBlockTranslucent(dyeColor: DyeColor): DeferredBlock<TransparentBlock> {
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
        }
    }

    fun getStainedBrick(dyeColor: DyeColor): DeferredBlock<Block> {
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
        }
    }

    fun getStainedBrickLuminous(dyeColor: DyeColor): DeferredBlock<Block> {
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
        }
    }

}