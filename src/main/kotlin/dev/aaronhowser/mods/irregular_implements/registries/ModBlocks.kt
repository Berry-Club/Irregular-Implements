package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister

object ModBlocks {

    val BLOCK_REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(IrregularImplements.ID)

    val FERTILIZED_DIRT = basicBlock("fertilized_dirt")
    val LAPIS_GLASS: DeferredBlock<PermeableGlassBlock> =
        registerBlock("lapis_glass") { PermeableGlassBlock(PermeableGlassBlock.Type.LAPIS) }
    val LAPIS_LAMP = basicBlock("lapis_lamp")
    val DYEING_MACHINE = basicBlock("dyeing_machine")
    val ENDER_BRIDGE = basicBlock("ender_bridge")
    val PRISMARINE_ENDER_BRIDGE = basicBlock("prismarine_ender_bridge")
    val ENDER_ANCHOR = basicBlock("ender_anchor")
    val BEAN_POD = basicBlock("bean_pod")
    val LIGHT_REDIRECTOR = basicBlock("light_redirector")
    val IMBUING_STATION = basicBlock("imbuing_station")
    val NATURE_CHEST = basicBlock("nature_chest")
    val WATER_CHEST = basicBlock("water_chest")
    val ANALOG_EMITTER: DeferredBlock<AnalogEmitter> =
        registerBlock("analog_emitter") { AnalogEmitter() }
    val FLUID_DISPLAY = basicBlock("fluid_display")
    val CUSTOM_WORKBENCH = basicBlock("custom_workbench")   // Same block for every variant
    val ENDER_MAILBOX = basicBlock("ender_mailbox")
    val PITCHER_PLANT = basicBlock("pitcher_plant")
    val QUARTZ_LAMP = basicBlock("quartz_lamp")
    val QUARTZ_GLASS: DeferredBlock<PermeableGlassBlock> =
        registerBlock("quartz_glass") { PermeableGlassBlock(PermeableGlassBlock.Type.QUARTZ) }
    val POTION_VAPORIZER = basicBlock("potion_vaporizer")
    val CONTACT_BUTTON = basicBlock("contact_button")
    val CONTACT_LEVER = basicBlock("contact_lever")     // Contact Mike reference?
    val RAIN_SHIELD =
        registerBlock("rain_shield") { RainShieldBlock() }
    val BLOCK_BREAKER = basicBlock("block_breaker")
    val COMPRESSED_SLIME_BLOCK = basicBlock("compressed_slime_block")
    val REDSTONE_OBSERVER = basicBlock("redstone_observer")
    val BIOME_RADAR = basicBlock("biome_radar")     // Custom canSurvive and onPlace that automatically does the antenna
    val IRON_DROPPER = basicBlock("iron_dropper")
    val IGNITER = basicBlock("igniter")
    val BLOCK_OF_STICKS: DeferredBlock<BlockOfSticksBlock> =
        registerBlock("block_of_sticks") { BlockOfSticksBlock(returning = false) }
    val RETURNING_BLOCK_OF_STICKS: DeferredBlock<BlockOfSticksBlock> =
        registerBlock("returning_block_of_sticks") { BlockOfSticksBlock(returning = true) }
    val INVENTORY_REROUTER = basicBlock("inventory_rerouter")
    val SLIME_CUBE = basicBlock("slime_cube")
    val BLAZE_FIRE: DeferredBlock<BlazeFire> =
        registerBlockWithoutItem("blaze_fire") { BlazeFire() }

