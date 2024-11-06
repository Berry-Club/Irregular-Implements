package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.*
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ModItems {

    val ITEM_REGISTRY: DeferredRegister.Items =
        DeferredRegister.createItems(IrregularImplements.ID)

    val STABLE_ENDER_PEARL: DeferredItem<StableEnderPearlItem> =
        register("stable_ender_pearl") { StableEnderPearlItem() }
    val BIOME_CRYSTAL: DeferredItem<BiomeCrystalItem> =
        register("biome_crystal") { BiomeCrystalItem() }
    val SUMMONING_PENDULUM = basic("summoning_pendulum")
    val BEAN = basic("bean")
    val LESSER_MAGIC_BEAN = basic("lesser_magic_bean")
    val MAGIC_BEAN = basic("magic_bean")
    val BEAN_STEW = basic("bean_stew")
    val WATER_WALKING_BOOTS = basic("water_walking_boots")
    val LOOT_GENERATOR = basic("loot_generator")
    val LAVA_CHARM = basic("lava_charm")
    val LAVA_WADERS = basic("lava_waders")
    val OBSIDIAN_SKULL = basic("obsidian_skull")
    val OBSIDIAN_SKULL_RING = basic("obsidian_skull_ring")      //Maybe you could just put the regular skull in the slot?
    val OBSIDIAN_WATER_WALKING_BOOTS = basic("obsidian_water_walking_boots")
    val MAGIC_HOOD: DeferredItem<ArmorItem> =
        register("magic_hood") {
            ArmorItem(
                ArmorMaterials.CHAIN,   //TODO: Something that allows a custom texture
                ArmorItem.Type.HELMET,
                Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
            )
        }
    val BOTTLE_OF_AIR: DeferredItem<AirBottleItem> =
        register("bottle_of_air") { AirBottleItem() }
    val ENDER_LETTER = basic("ender_letter")
    val SAKANADE_SPORES = basic("sakanade_spores")
    val EVIL_TEAR = basic("evil_tear")
    val ECTOPLASM = basic("ectoplasm")
    val BIOME_SENSOR = basic("biome_sensor")
    val LUMINOUS_POWDER = basic("luminous_powder")
    val PLATE_BASE = basic("plate_base")
    val PRECIOUS_EMERALD = basic("precious_emerald")
    val LOTUS_BLOSSOM = basic("lotus_blossom")
    val GOLDEN_EGG = basic("golden_egg")
    val BLACKOUT_POWDER = basic("blackout_powder")
    val EMERALD_COMPASS = basic("emerald_compass")
    val BLAZE_AND_STEEL: DeferredItem<BlazeAndSteelItem> =
        register("blaze_and_steel") { BlazeAndSteelItem() }
    val RUNE_PATTERN = basic("rune_pattern")
    val PORTKEY = basic("portkey")
    val LOTUS_SEEDS = basic("lotus_seeds")
    val ESCAPE_ROPE = basic("escape_rope")
    val ENDER_BUCKET = basic("ender_bucket")
    val REINFORCED_ENDER_BUCKET = basic("reinforced_ender_bucket")
    val CHUNK_ANALYZER: DeferredItem<ChunkAnalyzerItem> =
        register("chunk_analyzer") { ChunkAnalyzerItem() }
    val TIME_IN_A_BOTTLE = basic("time_in_a_bottle")
    val ECLIPSED_CLOCK = basic("eclipsed_clock")
    val DIVINING_ROD = basic("divining_rod")    // Same item for all ores

    // Weather Eggs
    val WEATHER_EGG_SUNNY: DeferredItem<WeatherEggItem> =
        register("weather_egg_sunny") { WeatherEggItem.SUNNY }
    val WEATHER_EGG_RAINY: DeferredItem<WeatherEggItem> =
        register("weather_egg_rainy") { WeatherEggItem.RAINY }
    val WEATHER_EGG_STORMY: DeferredItem<WeatherEggItem> =
        register("weather_egg_stormy") { WeatherEggItem.STORMY }

    // Filters
    val LOCATION_FILTER: DeferredItem<FilterLocationItem> =
        register("location_filter") { FilterLocationItem() }
    val ITEM_FILTER = basic("item_filter")
    val ENTITY_FILTER: DeferredItem<FilterEntityItem> =
        register("entity_filter") { FilterEntityItem() }
    val ID_CARD: DeferredItem<FilterPlayerItem> =
        register("id_card") { FilterPlayerItem() }

    // Imbues
    val FIRE_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_fire") { ImbueItem(ModEffects.FIRE_IMBUE) }
    val POISON_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_poison") { ImbueItem(ModEffects.POISON_IMBUE) }
    val EXPERIENCE_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_experience") { ImbueItem(ModEffects.EXPERIENCE_IMBUE) }
    val WITHER_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_wither") { ImbueItem(ModEffects.WITHER_IMBUE) }
    val COLLAPSE_IMBUE: DeferredItem<ImbueItem> =
        register("imbue_collapse") { ImbueItem(ModEffects.COLLAPSE_IMBUE) }

    // Spectre
    val SPECTRE_INGOT = basic("spectre_ingot")
    val SPECTRE_STRING = basic("spectre_string")
    val SPECTRE_ILLUMINATOR = basic("spectre_illuminator")
    val SPECTRE_KEY = basic("spectre_key")
    val SPECTRE_ANCHOR = basic("spectre_anchor")
    val SPECTRE_SWORD = basic("spectre_sword")
    val SPECTRE_PICKAXE = basic("spectre_pickaxe")
    val SPECTRE_AXE = basic("spectre_axe")
    val SPECTRE_SHOVEL = basic("spectre_shovel")
    val SPECTRE_CHARGER = basic("spectre_charger") //Same item for all tiers

    // Redstone
    val ADVANCED_REDSTONE_REPEATER = basic("advanced_redstone_repeater")
    val ADVANCED_REDSTONE_TORCH = basic("advanced_redstone_torch")
    val REDSTONE_TOOL = basic("redstone_tool")
    val REDSTONE_ACTIVATOR = basic("redstone_activator")
    val REDSTONE_REMOTE = basic("redstone_remote")

    // Lubricant
    val SUPER_LUBRICANT_TINCTURE = basic("super_lubricant_tincture")

    // What if you could instead apply the tincture to any boots?
    val SUPER_LUBRICANT_BOOTS: DeferredItem<ArmorItem> =
        register("super_lubricant_boots") {
            ArmorItem(
                ArmorMaterials.IRON,
                ArmorItem.Type.BOOTS,
                Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
            )
        }

    // Floo
    val FLOO_POWDER = basic("floo_powder")
    val FLOO_SIGN = basic("floo_sign")
    val FLOO_TOKEN = basic("floo_token")
    val FLOO_POUCH = basic("floo_pouch")

    // Sound
    val SOUND_PATTERN = basic("sound_pattern")
    val SOUND_RECORDER = basic("sound_recorder")
    val PORTABLE_SOUND_DAMPENER = basic("portable_sound_dampener")

    // Colors
    val GRASS_SEEDS: DeferredItem<GrassSeedItem> =
        register("grass_seeds") { GrassSeedItem(dyeColor = null) }
    val GRASS_SEEDS_WHITE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_white") { GrassSeedItem(dyeColor = DyeColor.WHITE) }
    val GRASS_SEEDS_ORANGE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_orange") { GrassSeedItem(dyeColor = DyeColor.ORANGE) }
    val GRASS_SEEDS_MAGENTA: DeferredItem<GrassSeedItem> =
        register("grass_seeds_magenta") { GrassSeedItem(dyeColor = DyeColor.MAGENTA) }
    val GRASS_SEEDS_LIGHT_BLUE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_light_blue") { GrassSeedItem(dyeColor = DyeColor.LIGHT_BLUE) }
    val GRASS_SEEDS_YELLOW: DeferredItem<GrassSeedItem> =
        register("grass_seeds_yellow") { GrassSeedItem(dyeColor = DyeColor.YELLOW) }
    val GRASS_SEEDS_LIME: DeferredItem<GrassSeedItem> =
        register("grass_seeds_lime") { GrassSeedItem(dyeColor = DyeColor.LIME) }
    val GRASS_SEEDS_PINK: DeferredItem<GrassSeedItem> =
        register("grass_seeds_pink") { GrassSeedItem(dyeColor = DyeColor.PINK) }
    val GRASS_SEEDS_GRAY: DeferredItem<GrassSeedItem> =
        register("grass_seeds_gray") { GrassSeedItem(dyeColor = DyeColor.GRAY) }
    val GRASS_SEEDS_LIGHT_GRAY: DeferredItem<GrassSeedItem> =
        register("grass_seeds_light_gray") { GrassSeedItem(dyeColor = DyeColor.LIGHT_GRAY) }
    val GRASS_SEEDS_CYAN: DeferredItem<GrassSeedItem> =
        register("grass_seeds_cyan") { GrassSeedItem(dyeColor = DyeColor.CYAN) }
    val GRASS_SEEDS_PURPLE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_purple") { GrassSeedItem(dyeColor = DyeColor.PURPLE) }
    val GRASS_SEEDS_BLUE: DeferredItem<GrassSeedItem> =
        register("grass_seeds_blue") { GrassSeedItem(dyeColor = DyeColor.BLUE) }
    val GRASS_SEEDS_BROWN: DeferredItem<GrassSeedItem> =
        register("grass_seeds_brown") { GrassSeedItem(dyeColor = DyeColor.LIGHT_BLUE) }
    val GRASS_SEEDS_GREEN: DeferredItem<GrassSeedItem> =
        register("grass_seeds_green") { GrassSeedItem(dyeColor = DyeColor.GREEN) }
    val GRASS_SEEDS_RED: DeferredItem<GrassSeedItem> =
        register("grass_seeds_red") { GrassSeedItem(dyeColor = DyeColor.RED) }
    val GRASS_SEEDS_BLACK: DeferredItem<GrassSeedItem> =
        register("grass_seeds_black") { GrassSeedItem(dyeColor = DyeColor.BLACK) }

    val RUNE_DUST_WHITE = basic("rune_dust_white")
    val RUNE_DUST_ORANGE = basic("rune_dust_orange")
    val RUNE_DUST_MAGENTA = basic("rune_dust_magenta")
    val RUNE_DUST_LIGHT_BLUE = basic("rune_dust_light_blue")
    val RUNE_DUST_YELLOW = basic("rune_dust_yellow")
    val RUNE_DUST_LIME = basic("rune_dust_lime")
    val RUNE_DUST_PINK = basic("rune_dust_pink")
    val RUNE_DUST_GRAY = basic("rune_dust_gray")
    val RUNE_DUST_LIGHT_GRAY = basic("rune_dust_light_gray")
    val RUNE_DUST_CYAN = basic("rune_dust_cyan")
    val RUNE_DUST_PURPLE = basic("rune_dust_purple")
    val RUNE_DUST_BLUE = basic("rune_dust_blue")
    val RUNE_DUST_BROWN = basic("rune_dust_brown")
    val RUNE_DUST_GREEN = basic("rune_dust_green")
    val RUNE_DUST_RED = basic("rune_dust_red")
    val RUNE_DUST_BLACK = basic("rune_dust_black")

    private fun basic(id: String): DeferredItem<Item> {
        return ITEM_REGISTRY.registerSimpleItem(id)
    }

    private fun <T : Item> register(id: String, itemBuilder: (Item.Properties) -> T): DeferredItem<T> {
        return ITEM_REGISTRY.registerItem(id, itemBuilder)
    }

    //TODO: Move to rune dust class, when that's been made
    fun getRuneDust(dyeColor: DyeColor): DeferredItem<Item> {
        return when (dyeColor) {
            DyeColor.WHITE -> RUNE_DUST_WHITE
            DyeColor.ORANGE -> RUNE_DUST_ORANGE
            DyeColor.MAGENTA -> RUNE_DUST_MAGENTA
            DyeColor.LIGHT_BLUE -> RUNE_DUST_LIGHT_BLUE
            DyeColor.YELLOW -> RUNE_DUST_YELLOW
            DyeColor.LIME -> RUNE_DUST_LIME
            DyeColor.PINK -> RUNE_DUST_PINK
            DyeColor.GRAY -> RUNE_DUST_GRAY
            DyeColor.LIGHT_GRAY -> RUNE_DUST_LIGHT_GRAY
            DyeColor.CYAN -> RUNE_DUST_CYAN
            DyeColor.PURPLE -> RUNE_DUST_PURPLE
            DyeColor.BLUE -> RUNE_DUST_BLUE
            DyeColor.BROWN -> RUNE_DUST_BROWN
            DyeColor.GREEN -> RUNE_DUST_GREEN
            DyeColor.RED -> RUNE_DUST_RED
            DyeColor.BLACK -> RUNE_DUST_BLACK
        }
    }

}