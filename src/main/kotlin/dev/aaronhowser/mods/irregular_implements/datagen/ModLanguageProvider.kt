package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.config.ClientConfig
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.ChatFormatting
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue
import net.neoforged.neoforge.common.data.LanguageProvider

class ModLanguageProvider(
    output: PackOutput
) : LanguageProvider(output, IrregularImplements.ID, "en_us") {

    companion object {
        fun String.toComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args)
        fun String.toGrayComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args).withStyle(ChatFormatting.GRAY)

        fun getInfoString(itemLike: ItemLike): String {
            val location = BuiltInRegistries.ITEM.getKey(itemLike.asItem())

            return StringBuilder()
                .append("info.")
                .append(location.namespace)
                .append(".")
                .append(location.path)
                .toString()
        }
    }

    override fun addTranslations() {
        addTooltips()
        addItems()
        addBlocks()
        addInfo()
        addEffects()
        addSubtitles()
        addMessages()
        addMisc()
        addConfigs()
    }

    private fun addConfig(config: ConfigValue<*>, desc: String) {
        val configString = StringBuilder()
            .append(IrregularImplements.ID)
            .append(".configuration.")
            .append(config.path.last())
            .toString()

        add(configString, desc)
    }

    private fun addConfigCategory(category: String, desc: String) {
        val categoryString = StringBuilder()
            .append(IrregularImplements.ID)
            .append(".configuration.")
            .append(category)
            .toString()

        add(categoryString, desc)
    }

    private fun addConfigs() {
        addConfig(ClientConfig.COLLAPSE_INVERTS_MOUSE, "Collapse Inverts Mouse")
        addConfig(ClientConfig.HIDE_CUSTOM_CRAFTING_TABLE_RECIPE_BOOK_BUTTON, "Hide Recipe Book Button In Custom Crafting Table")

        addConfig(ServerConfig.BLOCK_DESTABILIZER_LIMIT, "Block Destabilizer Limit")
        addConfig(ServerConfig.BIOME_PAINTER_HORIZONTAL_RADIUS, "Biome Painter Horizontal Radius")
        addConfig(ServerConfig.BIOME_PAINTER_BLOCKS_ABOVE, "Biome Painter Blocks Above")
        addConfig(ServerConfig.BIOME_PAINTER_BLOCKS_BELOW, "Biome Painter Blocks Below")

        addConfig(ServerConfig.BLOCK_MOVER_TRY_VAPORIZE_FLUID, "Block Mover Vaporizes Fluid")
        addConfig(ServerConfig.PORTABLE_ENDER_BRIDGE_RANGE, "Portable Ender Bridge Range")
        addConfig(ServerConfig.SUMMONING_PENDULUM_CAPACITY, "Summoning Pendulum Capacity")
        addConfig(ServerConfig.BLOCK_REPLACER_UNIQUE_BLOCKS, "Block Replacer Unique Blocks")
        addConfig(ServerConfig.DIVINING_ROD_CHECK_RADIUS, "Divining Rod Check Radius")
        addConfig(ServerConfig.RAIN_SHIELD_CHUNK_RADIUS, "Rain Shield Chunk Radius")
        addConfig(ServerConfig.ESCAPE_ROPE_MAX_BLOCKS, "Escape Rope Max Blocks")
        addConfig(ServerConfig.ESCAPE_ROPE_BLOCKS_PER_TICK, "Escape Rope Blocks Per Tick")

        addConfig(ServerConfig.SPECTRE_IMBUE_CHANCE, "Spectre Imbue Chance")

        addConfig(ServerConfig.SPECTRE_BUFFER_CAPACITY, "Spectre Buffer Capacity")
        addConfig(ServerConfig.SPECTRE_BASIC_RATE, "Basic Coil Rate")
        addConfig(ServerConfig.SPECTRE_REDSTONE_RATE, "Redstone Coil Rate")
        addConfig(ServerConfig.SPECTRE_ENDER_RATE, "Ender Coil Rate")
        addConfig(ServerConfig.SPECTRE_NUMBER_RATE, "Number Coil Rate")
        addConfig(ServerConfig.SPECTRE_GENESIS_RATE, "Genesis Coil Rate")

        addConfig(ServerConfig.SPECTRE_CHARGER_BASIC, "Basic Charger Rate")
        addConfig(ServerConfig.SPECTRE_CHARGER_REDSTONE, "Redstone Charger Rate")
        addConfig(ServerConfig.SPECTRE_CHARGER_ENDER, "Ender Charger Rate")
        addConfig(ServerConfig.SPECTRE_CHARGER_GENESIS, "Genesis Charger Rate")

        addConfigCategory(ServerConfig.SPECTRE_CATEGORY, "Spectre")
    }

    object Misc {
        const val MAGNETIC_NAME = "enchantment.irregular_implements.magnetic"
        const val MAGNETIC_DESC = "enchantment.irregular_implements.magnetic.desc"

        const val MAGNETIC_TAG = "tag.item.irregular_implements.enchantable.magnetic"
        const val GRASS_SEEDS_TAG = "tag.item.irregular_implements.grass_seeds"
        const val HIDE_NAME_HELMET_TAG = "tag.item.irregular_implements.hide_name_helmet"
        const val HIDE_POTION_HELMET_TAG = "tag.item.irregular_implements.hide_potion_helmet"
        const val SUPER_LUBRICATED_TAG = "tag.item.irregular_implements.super_lubricated"
        const val CUSTOM_CRAFTING_TABLE_ITEMS_TAG = "tag.item.irregular_implements.custom_crafting_table_items"
        const val SPECTRE_ANCHOR_BLACKLIST_TAG = "tag.item.irregular_implements.spectre_anchor_blacklist"

        const val C_BEAN_TAG = "tag.item.c.crops.bean"
        const val C_OBSIDIAN_RODS_TAG = "tag.item.c.rods.obsidian"

        const val ALLOWS_LAVA_WALKING = "tag.fluid.irregular_implements.allows_lava_walking"
        const val ALLOWS_WATER_WALKING = "tag.fluid.irregular_implements.allows_water_walking"
    }

    private fun addMisc() {
        add(Misc.MAGNETIC_NAME, "Magnetic")
        add(Misc.MAGNETIC_DESC, "Teleports fresh item drops towards you")

        add(Misc.MAGNETIC_TAG, "Magnetic Enchantable")
        add(Misc.GRASS_SEEDS_TAG, "Grass Seeds")
        add(Misc.HIDE_NAME_HELMET_TAG, "Hides Name Helmet")
        add(Misc.HIDE_POTION_HELMET_TAG, "Hides Potion Helmet")
        add(Misc.SUPER_LUBRICATED_TAG, "Super Lubricated Boots")
        add(Misc.CUSTOM_CRAFTING_TABLE_ITEMS_TAG, "Custom Crafting Table Items")
        add(Misc.SPECTRE_ANCHOR_BLACKLIST_TAG, "Spectre Anchor Blacklist")

        add(Misc.C_BEAN_TAG, "Beans")
        add(Misc.C_OBSIDIAN_RODS_TAG, "Obsidian Rods")

        add(Misc.ALLOWS_WATER_WALKING, "Allows Water Walking")
        add(Misc.ALLOWS_LAVA_WALKING, "Allows Lava Walking")
    }

    object Messages {
        const val ENDER_BRIDGE_ITERATIONS = "message.irregular_implements.ender_bridge.iterations"
        const val ENDER_BRIDGE_HIT_BLOCK = "message.irregular_implements.ender_bridge.hit_block"
        const val REDSTONE_TOOL_BASE_SET = "message.irregular_implements.redstone_tool.base_block_set"
        const val REDSTONE_TOOL_INVALID_BASE_BLOCK = "message.irregular_implements.redstone_tool.no_base_block"
        const val REDSTONE_TOOL_WRONG_DIMENSION = "message.irregular_implements.redstone_tool.wrong_dimension"
        const val REDSTONE_TOOL_UNLOADED = "message.irregular_implements.redstone_tool.unloaded"
        const val REDSTONE_TOOL_BASE_NOT_LINKABLE = "message.irregular_implements.redstone_tool.base_not_linkable"
        const val REDSTONE_TOOL_LINKED = "message.irregular_implements.redstone_tool.linked"
        const val FLUID_FALL_DEATH_BOOT = "death.fell.accident.fluid_fall.boot"
        const val FLUID_FALL_DEATH_GENERIC = "death.fell.accident.fluid_fall.generic"
        const val ILLUMINATOR_ALREADY_PRESENT = "message.irregular_implements.spectre_illuminator.already_present"
        const val FE_RATIO = "message.irregular_implements.fe_ratio"
        const val ADVANCED_ITEM_COLLECTOR_X_RADIUS = "message.irregular_implements.advanced_item_collector.x_radius"
        const val ADVANCED_ITEM_COLLECTOR_Y_RADIUS = "message.irregular_implements.advanced_item_collector.y_radius"
        const val ADVANCED_ITEM_COLLECTOR_Z_RADIUS = "message.irregular_implements.advanced_item_collector.z_radius"
    }

    private fun addMessages() {
        add(Messages.ENDER_BRIDGE_ITERATIONS, "Ender Bridge stopped searching after %d blocks.")
        add(Messages.ENDER_BRIDGE_HIT_BLOCK, "Ender Bridge stopped searching because it hit a %s at %d %d %d.")
        add(Messages.REDSTONE_TOOL_BASE_SET, "Redstone Tool linked to the %s at %d %d %d.")
        add(Messages.REDSTONE_TOOL_INVALID_BASE_BLOCK, "Cannot link as no base block is set.")
        add(Messages.REDSTONE_TOOL_WRONG_DIMENSION, "Cannot link as base %s is in a different dimension.")
        add(Messages.REDSTONE_TOOL_UNLOADED, "Cannot link as base %s is in an unloaded chunk.")
        add(Messages.REDSTONE_TOOL_BASE_NOT_LINKABLE, "Cannot link as base %s was replaced with a %s.")
        add(Messages.REDSTONE_TOOL_LINKED, "Linked the %s at %d %d %d to the %s at %d %d %d.")
        add(Messages.FLUID_FALL_DEATH_BOOT, "%s splattered against the surface of %s because they were wearing %s")
        add(Messages.FLUID_FALL_DEATH_GENERIC, "%s splattered against the surface of %s because they could walk on it")
        add(Messages.ILLUMINATOR_ALREADY_PRESENT, "This chunk already has a Spectre Illuminator!")
        add(Messages.FE_RATIO, "%s FE / %s FE")
        add(Messages.ADVANCED_ITEM_COLLECTOR_X_RADIUS, "X Radius: %d")
        add(Messages.ADVANCED_ITEM_COLLECTOR_Y_RADIUS, "Y Radius: %d")
        add(Messages.ADVANCED_ITEM_COLLECTOR_Z_RADIUS, "Z Radius: %d")
    }

    object Subtitles {
        const val FART = "subtitle.irregular_implements.fart"
    }

    private fun addSubtitles() {
        add(Subtitles.FART, "Pbbbt")
    }

    object Items {
        const val CREATIVE_TAB = "itemGroup.irregular_implements"
    }

    private fun addItems() {

        add(Items.CREATIVE_TAB, "Irregular Implements")

        addItem(ModItems.STABLE_ENDER_PEARL, "Stable Ender Pearl")
        addItem(ModItems.BIOME_CRYSTAL, "Biome Crystal")
        addItem(ModItems.LOCATION_FILTER, "Location Filter")
        addItem(ModItems.ID_CARD, "ID Card")
        addItem(ModItems.ENTITY_FILTER, "Entity Filter")
        addItem(ModItems.SUMMONING_PENDULUM, "Summoning Pendulum")
        addItem(ModItems.BEAN, "Bean")
        addItem(ModItems.LESSER_MAGIC_BEAN, "Lesser Magic Bean")
        addItem(ModItems.MAGIC_BEAN, "Magic Bean")
        addItem(ModItems.BEAN_STEW, "Bean Stew")
        addItem(ModItems.WATER_WALKING_BOOTS, "Water Walking Boots")
        addItem(ModItems.LOOT_GENERATOR, "Loot Generator")
        addItem(ModItems.LAVA_CHARM, "Lava Charm")
        addItem(ModItems.LAVA_WADERS, "Lava Waders")
        addItem(ModItems.OBSIDIAN_SKULL, "Obsidian Skull")
        addItem(ModItems.OBSIDIAN_SKULL_RING, "Obsidian Skull Ring")
        addItem(ModItems.OBSIDIAN_WATER_WALKING_BOOTS, "Obsidian Water Walking Boots")
        addItem(ModItems.MAGIC_HOOD, "Magic Hood")
        addItem(ModItems.BOTTLE_OF_AIR, "Bottle of Air")
        addItem(ModItems.ENDER_LETTER, "Ender Letter")
        addItem(ModItems.ECTOPLASM, "Ectoplasm")
        addItem(ModItems.BIOME_SENSOR, "Biome Sensor")
        addItem(ModItems.LUMINOUS_POWDER, "Luminous Powder")
        addItem(ModItems.PLATE_BASE, "Plate Base")
        addItem(ModItems.LOTUS_BLOSSOM, "Lotus Blossom")
        addItem(ModItems.GOLDEN_EGG, "Golden Egg")
        addItem(ModItems.BLACKOUT_POWDER, "Blackout Powder")
        addItem(ModItems.ITEM_FILTER, "Item Filter")
        addItem(ModItems.EMERALD_COMPASS, "Emerald Compass")
        addItem(ModItems.BLAZE_AND_STEEL, "Blaze and Steel")
        addItem(ModItems.PORTKEY, "Portkey")
        addItem(ModItems.LOTUS_SEEDS, "Lotus Seeds")
        addItem(ModItems.ESCAPE_ROPE, "Escape Rope")
        addItem(ModItems.WEATHER_EGG_SUNNY, "Weather Egg (Sunny)")
        addItem(ModItems.WEATHER_EGG_RAINY, "Weather Egg (Rainy)")
        addItem(ModItems.WEATHER_EGG_STORMY, "Weather Egg (Stormy)")
        addItem(ModItems.ENDER_BUCKET, "Ender Bucket")
        addItem(ModItems.REINFORCED_ENDER_BUCKET, "Reinforced Ender Bucket")
        addItem(ModItems.CHUNK_ANALYZER, "Chunk Analyzer")
        addItem(ModItems.DIVINING_ROD, "Divining Rod")
        addItem(ModItems.BLOCK_MOVER, "Block Mover")
        addItem(ModItems.TRANSFORMATION_CORE, "Transformation Core")
        addItem(ModItems.BIOME_CAPSULE, "Biome Capsule")
        addItem(ModItems.BIOME_PAINTER, "Biome Painter")
        addItem(ModItems.OBSIDIAN_ROD, "Obsidian Rod")
        addItem(ModItems.DROP_FILTER, "Drop Filter")
        addItem(ModItems.VOIDING_DROP_FILTER, "Voiding Drop Filter")
        addItem(ModItems.VOID_STONE, "Void Stone")
        addItem(ModItems.WHITE_STONE, "White Stone")
        addItem(ModItems.MAGNETIC_FORCE, "Magnetic Force")
        addItem(ModItems.PORTABLE_ENDER_BRIDGE, "Portable Ender Bridge")
        addItem(ModItems.DIAMOND_BREAKER, "Diamond Breaker")
        addItem(ModItems.BLOCK_REPLACER, "Block Replacer")
        addItem(ModItems.DIAPHANOUS_BLOCK, "Diaphanous Block")
        addItem(ModItems.EVIL_TEAR, "Evil Tear")

        addItem(ModItems.FIRE_IMBUE, "Fire Imbue")
        addItem(ModItems.POISON_IMBUE, "Poison Imbue")
        addItem(ModItems.EXPERIENCE_IMBUE, "Experience Imbue")
        addItem(ModItems.WITHER_IMBUE, "Wither Imbue")
        addItem(ModItems.COLLAPSE_IMBUE, "Collapse Imbue")
        addItem(ModItems.SPECTRE_IMBUE, "Spectre Imbue")

        addItem(ModItems.SPECTRE_INGOT, "Spectre Ingot")
        addItem(ModItems.SPECTRE_STRING, "Spectre String")
        addItem(ModItems.SPECTRE_ILLUMINATOR, "Spectre Illuminator")
        addItem(ModItems.SPECTRE_KEY, "Spectre Key")
        addItem(ModItems.SPECTRE_ANCHOR, "Spectre Anchor")
        addItem(ModItems.SPECTRE_CHARGER_BASIC, "Spectre Charger")
        addItem(ModItems.SPECTRE_CHARGER_REDSTONE, "Redstone Spectre Charger")
        addItem(ModItems.SPECTRE_CHARGER_ENDER, "Ender Spectre Charger")
        addItem(ModItems.SPECTRE_CHARGER_GENESIS, "Genesis Spectre Charger")
        addItem(ModItems.SPECTRE_SWORD, "Spectre Sword")
        addItem(ModItems.SPECTRE_PICKAXE, "Spectre Pickaxe")
        addItem(ModItems.SPECTRE_AXE, "Spectre Axe")
        addItem(ModItems.SPECTRE_SHOVEL, "Spectre Shovel")
        addItem(ModItems.SPECTRE_HELMET, "Spectre Helmet")
        addItem(ModItems.SPECTRE_CHESTPLATE, "Spectre Chestplate")
        addItem(ModItems.SPECTRE_LEGGINGS, "Spectre Leggings")
        addItem(ModItems.SPECTRE_BOOTS, "Spectre Boots")

        addItem(ModItems.REDSTONE_TOOL, "Redstone Tool")
        addItem(ModItems.REDSTONE_ACTIVATOR, "Redstone Activator")
        addItem(ModItems.REDSTONE_REMOTE, "Redstone Remote")

        addItem(ModItems.SUPER_LUBRICANT_TINCTURE, "Super Lubricant Tincture")

        addItem(ModItems.FLOO_POWDER, "Floo Powder")
        addItem(ModItems.FLOO_SIGN, "Floo Sign")
        addItem(ModItems.FLOO_TOKEN, "Floo Token")
        addItem(ModItems.FLOO_POUCH, "Floo Pouch")

        addItem(ModItems.SOUND_PATTERN, "Sound Pattern")
        addItem(ModItems.SOUND_RECORDER, "Sound Recorder")
        addItem(ModItems.PORTABLE_SOUND_DAMPENER, "Portable Sound Dampener")

        addItem(ModItems.GRASS_SEEDS, "Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_WHITE, "White Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_ORANGE, "Orange Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_MAGENTA, "Magenta Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_LIGHT_BLUE, "Light Blue Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_YELLOW, "Yellow Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_LIME, "Lime Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_PINK, "Pink Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_GRAY, "Gray Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_LIGHT_GRAY, "Light Gray Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_CYAN, "Cyan Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_PURPLE, "Purple Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_BLUE, "Blue Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_BROWN, "Brown Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_GREEN, "Green Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_RED, "Red Grass Seeds")
        addItem(ModItems.GRASS_SEEDS_BLACK, "Black Grass Seeds")
    }

    private fun addBlocks() {
        addBlock(ModBlocks.ADVANCED_REDSTONE_REPEATER, "Advanced Redstone Repeater")
        addBlock(ModBlocks.ADVANCED_REDSTONE_TORCH, "Advanced Redstone Torch")
        addBlock(ModBlocks.CUSTOM_CRAFTING_TABLE, "Custom Crafting Table")
        addBlock(ModBlocks.FERTILIZED_DIRT, "Fertilized Dirt")
        addBlock(ModBlocks.LAPIS_GLASS, "Lapis Glass")
        addBlock(ModBlocks.ENDER_BRIDGE, "Ender Bridge")
        addBlock(ModBlocks.PRISMARINE_ENDER_BRIDGE, "Prismarine Ender Bridge")
        addBlock(ModBlocks.ENDER_ANCHOR, "Ender Anchor")
        addBlock(ModBlocks.BEAN_POD, "Bean Pod")
        addBlock(ModBlocks.IMBUING_STATION, "Imbuing Station")
        addBlock(ModBlocks.NATURE_CHEST, "Nature Chest")
        addBlock(ModBlocks.WATER_CHEST, "Water Chest")
        addBlock(ModBlocks.ANALOG_EMITTER, "Analog Emitter")
        addBlock(ModBlocks.FLUID_DISPLAY, "Fluid Display")
        addBlock(ModBlocks.ENDER_MAILBOX, "Ender Mailbox")
        addBlock(ModBlocks.PITCHER_PLANT, "Pitcher Plant")
        addBlock(ModBlocks.QUARTZ_GLASS, "Quartz Glass")
        addBlock(ModBlocks.POTION_VAPORIZER, "Potion Vaporizer")
        addBlock(ModBlocks.CONTACT_BUTTON, "Contact Button")
        addBlock(ModBlocks.CONTACT_LEVER, "Contact Lever")
        addBlock(ModBlocks.RAIN_SHIELD, "Rain Shield")
        addBlock(ModBlocks.BLOCK_BREAKER, "Block Breaker")
        addBlock(ModBlocks.COMPRESSED_SLIME_BLOCK, "Compressed Slime Block")
        addBlock(ModBlocks.REDSTONE_OBSERVER, "Redstone Observer")
        addBlock(ModBlocks.BIOME_RADAR, "Biome Radar")
        addBlock(ModBlocks.IRON_DROPPER, "Iron Dropper")
        addBlock(ModBlocks.IGNITER, "Igniter")
        addBlock(ModBlocks.BLOCK_OF_STICKS, "Block of Sticks")
        addBlock(ModBlocks.RETURNING_BLOCK_OF_STICKS, "Returning Block of Sticks")
        addBlock(ModBlocks.INVENTORY_REROUTER, "Inventory Rerouter")
        addBlock(ModBlocks.SLIME_CUBE, "Slime Cube")
        addBlock(ModBlocks.PEACE_CANDLE, "Peace Candle")
        addBlock(ModBlocks.GLOWING_MUSHROOM, "Glowing Mushroom")
        addBlock(ModBlocks.INVENTORY_TESTER, "Inventory Tester")
        addBlock(ModBlocks.TRIGGER_GLASS, "Trigger Glass")
        addBlock(ModBlocks.BLOCK_DESTABILIZER, "Block Destabilizer")
        addBlock(ModBlocks.SOUND_BOX, "Sound Box")
        addBlock(ModBlocks.SOUND_DAMPENER, "Sound Dampener")
        addBlock(ModBlocks.SIDED_BLOCK_OF_REDSTONE, "Sided Block of Redstone")
        addBlock(ModBlocks.ITEM_COLLECTOR, "Item Collector")
        addBlock(ModBlocks.ADVANCED_ITEM_COLLECTOR, "Advanced Item Collector")
        addBlock(ModBlocks.NATURE_CORE, "Nature Core")
        addBlock(ModBlocks.RAINBOW_LAMP, "Rainbow Lamp")
        addBlock(ModBlocks.LOTUS, "Lotus")
        addBlock(ModBlocks.MOON_PHASE_DETECTOR, "Moon Phase Detector")
        addBlock(ModBlocks.ENERGY_DISTRIBUTOR, "Energy Distributor")
        addBlock(ModBlocks.ENDER_ENERGY_DISTRIBUTOR, "Ender Energy Distributor")
        addBlock(ModBlocks.SHOCK_ABSORBER, "Shock Absorber")
        addBlock(ModBlocks.AUTO_PLACER, "Auto Placer")
        addBlock(ModBlocks.BLOCK_TELEPORTER, "Block Teleporter")
        addBlock(ModBlocks.DIAPHANOUS_BLOCK, "Diaphanous Block")
        addBlock(ModBlocks.SAKANADE_SPORES, "Sakanade Spores")

        addBlock(ModBlocks.SUPER_LUBRICANT_ICE, "Super Lubricant Ice")
        addBlock(ModBlocks.SUPER_LUBRICANT_PLATFORM, "Super Lubricant Platform")
        addBlock(ModBlocks.FILTERED_SUPER_LUBRICANT_PLATFORM, "Filtered Super Lubricant Platform")
        addBlock(ModBlocks.SUPER_LUBRICANT_STONE, "Super Lubricant Stone")

        addBlock(ModBlocks.ONLINE_DETECTOR, "Online Detector")
        addBlock(ModBlocks.CHAT_DETECTOR, "Chat Detector")
        addBlock(ModBlocks.GLOBAL_CHAT_DETECTOR, "Global Chat Detector")
        addBlock(ModBlocks.ENTITY_DETECTOR, "Entity Detector")

        addBlock(ModBlocks.PLAYER_INTERFACE, "Player Interface")
        addBlock(ModBlocks.NOTIFICATION_INTERFACE, "Notification Interface")
        addBlock(ModBlocks.BASIC_REDSTONE_INTERFACE, "Basic Redstone Interface")
        addBlock(ModBlocks.ADVANCED_REDSTONE_INTERFACE, "Advanced Redstone Interface")

        addBlock(ModBlocks.SPECTRE_BLOCK, "Spectre Block")
        addBlock(ModBlocks.SPECTRE_LENS, "Spectre Lens")
        addBlock(ModBlocks.SPECTRE_ENERGY_INJECTOR, "Spectre Energy Injector")
        addBlock(ModBlocks.SPECTRE_COIL_BASIC, "Spectre Coil")
        addBlock(ModBlocks.SPECTRE_COIL_REDSTONE, "Redstone Spectre Coil")
        addBlock(ModBlocks.SPECTRE_COIL_ENDER, "Ender Spectre Coil")
        addBlock(ModBlocks.SPECTRE_COIL_NUMBER, "Spectre Coil Nr. 245")
        addBlock(ModBlocks.SPECTRE_COIL_GENESIS, "Genesis Spectre Coil")
        addBlock(ModBlocks.SPECTRE_PLANKS, "Spectre Planks")
        addBlock(ModBlocks.SPECTRE_SAPLING, "Spectre Sapling")
        addBlock(ModBlocks.SPECTRE_LOG, "Spectre Log")
        addBlock(ModBlocks.STRIPPED_SPECTRE_LOG, "Stripped Spectre Log")
        addBlock(ModBlocks.SPECTRE_WOOD, "Spectre Wood")
        addBlock(ModBlocks.SPECTRE_LEAVES, "Spectre Leaves")

        addBlock(ModBlocks.BIOME_COBBLESTONE, "Biome Cobblestone")
        addBlock(ModBlocks.BIOME_STONE, "Biome Stone")
        addBlock(ModBlocks.BIOME_STONE_BRICKS, "Biome Stone Bricks")
        addBlock(ModBlocks.BIOME_STONE_BRICKS_CRACKED, "Cracked Biome Stone Bricks")
        addBlock(ModBlocks.BIOME_STONE_BRICKS_CHISELED, "Chiseled Biome Stone Bricks")
        addBlock(ModBlocks.BIOME_GLASS, "Biome Glass")

        addBlock(ModBlocks.PROCESSING_PLATE, "Processing Plate")
        addBlock(ModBlocks.REDIRECTOR_PLATE, "Redirector Plate")
        addBlock(ModBlocks.FILTERED_REDIRECTOR_PLATE, "Filtered Redirector Plate")
        addBlock(ModBlocks.REDSTONE_PLATE, "Redstone Plate")
        addBlock(ModBlocks.CORRECTOR_PLATE, "Corrector Plate")
        addBlock(ModBlocks.ITEM_SEALER_PLATE, "Item Sealer Plate")
        addBlock(ModBlocks.ITEM_REJUVENATOR_PLATE, "Item Rejuvenator Plate")
        addBlock(ModBlocks.ACCELERATOR_PLATE, "Accelerator Plate")
        addBlock(ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE, "Directional Accelerator Plate")
        addBlock(ModBlocks.BOUNCY_PLATE, "Bouncy Plate")
        addBlock(ModBlocks.COLLECTION_PLATE, "Collection Plate")
        addBlock(ModBlocks.EXTRACTION_PLATE, "Extraction Plate")

        addBlock(ModBlocks.COLORED_GRASS_WHITE, "White Grass")
        addBlock(ModBlocks.COLORED_GRASS_ORANGE, "Orange Grass")
        addBlock(ModBlocks.COLORED_GRASS_MAGENTA, "Magenta Grass")
        addBlock(ModBlocks.COLORED_GRASS_LIGHT_BLUE, "Light Blue Grass")
        addBlock(ModBlocks.COLORED_GRASS_YELLOW, "Yellow Grass")
        addBlock(ModBlocks.COLORED_GRASS_LIME, "Lime Grass")
        addBlock(ModBlocks.COLORED_GRASS_PINK, "Pink Grass")
        addBlock(ModBlocks.COLORED_GRASS_GRAY, "Gray Grass")
        addBlock(ModBlocks.COLORED_GRASS_LIGHT_GRAY, "Light Gray Grass")
        addBlock(ModBlocks.COLORED_GRASS_CYAN, "Cyan Grass")
        addBlock(ModBlocks.COLORED_GRASS_PURPLE, "Purple Grass")
        addBlock(ModBlocks.COLORED_GRASS_BLUE, "Blue Grass")
        addBlock(ModBlocks.COLORED_GRASS_BROWN, "Brown Grass")
        addBlock(ModBlocks.COLORED_GRASS_GREEN, "Green Grass")
        addBlock(ModBlocks.COLORED_GRASS_RED, "Red Grass")
        addBlock(ModBlocks.COLORED_GRASS_BLACK, "Black Grass")

        addBlock(ModBlocks.LUMINOUS_BLOCK_WHITE, "White Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_ORANGE, "Orange Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_MAGENTA, "Magenta Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_LIGHT_BLUE, "Light Blue Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_YELLOW, "Yellow Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_LIME, "Lime Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_PINK, "Pink Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_GRAY, "Gray Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_LIGHT_GRAY, "Light Gray Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_CYAN, "Cyan Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_PURPLE, "Purple Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_BLUE, "Blue Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_BROWN, "Brown Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_GREEN, "Green Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_RED, "Red Luminous Block")
        addBlock(ModBlocks.LUMINOUS_BLOCK_BLACK, "Black Luminous Block")

        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_WHITE, "White Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_ORANGE, "Orange Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_MAGENTA, "Magenta Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_BLUE, "Light Blue Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_YELLOW, "Yellow Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_LIME, "Lime Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_PINK, "Pink Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_GRAY, "Gray Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_LIGHT_GRAY, "Light Gray Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_CYAN, "Cyan Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_PURPLE, "Purple Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_BLUE, "Blue Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_BROWN, "Brown Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_GREEN, "Green Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_RED, "Red Translucent Luminous Block")
        addBlock(ModBlocks.TRANSLUCENT_LUMINOUS_BLOCK_BLACK, "Black Translucent Luminous Block")

        addBlock(ModBlocks.STAINED_BRICKS_WHITE, "White Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_ORANGE, "Orange Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_MAGENTA, "Magenta Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_LIGHT_BLUE, "Light Blue Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_YELLOW, "Yellow Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_LIME, "Lime Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_PINK, "Pink Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_GRAY, "Gray Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_LIGHT_GRAY, "Light Gray Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_CYAN, "Cyan Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_PURPLE, "Purple Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_BLUE, "Blue Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_BROWN, "Brown Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_GREEN, "Green Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_RED, "Red Stained Bricks")
        addBlock(ModBlocks.STAINED_BRICKS_BLACK, "Black Stained Bricks")

        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_WHITE, "White Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_ORANGE, "Orange Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_MAGENTA, "Magenta Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_LIGHT_BLUE, "Light Blue Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_YELLOW, "Yellow Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_LIME, "Lime Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_PINK, "Pink Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_GRAY, "Gray Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_LIGHT_GRAY, "Light Gray Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_CYAN, "Cyan Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_PURPLE, "Purple Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_BLUE, "Blue Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_BROWN, "Brown Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_GREEN, "Green Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_RED, "Red Luminous Stained Bricks")
        addBlock(ModBlocks.LUMINOUS_STAINED_BRICKS_BLACK, "Black Luminous Stained Bricks")

        addBlock(ModBlocks.FLOO_BRICK, "Floo Bricks")
        addBlock(ModBlocks.ANCIENT_BRICK, "Ancient Brick")

        addBlock(ModBlocks.OAK_PLATFORM, "Oak Platform")
        addBlock(ModBlocks.SPRUCE_PLATFORM, "Spruce Platform")
        addBlock(ModBlocks.BIRCH_PLATFORM, "Birch Platform")
        addBlock(ModBlocks.JUNGLE_PLATFORM, "Jungle Platform")
        addBlock(ModBlocks.ACACIA_PLATFORM, "Acacia Platform")
        addBlock(ModBlocks.DARK_OAK_PLATFORM, "Dark Oak Platform")
        addBlock(ModBlocks.CRIMSON_PLATFORM, "Crimson Platform")
        addBlock(ModBlocks.WARPED_PLATFORM, "Warped Platform")
        addBlock(ModBlocks.MANGROVE_PLATFORM, "Mangrove Platform")
        addBlock(ModBlocks.BAMBOO_PLATFORM, "Bamboo Platform")
        addBlock(ModBlocks.CHERRY_PLATFORM, "Cherry Platform")

    }

    object Tooltips {
        const val SHIFT_FOR_MORE = "tooltip.irregular_implements.shift_for_more"
        const val ENTITY_FILTER_ENTITY = "tooltip.irregular_implements.entity_filter_entity"
        const val PLAYER_FILTER_PLAYER = "tooltip.irregular_implements.player_filter_player"
        const val PLAYER_FILTER_UUID = "tooltip.irregular_implements.player_filter_uuid"
        const val COMPRESSED_SLIME_AMOUNT = "tooltip.irregular_implements.compressed_slime_AMOUNT"
        const val LUBRICATED = "tooltip.irregular_implements.lubricated"
        const val WITH_BLOCK_ENTITY = "tooltip.irregular_implements.with_block_entity"
        const val CHARGE = "tooltip.irregular_implements.charge"
        const val WHITE_STONE_FULL_MOON = "tooltip.irregular_implements.white_stone_full_moon"
        const val SUMMONING_PENDULUM_FRACTION = "tooltip.irregular_implements.summoning_pendulum_fraction"
        const val LIST_POINT = "tooltip.irregular_implements.summoning_pendulum_list_each"
        const val BLOCK_REPLACER_LOADING = "tooltip.irregular_implements.block_replacer_loading"
        const val BLOCK_REPLACER_UNLOADING = "tooltip.irregular_implements.block_replacer_unloading"
        const val BLOCK_REPLACER_ALT_FOR_LIST = "tooltip.irregular_implements.block_replacer_alt_for_list"
        const val ITEM_COUNT = "tooltip.irregular_implements.item_count"
        const val VOID_STONE_INSERT = "tooltip.irregular_implements.void_stone_insert"
        const val VOID_STONE_HOLDING = "tooltip.irregular_implements.void_stone_holding"
        const val VOID_STONE_REMOVE = "tooltip.irregular_implements.void_stone_remove"
        const val ANCHORED = "tooltip.irregular_implements.anchored"
        const val ALL_ORES = "tooltip.irregular_implements.all_ores"
        const val LAZY = "tooltip.irregular_implements.lazy"
        const val NOT_LAZY = "tooltip.irregular_implements.not_lazy"
        const val SHOW_LAZY_SHAPE = "tooltip.irregular_implements.show_lazy_shape"
        const val FORGET_LAZY_SHAPE = "tooltip.irregular_implements.forget_lazy_shape"
        const val STOPS_MESSAGE = "tooltip.irregular_implements.stops_message"
        const val DOESNT_STOP_MESSAGE = "tooltip.irregular_implements.doesnt_stop_message"
        const val MESSAGE_REGEX = "tooltip.irregular_implements.message_regex"
        const val COIL_TRANSFERS = "tooltip.irregular_implements.coil_transfers"
        const val COIL_GENERATES = "tooltip.irregular_implements.coil_generates"
        const val CHARGER_CHARGES = "tooltip.irregular_implements.charger_charges"
        const val LOCATION_COMPONENT = "tooltip.irregular_implements.location_component"
        const val IRON_DROPPER_CONTINUOUS_POWERED = "tooltip.irregular_implements.iron_dropper_continuous_powered"
        const val IRON_DROPPER_CONTINUOUS = "tooltip.irregular_implements.iron_dropper_continuous"
        const val IRON_DROPPER_PULSE = "tooltip.irregular_implements.iron_dropper_pulse"
        const val IRON_DROPPER_NO_DELAY = "tooltip.irregular_implements.iron_dropper_no_delay"
        const val IRON_DROPPER_FIVE_DELAY = "tooltip.irregular_implements.iron_dropper_five_delay"
        const val IRON_DROPPER_TWENTY_DELAY = "tooltip.irregular_implements.iron_dropper_twenty_delay"
        const val IRON_DROPPER_EXACT_VELOCITY = "tooltip.irregular_implements.iron_dropper_exact_velocity"
        const val IRON_DROPPER_RANDOM_VELOCITY = "tooltip.irregular_implements.iron_dropper_random_velocity"
        const val IRON_DROPPER_NO_EFFECTS = "tooltip.irregular_implements.iron_dropper_no_effects"
        const val IRON_DROPPER_ONLY_SOUND = "tooltip.irregular_implements.iron_dropper_only_sound"
        const val IRON_DROPPER_ONLY_PARTICLES = "tooltip.irregular_implements.iron_dropper_only_particles"
        const val IRON_DROPPER_BOTH_EFFECTS = "tooltip.irregular_implements.iron_dropper_both_effects"
        const val BLOCK = "tooltip.irregular_implements.block"
        const val DIAPHANOUS_INVERTED = "tooltip.irregular_implements.diaphanous_inverted"
        const val ITEM_TAG = "tooltip.irregular_implements.item_tag"
        const val BLACKLIST = "tooltip.irregular_implements.blacklist"
        const val WHITELIST = "tooltip.irregular_implements.whitelist"
        const val IGNITER_TOGGLE = "tooltip.irregular_implements.igniter_toggle"
        const val IGNITER_IGNITE = "tooltip.irregular_implements.igniter_ignite"
        const val IGNITER_KEEP_IGNITED = "tooltip.irregular_implements.igniter_keep_ignited"
        const val ITEM_FILTER_ITEM = "tooltip.irregular_implements.item_filter_item"
        const val ITEM_FILTER_TAG = "tooltip.irregular_implements.item_filter_tag"
        const val ITEM_FILTER_REQUIRE_COMPONENTS = "tooltip.irregular_implements.item_filter_require_components"
        const val ITEM_FILTER_IGNORE_COMPONENTS = "tooltip.irregular_implements.item_filter_ignore_components"
        const val ITEM_FILTER_REQUIRES_SAME_COMPONENTS = "tooltip.irregular_implements.item_filter_requires_same_components"
    }

    private fun addTooltips() {
        add(Tooltips.ITEM_FILTER_ITEM, "Item Filter")
        add(Tooltips.ITEM_FILTER_TAG, "Tag Filter")
        add(Tooltips.ITEM_FILTER_REQUIRE_COMPONENTS, "Require Components")
        add(Tooltips.ITEM_FILTER_IGNORE_COMPONENTS, "Ignore Components")
        add(Tooltips.ITEM_FILTER_REQUIRES_SAME_COMPONENTS, "Requires same components")

        add(Tooltips.SHIFT_FOR_MORE, "Hold SHIFT for more information")
        add(Tooltips.ENTITY_FILTER_ENTITY, "Entity Type: %s")
        add(Tooltips.PLAYER_FILTER_PLAYER, "Player: %s")
        add(Tooltips.PLAYER_FILTER_UUID, "Player UUID: %s")
        add(Tooltips.COMPRESSED_SLIME_AMOUNT, "Compression level %d")
        add(Tooltips.LUBRICATED, "Lubricated")
        add(Tooltips.WITH_BLOCK_ENTITY, "%s with block entity")
        add(Tooltips.CHARGE, "Charge: %d%%")
        add(Tooltips.WHITE_STONE_FULL_MOON, "Charge under the full moon")
        add(Tooltips.SUMMONING_PENDULUM_FRACTION, "Stored: %d/%d")
        add(Tooltips.LIST_POINT, "• %s")
        add(Tooltips.BLOCK_REPLACER_LOADING, "Store blocks by right-clicking them on this stack")
        add(Tooltips.BLOCK_REPLACER_UNLOADING, "Remove blocks by right-clicking this stack on empty slots")
        add(Tooltips.BLOCK_REPLACER_ALT_FOR_LIST, "Hold ALT to see stored blocks")
        add(Tooltips.ITEM_COUNT, "%s x%d")
        add(Tooltips.VOID_STONE_INSERT, "Store items by right-clicking them on this stack")
        add(Tooltips.VOID_STONE_HOLDING, "Currently holding %s, inserting another will void and replace it")
        add(Tooltips.VOID_STONE_REMOVE, "You can remove the %s by right-clicking the Stone into an empty slot")
        add(Tooltips.ANCHORED, "Anchored")
        add(Tooltips.ALL_ORES, "All Ores")
        add(Tooltips.LOCATION_COMPONENT, "%s, %dx %dy %dz")
        add(Tooltips.LAZY, "Lazy")
        add(Tooltips.NOT_LAZY, "Not Lazy")
        add(Tooltips.SHOW_LAZY_SHAPE, "Show Lazy Shape")
        add(Tooltips.FORGET_LAZY_SHAPE, "Forget Lazy Shape")
        add(Tooltips.STOPS_MESSAGE, "Stops message")
        add(Tooltips.DOESNT_STOP_MESSAGE, "Doesn't stop message")
        add(Tooltips.MESSAGE_REGEX, "Message regex")
        add(Tooltips.COIL_TRANSFERS, "Transfers %s RF/t")
        add(Tooltips.COIL_GENERATES, "Generates %s RF/t")
        add(Tooltips.CHARGER_CHARGES, "Charges %s RF/t")
        add(Tooltips.IRON_DROPPER_CONTINUOUS_POWERED, "Eject continuously while powered")
        add(Tooltips.IRON_DROPPER_CONTINUOUS, "Eject continuously")
        add(Tooltips.IRON_DROPPER_PULSE, "Eject when pulsed")
        add(Tooltips.IRON_DROPPER_NO_DELAY, "No pickup delay")
        add(Tooltips.IRON_DROPPER_FIVE_DELAY, "5 tick pickup delay")
        add(Tooltips.IRON_DROPPER_TWENTY_DELAY, "20 tick pickup delay")
        add(Tooltips.IRON_DROPPER_EXACT_VELOCITY, "Exact velocity")
        add(Tooltips.IRON_DROPPER_RANDOM_VELOCITY, "Random velocity")
        add(Tooltips.IRON_DROPPER_NO_EFFECTS, "No effects")
        add(Tooltips.IRON_DROPPER_ONLY_SOUND, "Sound effects")
        add(Tooltips.IRON_DROPPER_ONLY_PARTICLES, "Particle effects")
        add(Tooltips.IRON_DROPPER_BOTH_EFFECTS, "Sound & particle effects")
        add(Tooltips.BLOCK, "Block: %s")
        add(Tooltips.DIAPHANOUS_INVERTED, "Inverted")
        add(Tooltips.ITEM_TAG, "Item Tag: %s")
        add(Tooltips.BLACKLIST, "Blacklist")
        add(Tooltips.WHITELIST, "Whitelist")
        add(Tooltips.IGNITER_TOGGLE, "Toggle")
        add(Tooltips.IGNITER_IGNITE, "Ignite")
        add(Tooltips.IGNITER_KEEP_IGNITED, "Keep ignited")
    }

    object Info {
        const val PLATFORM = "info.irregular_implements.platform"
        const val BIOME_BLOCKS = "info.irregular_implements.biome_blocks"
        const val LUBRICANT = "info.irregular_implements.lubricant"
        const val SPECTRE_ARMOR = "info.irregular_implements.spectre_armor"
        const val SPECTRE_CHARGERS = "info.irregular_implements.spectre_chargers"
        const val MAGNETIC_ENCHANT = "info.irregular_implements.magnetic_enchant"
    }

    private fun addInfo(itemLike: ItemLike, infoString: String) {
        add(getInfoString(itemLike), infoString)
    }

    private fun addInfo() {
        add(Info.MAGNETIC_ENCHANT, "Teleports fresh item drops towards you.")
        addInfo(ModBlocks.FERTILIZED_DIRT, "Fertilized Dirt does not require hydration, grows crops 3 times faster, and can't be trampled.\n\nYou still have to till it with a Hoe to grow crops.")
        addInfo(ModBlocks.PLAYER_INTERFACE, "Exposes the inventory of the block's owner, as if it was the block's inventory.")
        addInfo(ModBlocks.LAPIS_GLASS, "Solid for players, not solid for anything else.")
        addInfo(ModBlocks.ONLINE_DETECTOR, "Emits a Redstone signal if the specified player is online.")
        addInfo(ModBlocks.CHAT_DETECTOR, "Emits a Redstone pulse if the block's owner sends a chat message containing some specified text.")
        addInfo(ModBlocks.ENDER_BRIDGE, "Upon the Ender Bridge receiving a Redstone Signal, it looks for an Ender Anchor in front of it.\n\nIt searches at 20 blocks per second, and then teleports the player standing on top to it.\n\nThere's no distance limit, though there can be no blocks between (save for the block directly in front of the Bridge).")
        addInfo(ModBlocks.PRISMARINE_ENDER_BRIDGE, "Upon the Prismarine Ender Bridge receiving a Redstone Signal, it looks for an Ender Anchor in front of it (with no distance limit, though it must be loaded, and no other blocks can be in the way).\n\nIt searches at 200 blocks per second, and then teleports the player standing on top to it.\n\nThere's no distance limit, though there can be no blocks between (save for the block directly in front of the Bridge).")
        addInfo(ModBlocks.ENDER_ANCHOR, "Destination point for the Ender Bridge or Prismarine Ender Bridge.")
        addInfo(ModBlocks.BEAN_POD, "Found at the top of a grown Magic Bean.\n\nContains loot!")
        addInfo(ModBlocks.SPECTRE_BLOCK, "An indestructible block that spawns in the Spectre Dimension.")
        addInfo(ModBlocks.ANALOG_EMITTER, "Emits a Redstone signal with a configurable strength when the front face is powered.")
        addInfo(ModBlocks.FLUID_DISPLAY, "A solid block that uses the texture of a fluid.\n\nSet the fluid by clicking it with a filled Bucket or fluid container.\n\nRight-click to toggle between still and flowing, and sneak right-click to rotate it.")
        addInfo(ModBlocks.ENDER_MAILBOX, "Allows players to send and receive Ender Letters.")
        addInfo(ModBlocks.PITCHER_PLANT, "Acts as an infinite water source.\n\nSlowly fills adjacent fluid containers.")
        add(Info.PLATFORM, "Only solid to non-sneaking entities.")
        addInfo(ModBlocks.ENTITY_DETECTOR, "Emits a Redstone signal if a configured entity is within a configured range.")
        addInfo(ModBlocks.QUARTZ_GLASS, "Solid for everything but players.")
        addInfo(ModBlocks.POTION_VAPORIZER, "Allows you to fill a room with a potion effect.\n\nInsert Potions and Furnace fuel, and it will scan the area in front of it to check if it's an enclosed area.\n\nIf it is, it'll fill it with the potion effect.")
        addInfo(ModBlocks.CONTACT_LEVER, "Acts like a Lever, but works when the block in front of it is clicked instead.")
        addInfo(ModBlocks.CONTACT_BUTTON, "Acts like a Button, but works when the block in front of it is clicked instead.")
        addInfo(ModBlocks.RAIN_SHIELD, "Prevents rain in a 5 chunk radius.")
        addInfo(ModBlocks.BLOCK_BREAKER, "Breaks blocks in front of it with the effectiveness of an Iron Pickaxe. Drops are inserted into the inventory behind it, or dropped.\n\nCan be disabled with a Redstone signal.\n\nCan be upgraded with a Diamond Breaker.")
        addInfo(ModBlocks.AUTO_PLACER, "Places blocks in front of it from its inventory.\n\nCan be disabled with a Redstone signal.")
        add(Info.LUBRICANT, "Completely frictionless. Anything moving on it will retain its momentum.")
        addInfo(ModBlocks.COMPRESSED_SLIME_BLOCK, "Create by clicking a Slime Block with a Shovel. Can be compressed multiple times.\n\nLaunches entities into the air.")
        addInfo(ModBlocks.REDSTONE_OBSERVER, "Emits the same Redstone signal as a linked block.\n\nUse a Redstone Tool to link it to a block.")
        addInfo(ModBlocks.BIOME_RADAR, "Insert a Biome Crystal and provide a Redstone signal, and it will attempt to find the Crystal's biome.\n\nIf it succeeds, it will spawn particles aiming in its direction.\n\nUsing a Paper on it will create a Location Filter for the biome.")
        addInfo(ModBlocks.IRON_DROPPER, "An upgraded Dropper, where the following can be configured:\n\n• Item pickup delay\n• Item motion randomness\n• Particle/Sound toggle\n• Redstone control")
        addInfo(ModBlocks.IGNITER, "Sets or extinguishes the block on front of it, depending on the Redstone signal.\n\nCan be set to keep the fire lit.")
        addInfo(ModBlocks.BLOCK_OF_STICKS, "Cheap building material that automatically breaks after 10 seconds.")
        addInfo(ModBlocks.RETURNING_BLOCK_OF_STICKS, "Works like a Block of Sticks, but teleports itself to the nearest player after breaking.")
        addInfo(ModBlocks.INVENTORY_REROUTER, "Each side can be configured to \"point\" to one side of the block in front.\n\nFor example, setting this block's bottom face to \"U\" will and then inserting items into it will try to insert those items into the top face of the block in front of it.\n\nAlso allows for extracting.")
        addInfo(ModBlocks.SLIME_CUBE, "Causes Slimes to spawn in great numbers in the chunk its in.\n\nWhen powered, it instead prevents Slimes from spawning in the chunk.")
        addInfo(ModBlocks.PEACE_CANDLE, "Prevents natural mob spawns in a 3 chunk radius.\n\nCan be found in roughly one third of Villager Churches.")
        addInfo(ModBlocks.NOTIFICATION_INTERFACE, "Sends a configurable toast notification to the block's owner when a Redstone signal is received.")
        addInfo(ModBlocks.INVENTORY_TESTER, "When placed on an inventory, constantly simulates attempting to insert a configured Item Stack.\n\nIf the simulation would succeed, it emits a Redstone signal. You can invert this behavior.")
        addInfo(ModBlocks.GLOBAL_CHAT_DETECTOR, "Emits a redstone pulse if any player sends a chat message containing the specified text.\n\nCan have a player whitelist using ID Cards.")
        addInfo(ModBlocks.TRIGGER_GLASS, "When given a Redstone pulse, loses its collision for 3 seconds.\n\nAlso triggers adjacent Trigger Glass.")
        addInfo(ModBlocks.BLOCK_DESTABILIZER, "When given a Redstone pulse, scans the blocks in front of it.\n\nIt then gives the nearest 50 connected blocks gravity, making them fall.\n\nYou can set it to \"lazy\" mode, which makes it remember the shape, and only destabilize those blocks.")
        addInfo(ModBlocks.SOUND_BOX, "When given a Redstone pulse, plays the contained Sound Pattern.")
        addInfo(ModBlocks.SOUND_DAMPENER, "Prevents sounds matching held Sound Patterns from being heard, within 10 blocks.")
        addInfo(ModBlocks.SIDED_BLOCK_OF_REDSTONE, "Emits a Redstone signal on only one side.")
        addInfo(ModBlocks.SPECTRE_LENS, "Place on a Beacon, and its effects will be granted to you no matter the distance.\n\nOnly works in the same dimension, and only when the Beacon's chunk is loaded.")
        addInfo(ModBlocks.SPECTRE_ENERGY_INJECTOR, "Acts like an Ender Chest for FE.\n\nFE can be inserted into the Spectre Energy Injector, but Spectre Coils are required to extract it.\n\nA player's Spectre Energy Buffer can store up to 1,000,000 FE by default.")
        addInfo(ModBlocks.SPECTRE_COIL_BASIC, "Outputs FE from the Spectre Energy Buffer, which is filled using Spectre Energy Injectors.\n\nHas a rate of 1,024 FE/t.")
        addInfo(ModBlocks.SPECTRE_COIL_REDSTONE, "Outputs FE from the Spectre Energy Buffer, which is filled using Spectre Energy Injectors.\n\nHas a rate of 4,096 FE/t.")
        addInfo(ModBlocks.SPECTRE_COIL_ENDER, "Outputs FE from the Spectre Energy Buffer, which is filled using Spectre Energy Injectors.\n\nHas a rate of 20,480 FE/t.")
        addInfo(ModBlocks.SPECTRE_COIL_NUMBER, "Generates 128 FE/t for free, inserting it into the block it's placed on.")
        addInfo(ModBlocks.SPECTRE_COIL_GENESIS, "Generates an infinite amount of FE for free, inserting it into the block it's placed on.")
        addInfo(ModBlocks.ADVANCED_REDSTONE_REPEATER, "A Redstone Repeater that can have both its Step Up and Step Down delays configured.")
        addInfo(ModBlocks.ADVANCED_REDSTONE_TORCH, "A Redstone Torch that can have its Powered and Unpowered output strengths configured")
        addInfo(ModBlocks.SPECTRE_SAPLING, "Created by using Ectoplasm on a Sapling.\n\nGrows into a Spectre Tree, whose blocks have a chance of dropping Ectoplasm.")
        addInfo(ModBlocks.ITEM_COLLECTOR, "Collects items in a 3 block radius and inserts it into the inventory it's placed on.")
        addInfo(ModBlocks.ADVANCED_ITEM_COLLECTOR, "Collects items in a configurable radius and inserts it into the inventory it's placed on.\n\nYou can also insert an Item Filter for more control.")
        add(Info.BIOME_BLOCKS, "Changes color to match the biome.")
        addInfo(ModBlocks.RAINBOW_LAMP, "Changes color depending on the Redstone signal strength.\n\nOnly the texture changes, the light stays the same color.")
        addInfo(ModBlocks.BASIC_REDSTONE_INTERFACE, "Powers the linked block with the Redstone signal it's receiving.\n\nUse a Redstone Tool to link it to a block.")
        addInfo(ModBlocks.ADVANCED_REDSTONE_INTERFACE, "Powers up to 9 linked blocks with the Redstone signal it's receiving.\n\nUse a Redstone Tool to link it to blocks.")
        addInfo(ModBlocks.SHOCK_ABSORBER, "Reduces all fall damage, and also emits a redstone signal strength relative to the distance fallen.")
        addInfo(ModBlocks.MOON_PHASE_DETECTOR, "Emits a Redstone signal depending on the current moon phase.")

        addInfo(ModBlocks.PROCESSING_PLATE, "???")
        addInfo(ModBlocks.REDIRECTOR_PLATE, "Has two \"enabled\" sides. Entities that enter from one side are sent to the other side.")
        addInfo(ModBlocks.FILTERED_REDIRECTOR_PLATE, "Has two \"input\" sides, and two color-coded \"output\" sides.\n\nBoth output sides have a slot for an Entity Filter, and any entity that enters from the input sides are teleported to the output with a matching Filter.")
        addInfo(ModBlocks.REDSTONE_PLATE, "Entities moving over this plate are redirected in a configurable direction.\n\nIf powered, they instead move forward.")
        addInfo(ModBlocks.CORRECTOR_PLATE, "Entities moving on the Plate are centered along the axis they're moving.\n\nFor example, if they're moving South, they're teleported to the middle of the North-South axis of the block.")
        addInfo(ModBlocks.ITEM_SEALER_PLATE, "Dropped item entities that pass over this Plate cannot be picked up for 30 seconds.")
        addInfo(ModBlocks.ITEM_REJUVENATOR_PLATE, "Dropped item entities that pass over this Plate have their despawn timer set to 4 minutes.")
        addInfo(ModBlocks.ACCELERATOR_PLATE, "Entities moving on this Plate are sped up slightly, to a limit.")
        addInfo(ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE, "Entities on this Plate are pushed slightly in the direction the Plate is facing.")
        addInfo(ModBlocks.BOUNCY_PLATE, "Entities on this Plate are propelled upwards.")
        addInfo(ModBlocks.COLLECTION_PLATE, "Dropped item entities that pass over this Plate attempt to insert into adjacent inventories.\n\nThe order it tries is: Down, North, South, East, West, Up")
        addInfo(ModBlocks.EXTRACTION_PLATE, "Has an \"input\" side and an \"output\" side. Extracts stacks from the input side, and drops or inserts them on the output side, depending on if there's an inventory there.\n\nRight-click with an empty hand to change the output side, and do so while sneaking to change the input side.")

        addInfo(ModItems.STABLE_ENDER_PEARL, "Use to bind to yourself.\n\nSeven seconds after being dropped, it teleports the bound entity to it. If no entity is bound, it grabs a random entity within 10 blocks instead.")
        addInfo(ModItems.BIOME_CRYSTAL, "Found in dungeon chests, can be inserted into a Biome Radar to locate a biome.")
        addInfo(ModItems.LOCATION_FILTER, "An item representation of a position in the world.\n\nSet by either using it on a block.")
        addInfo(ModItems.SUMMONING_PENDULUM, "Found in dungeon chests, can be used to pick up and place entities.")
        addInfo(ModItems.LESSER_MAGIC_BEAN, "Can be planted on any block, and grows into a bean stalk.\n\nBean Stalk blocks can be climbed faster than Ladders.")
        addInfo(ModItems.MAGIC_BEAN, "Can be planted on any block, and grows a bean stalk that reaches the sky.\n\nThe top of the bean stalk has treasures.")
        addInfo(ModItems.REDSTONE_TOOL, "Used to bind the Redstone Observer or Redstone Interfaces to blocks.\n\nSneak right-click on the Observer/Interface, then click on the block to bind/unbind it.\n\nAlso shows the signal strength of the block you're looking at!")
        addInfo(ModItems.WATER_WALKING_BOOTS, "Allows you to walk on water when not sneaking.")
        addInfo(ModItems.LAVA_CHARM, "Adds a temporary lava shield, visible above your armor bar. This prevents taking damage from Lava, but does NOT prevent you from lighting on fire!\n\nLasts about 10 seconds.")
        addInfo(ModItems.OBSIDIAN_SKULL, "Has a chance of negating fire damage.\n\nThe chance is higher the lower the damage would be.")
        addInfo(ModItems.OBSIDIAN_SKULL_RING, "Works like the Obsidian Skull, but can be worn as a Curio.")
        addInfo(ModItems.OBSIDIAN_WATER_WALKING_BOOTS, "Combines the effects of the Obsidian Skull and Water Walking Boots.")
        addInfo(ModItems.LAVA_WADERS, "Combines the effects of Obsidian Water Walking Boots and a Lava Charm.\n\nAdditionally allows you to stand on lava.")
        addInfo(ModItems.MAGIC_HOOD, "Disables your name plate and potion particles.")
        addInfo(ModItems.FIRE_IMBUE, "Sets entities you hit with melee attacks on fire.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.POISON_IMBUE, "Inflicts Poison II on entities you hit with melee attacks.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.EXPERIENCE_IMBUE, "Increases experience dropped by entities by 50%%.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.WITHER_IMBUE, "Inflicts Wither II on entities you hit with melee attacks.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.SPECTRE_IMBUE, "Gives you a configurable chance to fully negate damage.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.COLLAPSE_IMBUE, "Inverts your controls.\n\nWhy would you want this?")
        addInfo(ModItems.BOTTLE_OF_AIR, "Refills your breath.\n\nCan be found in Ocean Monuments.")
        addInfo(ModItems.ENDER_LETTER, "Can be used to send items and messages to other players.\n\nType the name of the player at the top, and insert up to 9 items.\n\nClick an Ender Mailbox, and it will be inserted into their Ender Mailbox if it has room.")
        addInfo(ModItems.ENTITY_FILTER, "Allows you to filter by entity type. Do so by using it on an entity.")
        addInfo(ModBlocks.SAKANADE_SPORES, "Found on the bottom of huge brown mushrooms.")
        addInfo(ModItems.ECTOPLASM, "Every time an entity dies, it has a chance of spawning a Spirit.\n\nSpirits can only be killed by magic, and they drop Ectoplasm.\n\nCan also be harvested from Spectre Trees.")
        addInfo(ModItems.LUMINOUS_POWDER, "Can be crafted onto enchanted items to make their glints glow in the dark.")
        addInfo(ModItems.LOTUS_BLOSSOM, "Grants experience when consumed.\n\nSneak to consume the entire stack.")
        addInfo(ModItems.GOLDEN_EGG, "Spawns a Chicken that lays Gold Ingots rather than Eggs.")
        addInfo(ModItems.BLACKOUT_POWDER, "Removes the Spectre Illuminator in the chunk it's used in.")
        addInfo(ModItems.ITEM_FILTER, "Allows you to filter Item Stacks. Do so in its GUI.")
        addInfo(ModItems.REDSTONE_ACTIVATOR, "Using it on a block will give it a temporary Redstone signal.\n\nUse it on the air to change the duration.")
        addInfo(ModItems.REDSTONE_REMOTE, "Allows you to temporarily power one of nine blocks from a distance.\n\nSneak right-click it to open its GUI, where you can place up to 9 Location Filters. Under each you can set an item to represent that position.\n\nRight-click normally to see all the positions it can power.")
        addInfo(ModItems.BLAZE_AND_STEEL, "Lights a much more aggressive Fire.")
        addInfo(ModItems.FLOO_SIGN, "?????????????????????")
        addInfo(ModItems.PORTKEY, "Use on a block to set a destination, then throw it on the ground.\n\nAfter 5 seconds, anyone who picks it up will be teleported to the destination.\n\nYou can craft it with any other item to disguise it!")
        addInfo(ModItems.ID_CARD, "Acts like an Entity Filter, but specifically for the player set.\n\nSet by using it in your hand.")
        addInfo(ModItems.EMERALD_COMPASS, "Points to the set player, if they are online.\n\nSet the player by crafting with an ID Card.")
        addInfo(ModItems.SOUND_PATTERN, "Represents a sound.\n\nSet with a Sound Recorder.")
        addInfo(ModItems.SOUND_RECORDER, "Lets you save a sound to a stored Sound Pattern.\n\nUse it to toggle recording; it will stop automatically once it reaches 10 unique sounds.\n\nSneak right-click again to choose which sound to save to the Pattern.")
        addInfo(ModItems.PORTABLE_SOUND_DAMPENER, "Prevents sounds matching held Sound Patterns from being heard by the player holding it.")
        addInfo(ModItems.SUPER_LUBRICANT_TINCTURE, "Can be applied to any Boots to make you slide when wearing them.\n\nCan be washed with a Water Bottle after.")
        addInfo(ModItems.DIVINING_ROD, "When held in hand, highlights the corresponding ore if it's within 20 blocks of you.\n\nThis radius can be configured.")
        addInfo(ModItems.LOTUS_SEEDS, "Can be planted to grow Lotus Blossoms.\n\nLotus plants can be found in cold biomes.")
        addInfo(ModItems.ESCAPE_ROPE, "Hold right-click to begin searching for the nearest location that can see the sky.\n\nIf it finds one, it'll teleport you there.")
        addInfo(ModItems.WEATHER_EGG_SUNNY, "When thrown, spawns a cloud that changes the weather to clear.")
        addInfo(ModItems.WEATHER_EGG_RAINY, "When thrown, spawns a cloud that changes the weather to rain.")
        addInfo(ModItems.WEATHER_EGG_STORMY, "When thrown, spawns a cloud that changes the weather to a thunderstorm.")
        addInfo(ModItems.ENDER_BUCKET, "When used on a non-source fluid, it picks up the nearest source.")
        addInfo(ModItems.REINFORCED_ENDER_BUCKET, "Works like an Ender Bucket, but can hold 10 sources.\n\nIf sneaking, it will pick up as many sources as it can.")
        addInfo(ModItems.CHUNK_ANALYZER, "When used, shows you all the blocks and entities in the chunk you're in.")
        addInfo(ModItems.FLOO_POUCH, "???")
        addInfo(ModItems.SPECTRE_ILLUMINATOR, "When used, spawns a Spectre Illuminator that lights up the entire chunk.\n\nCan be removed either by clicking the Illuminator, or using Blackout Powder in the chunk.")
        add(Info.SPECTRE_CHARGERS, "Charge items in your inventory using energy from your Spectre Energy Buffer.")
        addInfo(ModItems.SPECTRE_CHARGER_GENESIS, "Charges items in your inventory with an infinite amount of energy.\n\nCreative only!")
        addInfo(ModItems.GRASS_SEEDS, "Can be planted on Dirt to grow Grass.")
        addInfo(ModItems.SPECTRE_KEY, "Use for 5 seconds to teleport to a private dimension.\n\nDo the same there to return.")
        addInfo(ModItems.SPECTRE_ANCHOR, "When crafted with an item, the item is kept after death.")
        addInfo(ModItems.SPECTRE_SWORD, "Comparable to a Diamond Sword with higher durability and enchantability.\n\nIncreases the entity reach range of the player by 3 blocks, and can be used to kill Spirits.")
        addInfo(ModItems.SPECTRE_PICKAXE, "Comparable to a Diamond Pickaxe with higher durability and enchantability.\n\nIncreases the block reach of the player by 3 blocks.")
        addInfo(ModItems.SPECTRE_AXE, "Comparable to a Diamond Axe with higher durability and enchantability.\n\nIncreases the block reach of the player by 3 blocks.")
        addInfo(ModItems.SPECTRE_SHOVEL, "Comparable to a Diamond Shovel with higher durability and enchantability.\n\nIncreases the block reach of the player by 3 blocks.")
        add(Info.SPECTRE_ARMOR, "Comparable to Diamond Armor with higher durability and enchantability.\n\nAlso makes you slightly transparent!")
        addInfo(ModItems.BIOME_CAPSULE, "Throw on the ground, and it will capture the biome it lands in.\n\nUsed in conjunction with the Biome Painter.")
        addInfo(ModItems.BIOME_PAINTER, "Use it on a block, and it will paint the biome of the first Biome Capsule in your inventory.\n\nThe radius can be configured.")
        addInfo(ModItems.VOID_STONE, "In its menu, has a slot that immediately deletes whatever is inserted.")
        addInfo(ModItems.DROP_FILTER, "Filters out items that match the contained Item Filters from entering your inventory.")
        addInfo(ModItems.VOIDING_DROP_FILTER, "Works like the Drop Filter, but deletes the items instead of preventing them from entering your inventory.")
        addInfo(ModItems.WHITE_STONE, "Charges when held while under the fool moon. When fully charged, it will prevent your death once and discharges.\n\nCan be found in dungeon loot.")
        addInfo(ModItems.MAGNETIC_FORCE, "Single-use item that allows you to teleport to any player, if they accept, in a radius around you.\n\nThe radius is configurable.")
        addInfo(ModItems.BLOCK_MOVER, "Use on a block to pick it up, and use it again to place it.\n\nCan be used on blocks with Block Entities, like Chests and machines.")
        addInfo(ModItems.DIAMOND_BREAKER, "Apply to a Block Breaker to increase its mining level to equivalent to a Diamond Pickaxe.\n\nCan also be enchanted!")
        addInfo(ModItems.BLOCK_REPLACER, "Stores up to 9 stacks of blocks. When used on a block, it gets replaced with a random stored block.\n\nSneak right-click to open its inventory.")
        addInfo(ModItems.PORTABLE_ENDER_BRIDGE, "Use while looking at an Ender Anchor to teleport to it.\n\nHas a configurable range, defaulting to 300. Requires direct line of sight.")
        addInfo(ModItems.DIAPHANOUS_BLOCK, "Looks like another block, but becomes transparent as you approach.\n\nCraft it with any block to set its appearance.\n\nCraft it by itself to invert it, so it starts transparent and becomes opaque.")
        addInfo(ModItems.EVIL_TEAR, "Can be used on a specific structure to make an Artificial End Portal.\n\nPlace an End Stone, and place an End Rod under it.\n\n4 blocks below it, build a 3x3 of End Stone, with a 5x5 ring of Obsidian one block above it.\n\nUse the Evil Tear on the End Rod to complete the structure.")
        addInfo(ModItems.CUSTOM_CRAFTING_TABLE, "Works exactly like a vanilla Crafting Table, except it looks like the block it's made of.\n\nThis uses the item tag #irregular_implements:custom_crafting_table_items")
    }

    private fun addEffects() {
        addEffect(ModEffects.FIRE_IMBUE, "Fire Imbue")
        addEffect(ModEffects.POISON_IMBUE, "Poison Imbue")
        addEffect(ModEffects.EXPERIENCE_IMBUE, "Experience Imbue")
        addEffect(ModEffects.WITHER_IMBUE, "Wither Imbue")
        addEffect(ModEffects.COLLAPSE_IMBUE, "Collapse Imbue")
        addEffect(ModEffects.SPECTRE_IMBUE, "Spectre Imbue")
    }

}