    /** [net.neoforged.neoforge.common.world.StructureModifier] */
    val PEACE_CANDLE = basicBlock("peace_candle")
    val GLOWING_MUSHROOM = basicBlock("glowing_mushroom")
    val INVENTORY_TESTER = basicBlock("inventory_tester")
    val TRIGGER_GLASS: DeferredBlock<TriggerGlass> =
        registerBlock("trigger_glass") { TriggerGlass() }
    val BLOCK_DESTABILIZER = basicBlock("block_destabilizer")
    val SOUND_BOX = basicBlock("sound_box")
    val SOUND_DAMPENER = basicBlock("sound_dampener")
    val DIAPHANOUS_BLOCK = basicBlock("diaphanous_block")
    val SIDED_BLOCK_OF_REDSTONE =
        registerBlock("sided_redstone") { SidedRedstoneBlock() }
    val ITEM_COLLECTOR = basicBlock("item_collector")
    val ADVANCED_ITEM_COLLECTOR = basicBlock("advanced_item_collector")
    val NATURE_CORE = basicBlock("nature_core")
    val RAINBOW_LAMP =
        registerBlock("rainbow_lamp") { RainbowLampBlock() }

    // Platform
    val OAK_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("oak_platform") { PlatformBlock(blockSetType = BlockSetType.OAK) }
    val SPRUCE_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("spruce_platform") { PlatformBlock(blockSetType = BlockSetType.SPRUCE) }
    val BIRCH_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("birch_platform") { PlatformBlock(blockSetType = BlockSetType.BIRCH) }
    val JUNGLE_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("jungle_platform") { PlatformBlock(blockSetType = BlockSetType.JUNGLE) }
    val ACACIA_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("acacia_platform") { PlatformBlock(blockSetType = BlockSetType.ACACIA) }
    val DARK_OAK_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("dark_oak_platform") { PlatformBlock(blockSetType = BlockSetType.DARK_OAK) }
    val CRIMSON_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("crimson_platform") { PlatformBlock(blockSetType = BlockSetType.CRIMSON) }
    val WARPED_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("warped_platform") { PlatformBlock(blockSetType = BlockSetType.WARPED) }
    val MANGROVE_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("mangrove_platform") { PlatformBlock(blockSetType = BlockSetType.MANGROVE) }
    val BAMBOO_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("bamboo_platform") { PlatformBlock(blockSetType = BlockSetType.BAMBOO) }
    val CHERRY_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("cherry_platform") { PlatformBlock(blockSetType = BlockSetType.CHERRY) }

    // Lubricant
    val SUPER_LUBRICANT_ICE = basicBlock("super_lubricant_ice")
    val SUPER_LUBRICANT_STONE = basicBlock("super_lubricant_stone")
    val SUPER_LUBRICANT_PLATFORM: DeferredBlock<PlatformBlock> =
        registerBlock("super_lubricant_platform") { PlatformBlock(blockSetType = null) }
    val FILTERED_SUPER_LUBRICANT_PLATFORM = basicBlock("filtered_super_lubricant_platform")

    // Detectors
    val ONLINE_DETECTOR = basicBlock("online_detector")
    val CHAT_DETECTOR = basicBlock("chat_detector")
    val GLOBAL_CHAT_DETECTOR = basicBlock("global_chat_detector")
    val ENTITY_DETECTOR = basicBlock("entity_detector")

    // Interfaces
    val PLAYER_INTERFACE = basicBlock("player_interface")
    val NOTIFICATION_INTERFACE = basicBlock("notification_interface")
    val BASIC_REDSTONE_INTERFACE = basicBlock("basic_redstone_interface")
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
    val BIOME_COBBLESTONE = basicBlock("biome_cobblestone")
    val BIOME_STONE = basicBlock("biome_stone")
    val BIOME_STONE_BRICKS = basicBlock("biome_stone_bricks")
    val BIOME_STONE_BRICKS_CRACKED = basicBlock("biome_stone_bricks_cracked")
    val BIOME_STONE_BRICKS_CHISELED = basicBlock("biome_stone_bricks_chiseled")
    val BIOME_GLASS = basicBlock("biome_glass")

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
    val COLORED_GRASS_WHITE = basicBlock("colored_grass_white")
    val COLORED_GRASS_ORANGE = basicBlock("colored_grass_orange")
    val COLORED_GRASS_MAGENTA = basicBlock("colored_grass_magenta")
    val COLORED_GRASS_LIGHT_BLUE = basicBlock("colored_grass_light_blue")
    val COLORED_GRASS_YELLOW = basicBlock("colored_grass_yellow")
    val COLORED_GRASS_LIME = basicBlock("colored_grass_lime")
    val COLORED_GRASS_PINK = basicBlock("colored_grass_pink")
    val COLORED_GRASS_GRAY = basicBlock("colored_grass_gray")
    val COLORED_GRASS_LIGHT_GRAY = basicBlock("colored_grass_light_gray")
    val COLORED_GRASS_CYAN = basicBlock("colored_grass_cyan")
    val COLORED_GRASS_PURPLE = basicBlock("colored_grass_purple")
    val COLORED_GRASS_BLUE = basicBlock("colored_grass_blue")
    val COLORED_GRASS_BROWN = basicBlock("colored_grass_brown")
    val COLORED_GRASS_GREEN = basicBlock("colored_grass_green")
    val COLORED_GRASS_RED = basicBlock("colored_grass_red")
    val COLORED_GRASS_BLACK = basicBlock("colored_grass_black")

