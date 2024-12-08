package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.registry.ModEffects
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.data.LanguageProvider

class ModLanguageProvider(
    output: PackOutput
) : LanguageProvider(output, IrregularImplements.ID, "en_us") {

    companion object {
        fun String.toComponent(vararg args: Any?): MutableComponent = Component.translatable(this, *args)

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
    }

    object Subtitles {
        const val FART = "subtitle.irregular_implements.fart"
    }

    private fun addSubtitles() {
        add(Subtitles.FART, "Pbbbt")
    }

    object Items {
        const val CREATIVE_TAB = "itemGroup.irregular_implements"

        const val ENTITY_FILTER_UNSET = "item.irregular_implements.entity_filter.unset"
        const val ENTITY_FILTER_SET = "item.irregular_implements.entity_filter.set"

        const val PLAYER_FILTER_UNSET = "item.irregular_implements.player_filter.unset"
        const val PLAYER_FILTER_SET = "item.irregular_implements.player_filter.set"
    }

    private fun addItems() {

        add(Items.ENTITY_FILTER_UNSET, "Entity Filter")
        add(Items.ENTITY_FILTER_SET, "Entity Filter (%s)")

        add(Items.PLAYER_FILTER_UNSET, "ID Card")
        add(Items.PLAYER_FILTER_SET, "ID Card (%s)")

        add(Items.CREATIVE_TAB, "Irregular Implements")

        // Regular items

        addItem(ModItems.STABLE_ENDER_PEARL, "Stable Ender Pearl")
        addItem(ModItems.BIOME_CRYSTAL, "Biome Crystal")
        addItem(ModItems.LOCATION_FILTER, "Location Filter")
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
        addItem(ModItems.SAKANADE_SPORES, "Sakande Spores")
        addItem(ModItems.EVIL_TEAR, "Evil Tear")
        addItem(ModItems.ECTOPLASM, "Ectoplasm")
        addItem(ModItems.BIOME_SENSOR, "Biome Sensor")
        addItem(ModItems.LUMINOUS_POWDER, "Luminous Powder")
        addItem(ModItems.PLATE_BASE, "Plate Base")
        addItem(ModItems.PRECIOUS_EMERALD, "Precious Emerald")
        addItem(ModItems.LOTUS_BLOSSOM, "Lotus Blossom")
        addItem(ModItems.GOLDEN_EGG, "Golden Egg")
        addItem(ModItems.BLACKOUT_POWDER, "Blackout Powder")
        addItem(ModItems.ITEM_FILTER, "Item Filter")
        addItem(ModItems.EMERALD_COMPASS, "Emerald Compass")
        addItem(ModItems.BLAZE_AND_STEEL, "Blaze and Steel")
        addItem(ModItems.RUNE_PATTERN, "Rune Pattern")
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

        addItem(ModItems.FIRE_IMBUE, "Fire Imbue")
        addItem(ModItems.POISON_IMBUE, "Poison Imbue")
        addItem(ModItems.EXPERIENCE_IMBUE, "Experience Imbue")
        addItem(ModItems.WITHER_IMBUE, "Wither Imbue")
        addItem(ModItems.COLLAPSE_IMBUE, "Collapse Imbue")

        addItem(ModItems.SPECTRE_INGOT, "Spectre Ingot")
        addItem(ModItems.SPECTRE_STRING, "Spectre String")
        addItem(ModItems.SPECTRE_ILLUMINATOR, "Spectre Illuminator")
        addItem(ModItems.SPECTRE_KEY, "Spectre Key")
        addItem(ModItems.SPECTRE_ANCHOR, "Spectre Anchor")
        addItem(ModItems.SPECTRE_SWORD, "Spectre Sword")
        addItem(ModItems.SPECTRE_PICKAXE, "Spectre Pickaxe")
        addItem(ModItems.SPECTRE_AXE, "Spectre Axe")
        addItem(ModItems.SPECTRE_SHOVEL, "Spectre Shovel")
        addItem(ModItems.SPECTRE_CHARGER, "Spectre Charger")

        addItem(ModItems.ADVANCED_REDSTONE_REPEATER, "Advanced Redstone Repeater")
        addItem(ModItems.ADVANCED_REDSTONE_TORCH, "Advanced Redstone Torch")
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

        addItem(ModItems.RUNE_DUST_WHITE, "White Rune Dust")
        addItem(ModItems.RUNE_DUST_ORANGE, "Orange Rune Dust")
        addItem(ModItems.RUNE_DUST_MAGENTA, "Magenta Rune Dust")
        addItem(ModItems.RUNE_DUST_LIGHT_BLUE, "Light Blue Rune Dust")
        addItem(ModItems.RUNE_DUST_YELLOW, "Yellow Rune Dust")
        addItem(ModItems.RUNE_DUST_LIME, "Lime Rune Dust")
        addItem(ModItems.RUNE_DUST_PINK, "Pink Rune Dust")
        addItem(ModItems.RUNE_DUST_GRAY, "Gray Rune Dust")
        addItem(ModItems.RUNE_DUST_LIGHT_GRAY, "Light Gray Rune Dust")
        addItem(ModItems.RUNE_DUST_CYAN, "Cyan Rune Dust")
        addItem(ModItems.RUNE_DUST_PURPLE, "Purple Rune Dust")
        addItem(ModItems.RUNE_DUST_BLUE, "Blue Rune Dust")
        addItem(ModItems.RUNE_DUST_BROWN, "Brown Rune Dust")
        addItem(ModItems.RUNE_DUST_GREEN, "Green Rune Dust")
        addItem(ModItems.RUNE_DUST_RED, "Red Rune Dust")
        addItem(ModItems.RUNE_DUST_BLACK, "Black Rune Dust")
    }

    private fun addBlocks() {
        addBlock(ModBlocks.FERTILIZED_DIRT, "Fertilized Dirt")
        addBlock(ModBlocks.LAPIS_GLASS, "Lapis Glass")
        addBlock(ModBlocks.LAPIS_LAMP, "Lapis Lamp")
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
        addBlock(ModBlocks.QUARTZ_LAMP, "Quartz Lamp")
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
        addBlock(ModBlocks.SPECTRE_COIL, "Spectre Coil")
        addBlock(ModBlocks.SPECTRE_COIL_REDSTONE, "Spectre Coil Redstone")
        addBlock(ModBlocks.SPECTRE_COIL_ENDER, "Spectre Coil Ender")
        addBlock(ModBlocks.SPECTRE_COIL_NUMBER, "Spectre Coil Number")
        addBlock(ModBlocks.SPECTRE_COIL_GENESIS, "Spectre Coil Genesis")
        addBlock(ModBlocks.SPECTRE_PLANKS, "Spectre Planks")
        addBlock(ModBlocks.SPECTRE_SAPLING, "Spectre Sapling")
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
        addBlock(ModBlocks.ITEM_REJUVINATOR_PLATE, "Item Rejuvinator Plate")
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

        const val ENTITY_FILTER_CONTROLS = "tooltip.irregular_implements.entity_filter_controls"
        const val PLAYER_FILTER_CONTROLS = "tooltip.irregular_implements.player_filter_controls"

        const val COMPRESSED_SLIME_AMOUNT = "tooltip.irregular_implements.compressed_slime_AMOUNT"
    }

    private fun addTooltips() {
        add(Tooltips.SHIFT_FOR_MORE, "Hold [Shift] for more information")

        add(Tooltips.ENTITY_FILTER_CONTROLS, "Set entity type by clicking an entity,\nor sneak click to set as Player")
        add(Tooltips.PLAYER_FILTER_CONTROLS, "Set player by clicking")

        add(Tooltips.COMPRESSED_SLIME_AMOUNT, "Compression level %d")
    }

    object Info {
        const val PLATFORM = "info.irregular_implements.platform"
        const val BIOME_BLOCKS = "info.irregular_implements.biome_blocks"
    }

    private fun addInfo(itemLike: ItemLike, infoString: String) {
        add(getInfoString(itemLike), infoString)
    }

    private fun addInfo() {
        addInfo(ModBlocks.FERTILIZED_DIRT, "Fertilized Dirt does not require hydration, grows crops 3 times faster, and can't be trampled.\n\nYou still have to till it with a Hoe.")
        addInfo(ModBlocks.PLAYER_INTERFACE, "Exposes the inventory of the block's owner, as if it was the block's inventory.")
        addInfo(ModBlocks.LAPIS_GLASS, "Solid for players, not solid for anything else.")
        addInfo(ModBlocks.LAPIS_LAMP, "Provides false light, which changes visibility but does not affect mob spawning.")
        addInfo(ModBlocks.ONLINE_DETECTOR, "Emits a Redstone signal if the specified player is online.")
        addInfo(ModBlocks.CHAT_DETECTOR, "Emits a Redstone pulse if the block's owner sends a chat message containing some specified text.")
        addInfo(ModBlocks.ENDER_BRIDGE, "Upon the Ender Bridge receiving a Redstone Signal, it looks for an Ender Anchor in front of it.\n\nIt searches at 20 blocks per second, and then teleports the player standing on top to it.\n\nThere's no distance limit, though there can be no blocks between (save for the block directly in front of the Bridge).")
        addInfo(ModBlocks.PRISMARINE_ENDER_BRIDGE, "Upon the Prismarine Ender Bridge receiving a Redstone Signal, it looks for an Ender Anchor in front of it (with no distance limit, though it must be loaded, and no other blocks can be in the way).\n\nIt searches at 200 blocks per second, and then teleports the player standing on top to it.\n\nThere's no distance limit, though there can be no blocks between (save for the block directly in front of the Bridge).")
        addInfo(ModBlocks.ENDER_ANCHOR, "Works with the Ender Bridge or Prismarine Ender Bridge.")
        addInfo(ModBlocks.BEAN_POD, "Found at the top of a grown Magic Bean.\n\nContains loot!")
        addInfo(ModBlocks.SPECTRE_BLOCK, "An indestructible block that spawns in the Spectre Dimension.")
        addInfo(ModBlocks.ANALOG_EMITTER, "Emits a configurable Redstone signal.")
        addInfo(ModBlocks.FLUID_DISPLAY, "A solid block that uses the texture of a fluid.\n\nSet the fluid by clicking it with a filled Bucket or fluid container.\n\nRight-click to toggle between still and flowing, and sneak right-click to rotate it.")
        addInfo(ModBlocks.ENDER_MAILBOX, "Allows players to send and receive Ender Letters.")
        addInfo(ModBlocks.PITCHER_PLANT, "Acts as an infinite water source.\n\nSlowly fills adjacent fluid containers.")
        add(Info.PLATFORM, "Only solid to non-sneaking entities.")
        addInfo(ModBlocks.ENTITY_DETECTOR, "Emits a Redstone signal if a configured entity is within a configured range.")
        addInfo(ModBlocks.QUARTZ_LAMP, "Provides invisible light, which can't be seen but affects mob spawning.")   //TODO: Make some joke about radiation
        addInfo(ModBlocks.QUARTZ_GLASS, "Solid for everything but players.")
        addInfo(ModBlocks.POTION_VAPORIZER, "Allows you to fill a room with a potion effect.\n\nInsert Potions and Furnace fuel, and it will scan the area in front of it to check if it's an enclosed area.\n\nIf it is, it'll fill it with the potion effect.")
        addInfo(ModBlocks.CONTACT_LEVER, "Acts like a Lever, but if the block in front of it is clicked instead.")
        addInfo(ModBlocks.CONTACT_BUTTON, "Acts like a Button, but if the block in front of it is clicked instead.")
        addInfo(ModBlocks.RAIN_SHIELD, "Prevents rain in a 5 chunk radius.")
        addInfo(ModBlocks.BLOCK_BREAKER, "Breaks blocks in front of it with the effectiveness of an Iron Pickaxe. Drops are inserted into the inventory behind it, or dropped.\n\nCan be disabled with a Redstone signal.")
        addInfo(ModBlocks.SUPER_LUBRICANT_ICE, "No friction.")
        addInfo(ModBlocks.SUPER_LUBRICANT_STONE, "No friction.")
        addInfo(ModBlocks.COMPRESSED_SLIME_BLOCK, "Create by clicking a Slime Block with a Shovel. Can be compressed multiple times.\n\nLaunches entities into the air.")
        addInfo(ModBlocks.REDSTONE_OBSERVER, "Emits the same Redstone signal as a linked block.\n\nUse a Redstone Tool to link it to a block.")
        addInfo(ModBlocks.BIOME_RADAR, "Insert a Biome Crystal and provide a Redstone signal, and it will attempt to find the Crystal's biome.\n\nIf it succeeds, it will spawn particles aiming in its direction.\n\nUsing a Paper on it will create a Location Filter for the biome.")
        addInfo(ModBlocks.IRON_DROPPER, "An upgraded Dropper, where the following can be configured:\n\n• Pickup Delay\n• Item motion randomness\n• Particle/Sound toggle\n• Redstone control")
        addInfo(ModBlocks.IGNITER, "Sets or extinguishes the block on front of it, depending on the Redstone signal.\n\nCan be set to keep the fire lit.")
        addInfo(ModBlocks.BLOCK_OF_STICKS, "Cheap building material that automatically breaks after 10 seconds.")
        addInfo(ModBlocks.RETURNING_BLOCK_OF_STICKS, "Works like a Block of Sticks, but the item drop is teleported to the nearest player.")
        addInfo(ModBlocks.INVENTORY_REROUTER, "??????????")
        addInfo(ModBlocks.SLIME_CUBE, "Causes Slimes to spawn in great numbers in the chunk its in.\n\nWhen powered, it instead prevents Slimes from spawning in the chunk.")
        addInfo(ModBlocks.PEACE_CANDLE, "Prevents natural mob spawns in a 3 chunk radius.\n\nCan be found in roughly one third of Villager Churches.")
        addInfo(ModBlocks.NOTIFICATION_INTERFACE, "Sends a configurable toast notification to the block's owner when a Redstone signal is received.")
        addInfo(ModBlocks.INVENTORY_TESTER, "When placed on an inventory, constantly simulates attempting to insert a configured Item Stack.\n\nIf the simulation would succeed, emits a Redstone signal.")
        addInfo(ModBlocks.GLOBAL_CHAT_DETECTOR, "Emits a redstone pulse if any player sends a chat message containing the specified text.\n\nCan have a player whitelist using ID Cards.")
        addInfo(ModBlocks.TRIGGER_GLASS, "When given a Redstone pulse, loses its collision for 3 seconds.\n\nAlso triggers adjacent Trigger Glass.")
        addInfo(ModBlocks.BLOCK_DESTABILIZER, "When given a Redstone pulse, scans the blocks in front of it.\n\nIt then gives the nearest 50 connected blocks gravity, making them fall.\n\nYou can set it to \"lazy\" mode, which makes it remember the shape, and only destabilize those blocks.")
        addInfo(ModBlocks.SOUND_BOX, "When given a Redstone pulse, plays the held Sound Pattern.")
        addInfo(ModBlocks.SOUND_DAMPENER, "Prevents sounds matching held Sound Patterns from being heard, within 10 blocks.")
        addInfo(ModBlocks.SIDED_BLOCK_OF_REDSTONE, "Emits a Redstone signal on only one side.")
        addInfo(ModBlocks.SPECTRE_LENS, "Place on a Beacon, and its effects will be granted to you no matter the distance.\n\nStill requires you be in the same dimension, and you can only have one Lens per dimension.")
        addInfo(ModBlocks.SPECTRE_ENERGY_INJECTOR, "Acts like an Ender Chest for FE.\n\nThe Energy Injector can insert FE, Spectre Coils are required to extract it.\n\nA player's Spectre Energy Buffer can store up to 1,000,000 FE by default.")
        addInfo(ModBlocks.SPECTRE_COIL, "Outputs FE from the Spectre Energy Buffer, which is filled using Spectre Energy Injectors.\n\nHas a rate of 1,024 FE/t.")
        addInfo(ModBlocks.SPECTRE_COIL_REDSTONE, "Outputs FE from the Spectre Energy Buffer, which is filled using Spectre Energy Injectors.\n\nHas a rate of 4,096 FE/t.")
        addInfo(ModBlocks.SPECTRE_COIL_ENDER, "Outputs FE from the Spectre Energy Buffer, which is filled using Spectre Energy Injectors.\n\nHas a rate of 20,480 FE/t.")
        addInfo(ModBlocks.SPECTRE_COIL_NUMBER, "Generates 128 FE/t for free, inserting it into the block it's placed on.")
        addInfo(ModBlocks.SPECTRE_COIL_GENESIS, "Once every 10 seconds, tries to insert an infinite amount of FE into the block it's placed on.")
        addInfo(ModItems.ADVANCED_REDSTONE_REPEATER, "A Redstone Repeater that can have both its Step Up and Step Down delays configured.")
        addInfo(ModItems.ADVANCED_REDSTONE_TORCH, "A Redstone Torch that can have its Powered and Unpowered output strengths configured")
        addInfo(ModBlocks.SPECTRE_SAPLING, "Created by using Ectoplasm on a Sapling.\n\nGrows into a Spectre Tree, whose blocks have a chance of dropping Ectoplasm.")
        addInfo(ModBlocks.ITEM_COLLECTOR, "Collects items in a 3 block radius and inserts it into the inventory it's placed on.")
        addInfo(ModBlocks.ADVANCED_ITEM_COLLECTOR, "Collects items in a configurable radius and inserts it into the inventory it's placed on.\n\nYou can also insert an Item Filter for more control.")
        add(Info.BIOME_BLOCKS, "Changes color to match the biome.")
        addInfo(ModBlocks.RAINBOW_LAMP, "Changes color depending on the Redstone signal strength.\n\nOnly the texture changes, the light stays the same color.")
        addInfo(ModBlocks.BASIC_REDSTONE_INTERFACE, "Powers the linked block with the Redstone signal it's receiving.\n\nUse a Redstone Tool to link it to a block.")
        addInfo(ModBlocks.ADVANCED_REDSTONE_INTERFACE, "Powers up to 9 linked blocks with the Redstone signal it's receiving.\n\nUse a Redstone Tool to link it to blocks.")
        addInfo(ModBlocks.REDIRECTOR_PLATE, "Has two \"enabled\" sides. Entities that enter from one side are sent to the other side.")
        addInfo(ModBlocks.FILTERED_REDIRECTOR_PLATE, "Has two \"input\" sides, and two color-coded \"output\" sides.\n\nBoth output sides have a slot for an Entity Filter, and any entity that enters from the input sides are teleported to the output with a matching Filter.")
        addInfo(ModBlocks.REDSTONE_PLATE, "Has an \"input\" side, a \"default\" side, and a \"powered\" side.\n\nEntities that entier from the input side are teleported to the default side if the Plate is unpowered, or the powered side if it is.")
        addInfo(ModBlocks.CORRECTOR_PLATE, "Entities moving on the Plate are centered along the axis they're moving.\n\nFor example, if they're moving South, they're teleported to the middle of the North-South axis of the block.")
        addInfo(ModBlocks.ITEM_SEALER_PLATE, "Dropped item entities that pass over this Plate have their pickup delay set to 30 seconds.")
        addInfo(ModBlocks.ITEM_REJUVINATOR_PLATE, "Dropped item entities that pass over this Plate have their despawn timer set to 4 minutes.")
        addInfo(ModBlocks.ACCELERATOR_PLATE, "Entities moving on this Plate are sped up slightly, to a limit.")
        addInfo(ModBlocks.DIRECTIONAL_ACCELERATOR_PLATE, "Entities moving on this Plate are sped up slightly, to a limit, in the direction the Plate is facing.")
        addInfo(ModBlocks.BOUNCY_PLATE, "Entities on this Plate are propelled upwards.")
        addInfo(ModBlocks.COLLECTION_PLATE, "Dropped item entities that pass over this Plate attempt to insert into adjacent inventories.")
        addInfo(ModBlocks.EXTRACTION_PLATE, "Has an \"input\" side and an \"output\" side. Extracts stacks from the input side, and drops or inserts them on the output side, depending on if there's an inventory there.\n\nRight-click with an empty hand to change the output side, and do so while sneaking to change the input side.")

        addInfo(ModItems.STABLE_ENDER_PEARL, "Use to bind to yourself.\n\nSeven seconds after being dropped, it teleports the bound entity to it. If no entity is bound, it grabs a random entity within 10 blocks instead.")
        addInfo(ModItems.BIOME_CRYSTAL, "Used in the Biome Radar.")
        addInfo(ModItems.LOCATION_FILTER, "An item representation of a position in the world.\n\nSet by either using it on a block, an entity, or empty space.")
        addInfo(ModItems.LESSER_MAGIC_BEAN, "Can be planted on any block, and grows into a bean stalk.\n\nBean Stalk blocks can be climbed faster than Ladders.")
        addInfo(ModItems.MAGIC_BEAN, "Can be planted on any block, and grows a bean stalk that reaches the sky.\n\nThe top of the bean stalk has treasures.")
        addInfo(ModItems.REDSTONE_TOOL, "Used to bind the Redstone Observer or Redstone Interfaces to blocks.\n\nSneak right-click on the Observer/Interface, then click on the block to bind/unbind it.\n\nAlso shows the signal strength of the block you're looking at!")
        addInfo(ModItems.WATER_WALKING_BOOTS, "Allows you to walk on water when not sneaking.")
        addInfo(ModItems.LAVA_CHARM, "Adds a temporary lava shield, visible above your armor bar.")
        addInfo(ModItems.OBSIDIAN_SKULL, "Has a chance of negating damage.\n\nOn success, it lowers durability. At lower durability, it has a lower chance.")
        addInfo(ModItems.OBSIDIAN_SKULL_RING, "Works like the Obsidian Skull, but can be worn as a Curio.")
        addInfo(ModItems.MAGIC_HOOD, "Disables your name plate and potion particles.")
        addInfo(ModItems.FIRE_IMBUE, "Sets entities you hit with melee attacks on fire.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.POISON_IMBUE, "Inflicts Poison II on entities you hit with melee attacks.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.EXPERIENCE_IMBUE, "Increases experience dropped by entities by 50%%.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.WITHER_IMBUE, "Inflicts Wither II on entities you hit with melee attacks.\n\nLasts 20 minutes, but you can only have one Imbuement at a time.")
        addInfo(ModItems.BOTTLE_OF_AIR, "Refills your breath.")
        addInfo(ModItems.ENDER_LETTER, "Can be used to send items and messages to other players.\n\nType the name of the player at the top, and insert up to 9 items.\n\nClick an Ender Mailbox, and it will be inserted into their Ender Mailbox if it has room.")
        addInfo(ModItems.ENTITY_FILTER, "Allows you to filter by entity type. Do so by using it on an entity.")
        addInfo(ModItems.SAKANADE_SPORES, "Found on the bottom of huge brown mushrooms.")
        addInfo(ModItems.ECTOPLASM, "Dropped by Spirits, which have a small chance of spawning when any entity dies.\n\nMagic damage drops more.\n\nAlso drops from Spectre Trees.")
        addInfo(ModItems.LUMINOUS_POWDER, "Can be crafted onto enchanted items to make their glints glow in the dark.")
        addInfo(ModItems.LOTUS_BLOSSOM, "Grants experience when consumed.\n\nSneak to consume the entire stack.")
        addInfo(ModItems.GOLDEN_EGG, "Spawns a Chicken that lays Gold Ingots rather than Eggs.")
        addInfo(ModItems.BLACKOUT_POWDER, "Removes the Spectre Illuminator in the chunk it's used in.")
        addInfo(ModItems.ITEM_FILTER, "Allows you to filter Item Stacks. Do so in its GUI.")
        addInfo(ModItems.REDSTONE_ACTIVATOR, "Using it on a block will give it a temporary Redstone signal.\n\nUse it on the air to change the duration.")
        addInfo(ModItems.REDSTONE_REMOTE, "Allows you to temporarily power one of nine blocks from a distance.\n\nSneak right-click it to open its GUI, where you can place up to 9 Location Filters. Under each you can set an item to represent that position.\n\nRight-click normally to see all the positions it can power.")
        addInfo(ModItems.BLAZE_AND_STEEL, "Lights a much more aggressive Fire.")
        addInfo(ModItems.RUNE_PATTERN, "Allows you to quickly recreate Runic Dust patterns.\n\nUse it on a placed Runic Dust pattern to save it, then use it on another block to place a copy of it.")
        addInfo(ModItems.FLOO_SIGN, "?????????????????????")
        addInfo(ModItems.ID_CARD, "Acts like an Entity Filter, but specifically for the player set.\n\nSet by using it in your hand.")
        addInfo(ModItems.EMERALD_COMPASS, "When combined with an ID Card, points to the player.")
        addInfo(ModItems.SOUND_PATTERN, "Represents a sound.\n\nSet with a Sound Recorder.")
        addInfo(ModItems.SOUND_RECORDER, "Lets you save a sound to a stored Sound Pattern.\n\nUse it to toggle recording; it will stop automatically once it reaches 10 unique sounds.\n\nSneak right-click again to choose which sound to save to the Pattern.")
        addInfo(ModItems.PORTABLE_SOUND_DAMPENER, "Prevents sounds matching held Sound Patterns from being heard by the player holding it.")
        addInfo(ModItems.SUPER_LUBRICANT_TINCTURE, "Can be applied to any Boots to make you slide when wearing them.\n\nCan be washed with a Water Bottle after.")
        //TODO: The others
    }

    private fun addEffects() {
        addEffect(ModEffects.FIRE_IMBUE, "Fire Imbue")
        addEffect(ModEffects.POISON_IMBUE, "Poison Imbue")
        addEffect(ModEffects.EXPERIENCE_IMBUE, "Experience Imbue")
        addEffect(ModEffects.WITHER_IMBUE, "Wither Imbue")
        addEffect(ModEffects.COLLAPSE_IMBUE, "Collapse Imbue")
    }

}