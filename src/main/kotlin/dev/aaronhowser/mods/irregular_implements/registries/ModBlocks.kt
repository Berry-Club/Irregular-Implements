package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister

object ModBlocks {

    val BLOCK_REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(IrregularImplements.ID)

    val FERTILIZED_DIRT = basicBlock("fertilized_dirt")
    val LAPIS_GLASS = basicBlock("lapis_glass")
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
    val PLATFORM = basicBlock("platform")   // Same block for every variant
    val QUARTZ_LAMP = basicBlock("quartz_lamp")
    val QUARTZ_GLASS = basicBlock("quartz_glass")
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
    val LUMINOUS_BLOCK = basicBlock("luminous_block")   // Same block for all colors
    val TRANSLUCENT_LUMINOUS_BLOCK = basicBlock("translucent_luminous_block")   // Same block for all colors
    val INVENTORY_REROUTER = basicBlock("inventory_rerouter")
    val SLIME_CUBE = basicBlock("slime_cube")

    /** [net.neoforged.neoforge.common.world.StructureModifier] */
    val PEACE_CANDLE = basicBlock("peace_candle")
    val GLOWING_MUSHROOM = basicBlock("glowing_mushroom")
    val INVENTORY_TESTER = basicBlock("inventory_tester")
    val TRIGGER_GLASS = basicBlock("trigger_glass")
    val BLOCK_DESTABILIZER = basicBlock("block_destabilizer")
    val SOUND_BOX = basicBlock("sound_box")
    val SOUND_DAMPENER = basicBlock("sound_dampener")
    val DIAPHANOUS_BLOCK = basicBlock("diaphanous_block")
    val SIDED_BLOCK_OF_REDSTONE =
        registerBlock("sided_redstone") { SidedRedstoneBlock() }
    val ITEM_COLLECTOR = basicBlock("item_collector")
    val ADVANCED_ITEM_COLLECTOR = basicBlock("advanced_item_collector")
    val NATURE_CORE = basicBlock("nature_core")
    val COLORED_GRASS = basicBlock("colored_grass") // Same block for all colors
    val RAINBOW_LAMP =
        registerBlock("rainbow_lamp") { RainbowLampBlock() }
    val STAINED_BRICKS = basicBlock("stained_bricks") // Same block for all colors
    val LUMINOUS_STAINED_BRICKS = basicBlock("luminous_stained_bricks") // Same block for all colors

    // Lubricant
    val SUPER_LUBRICANT_ICE = basicBlock("super_lubricant_ice")
    val SUPER_LUBRICANT_PLATFORM = basicBlock("super_lubricant_platform")
    val FILTERED_SUPER_LUBRICANT_PLATFORM = basicBlock("filtered_super_lubricant_platform")
    val SUPER_LUBRICANT_STONE = basicBlock("super_lubricant_stone")

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

}