    val LUMINOUS_BLOCK_WHITE = basicBlock("luminous_block_white")
    val LUMINOUS_BLOCK_ORANGE = basicBlock("luminous_block_orange")
    val LUMINOUS_BLOCK_MAGENTA = basicBlock("luminous_block_magenta")
    val LUMINOUS_BLOCK_LIGHT_BLUE = basicBlock("luminous_block_light_blue")
    val LUMINOUS_BLOCK_YELLOW = basicBlock("luminous_block_yellow")
    val LUMINOUS_BLOCK_LIME = basicBlock("luminous_block_lime")
    val LUMINOUS_BLOCK_PINK = basicBlock("luminous_block_pink")
    val LUMINOUS_BLOCK_GRAY = basicBlock("luminous_block_gray")
    val LUMINOUS_BLOCK_LIGHT_GRAY = basicBlock("luminous_block_light_gray")
    val LUMINOUS_BLOCK_CYAN = basicBlock("luminous_block_cyan")
    val LUMINOUS_BLOCK_PURPLE = basicBlock("luminous_block_purple")
    val LUMINOUS_BLOCK_BLUE = basicBlock("luminous_block_blue")
    val LUMINOUS_BLOCK_BROWN = basicBlock("luminous_block_brown")
    val LUMINOUS_BLOCK_GREEN = basicBlock("luminous_block_green")
    val LUMINOUS_BLOCK_RED = basicBlock("luminous_block_red")
    val LUMINOUS_BLOCK_BLACK = basicBlock("luminous_block_black")

    val TRANSLUCENT_LUMINOUS_BLOCK_WHITE = basicBlock("translucent_luminous_block_white")
    val TRANSLUCENT_LUMINOUS_BLOCK_ORANGE = basicBlock("translucent_luminous_block_orange")
    val TRANSLUCENT_LUMINOUS_BLOCK_MAGENTA = basicBlock("translucent_luminous_block_magenta")
    val TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_BLUE = basicBlock("translucent_luminous_block_light_blue")
    val TRANSLUCENT_LUMINOUS_BLOCK_YELLOW = basicBlock("translucent_luminous_block_yellow")
    val TRANSLUCENT_LUMINOUS_BLOCK_LIME = basicBlock("translucent_luminous_block_lime")
    val TRANSLUCENT_LUMINOUS_BLOCK_PINK = basicBlock("translucent_luminous_block_pink")
    val TRANSLUCENT_LUMINOUS_BLOCK_GRAY = basicBlock("translucent_luminous_block_gray")
    val TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_GRAY = basicBlock("translucent_luminous_block_light_gray")
    val TRANSLUCENT_LUMINOUS_BLOCK_CYAN = basicBlock("translucent_luminous_block_cyan")
    val TRANSLUCENT_LUMINOUS_BLOCK_PURPLE = basicBlock("translucent_luminous_block_purple")
    val TRANSLUCENT_LUMINOUS_BLOCK_BLUE = basicBlock("translucent_luminous_block_blue")
    val TRANSLUCENT_LUMINOUS_BLOCK_BROWN = basicBlock("translucent_luminous_block_brown")
    val TRANSLUCENT_LUMINOUS_BLOCK_GREEN = basicBlock("translucent_luminous_block_green")
    val TRANSLUCENT_LUMINOUS_BLOCK_RED = basicBlock("translucent_luminous_block_red")
    val TRANSLUCENT_LUMINOUS_BLOCK_BLACK = basicBlock("translucent_luminous_block_black")

    val STAINED_BRICKS_WHITE = basicBlock("stained_bricks_white")
    val STAINED_BRICKS_ORANGE = basicBlock("stained_bricks_orange")
    val STAINED_BRICKS_MAGENTA = basicBlock("stained_bricks_magenta")
    val STAINED_BRICKS_LIGHT_BLUE = basicBlock("stained_bricks_light_blue")
    val STAINED_BRICKS_YELLOW = basicBlock("stained_bricks_yellow")
    val STAINED_BRICKS_LIME = basicBlock("stained_bricks_lime")
    val STAINED_BRICKS_PINK = basicBlock("stained_bricks_pink")
    val STAINED_BRICKS_GRAY = basicBlock("stained_bricks_gray")
    val STAINED_BRICKS_LIGHT_GRAY = basicBlock("stained_bricks_light_gray")
    val STAINED_BRICKS_CYAN = basicBlock("stained_bricks_cyan")
    val STAINED_BRICKS_PURPLE = basicBlock("stained_bricks_purple")
    val STAINED_BRICKS_BLUE = basicBlock("stained_bricks_blue")
    val STAINED_BRICKS_BROWN = basicBlock("stained_bricks_brown")
    val STAINED_BRICKS_GREEN = basicBlock("stained_bricks_green")
    val STAINED_BRICKS_RED = basicBlock("stained_bricks_red")
    val STAINED_BRICKS_BLACK = basicBlock("stained_bricks_black")

    val LUMINOUS_STAINED_BRICKS_WHITE = basicBlock("luminous_stained_bricks_white")
    val LUMINOUS_STAINED_BRICKS_ORANGE = basicBlock("luminous_stained_bricks_orange")
    val LUMINOUS_STAINED_BRICKS_MAGENTA = basicBlock("luminous_stained_bricks_magenta")
    val LUMINOUS_STAINED_BRICKS_LIGHT_BLUE = basicBlock("luminous_stained_bricks_light_blue")
    val LUMINOUS_STAINED_BRICKS_YELLOW = basicBlock("luminous_stained_bricks_yellow")
    val LUMINOUS_STAINED_BRICKS_LIME = basicBlock("luminous_stained_bricks_lime")
    val LUMINOUS_STAINED_BRICKS_PINK = basicBlock("luminous_stained_bricks_pink")
    val LUMINOUS_STAINED_BRICKS_GRAY = basicBlock("luminous_stained_bricks_gray")
    val LUMINOUS_STAINED_BRICKS_LIGHT_GRAY = basicBlock("luminous_stained_bricks_light_gray")
    val LUMINOUS_STAINED_BRICKS_CYAN = basicBlock("luminous_stained_bricks_cyan")
    val LUMINOUS_STAINED_BRICKS_PURPLE = basicBlock("luminous_stained_bricks_purple")
    val LUMINOUS_STAINED_BRICKS_BLUE = basicBlock("luminous_stained_bricks_blue")
    val LUMINOUS_STAINED_BRICKS_BROWN = basicBlock("luminous_stained_bricks_brown")
    val LUMINOUS_STAINED_BRICKS_GREEN = basicBlock("luminous_stained_bricks_green")
    val LUMINOUS_STAINED_BRICKS_RED = basicBlock("luminous_stained_bricks_red")
    val LUMINOUS_STAINED_BRICKS_BLACK = basicBlock("luminous_stained_bricks_black")

    // Internal
    val FLOO_BRICK = basicBlock("floo_brick")
    val ANCIENT_BRICK = basicBlock("ancient_brick") // "Internal"? Whatever that means


    private fun basicBlock(name: String) = registerBlock(name) { Block(BlockBehaviour.Properties.of()) }

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

}